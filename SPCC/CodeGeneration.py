def showRegContents():
  for i in range(3):
    print("R",str(i+1)," ← ",registers[i],sep='',end='  ')
  print(" ")
fp = open('input.txt','r')
operators = ["+","*","="]
var = [] #list of variables
registers = ["","",""]
tag = {} #dict for TAG
ptr = 0
showRegContents()
for line in fp:
  print("\n",line,sep='')
  inst=""
  #line has original tag
  tokens = line.strip().split() 
  temp = line.strip().split("=")
  lhs = temp[0].strip()
  rhs = temp[1].strip().split()
  for t in tokens:
    if not any(op in t for op in operators):
      var.append(t.strip())
    else:
      op = t
  count = 3 if len(rhs)==3 else 2
  if count==3:
    for i in range(len(rhs)):
      if not any(op in rhs[i] for op in operators):
        rhs[i] =rhs[i].strip()
        if rhs[i] not in registers:
          #find which can be replaced
          if "" in registers:
            registers[registers.index("")]=rhs[i]
            print("MOV R",str(registers.index(rhs[i])+1),",",rhs[i],sep='')
            showRegContents()
          else:
            inserted = False
            for reg in registers:
              if not reg in var[ptr-1:] and not reg in rhs:
                registers[registers.index(reg)]=rhs[i]
                print("MOV R"+str(registers.index(rhs[i])+1),",",rhs[i],sep='')
                showRegContents()
                inserted = True
                break
            if not inserted:
              registers[0]=rhs[i]
              print("MOV R1,",rhs[i],sep='')
              showRegContents()
        if i>1:
          print(inst," R"+str(registers.index(rhs[i-2])+1),", R"+str(registers.index(rhs[i])+1),sep='')
          temvar = registers[registers.index(rhs[i-2])]
          registers[registers.index(rhs[i-2])]=temp[1]
          showRegContents()
          registers[registers.index(temp[1])] = temvar.strip() 
      else:
        inst = "ADD" if rhs[i]=="+" else "MUL"
    print("MOV ",lhs,", R",str(registers.index(rhs[i-2])+1),sep='')
    registers[registers.index(rhs[i-2])] = lhs
    showRegContents()
  else:
    if rhs[0].strip() not in registers:
      #find which can be replaced
      if "" in registers:
        registers[registers.index("")]=rhs[0]
        print("MOV R",str(registers.index(rhs[0])+1),",",rhs[0],sep='')
        showRegContents()
      else:
        inserted = False
        for reg in registers:
          if not reg in var[ptr:] and not reg in rhs:
            registers[registers.index(reg)]=rhs[0]
            print("MOV R"+str(registers.index(rhs[0])+1),",",rhs[0],sep='')
            showRegContents()
            inserted = True
            break
        if not inserted:
          registers[0]=rhs[i]
          print("MOV R1,",rhs[0],sep='')
          showRegContents()
    print("MOV ",lhs,", R",str(registers.index(rhs[0])+1),sep='')
    registers[registers.index(rhs[0])] = lhs
    showRegContents()

