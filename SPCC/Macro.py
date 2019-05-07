import re
def pass1():
  print(".....Starting pass 1....")
  keywords = ["MACRO","macro"]
  mntc = mdtc = 1
  mnt=[]
  mdt=[" "]
  for line in fp:
    if not any(kw in line for kw in keywords):
      op.write(line) #write to op as it is
    else: #start of macro found
      line = fp.readline()
      token = line.split()
      label="-"
      #get macro name and args list
      if len(token)==2:
        macroname = token[0]
        args = token[1].split(",")
      elif len(token)==3:
        label = token[0]
        macroname = token[1]
        args = token[2].split(",")
      else:
        macroname = token[0]
        args=""
      # print("Macro name is "+macroname)
      ala=[label]
      for arg in args:
        if not arg.find("=") == -1:
          temp = arg.split("=")
          arg = temp[0]
          # default[arg]=temp[1]
        ala.append(arg)
      mnt_entry={'name':macroname,'mdtindex':mdtc,'ala':ala} 
      mnt.append(mnt_entry)
      mntc += 1
      mdt.append(line)
      mdtc += 1
      while not "MEND" in line and not "mend" in line:
        line = fp.readline()
        for arg in  ala:
          if arg in line:
            line = line.replace(arg,"#"+str(ala.index(arg)))
        mdt.append(line)
        mdtc += 1
    #print
  print("\n------MNT------")
  print("Name\t|\tIndex")
  for entry in mnt:
    print(entry['name']+"\t\t"+str(entry['mdtindex']))
  print("\n------ALA------")
  for x in mnt:
    print("\nFor macro "+x['name'])
    print("Index\t|\tArgument")
    for y in x['ala']:
      print(str(x['ala'].index(y))+"\t\t\t"+y)
  print("\n------MDT------")
  print("Index\t|\tLine")
  for i in range(1,len(mdt)):
    print(str(i)+"\t\t\t"+mdt[i])
  op.close()
  print("\n.....Pass 1 over....\n")
  pass2(mnt,mdt)

def pass2(mnt,mdt):
  default = {}
  print("\n.....Starting pass 2....\n")
  print("\n------ALA------")
  for line in fp2:
    mdtp = 1
    label=""
    token = line.split()
    if len(token)==2:
      mnemonic = token[0]
      operand = token[1]
    elif len(token)==3:
      label = token[0]
      mnemonic = token[1]
      operand = token[2]
    else:
      mnemonic = line.strip()
    if not any(entry['name'] in mnemonic for entry in mnt):
      op2.write(line) #write to op as it is
    else:
      for entry in mnt:
        if entry.get('name')==mnemonic:
          mdtp = entry.get('mdtindex')
          ala = entry.get('ala')
      if len(ala)>1: #args present
        macrodef = mdt[mdtp].split()
        args = macrodef[len(macrodef)-1].split(",") #get args list
        for arg in args: 
          if arg.find("=") > -1: #search for default
            temp = arg.split("=")
            default[temp[0]]=temp[1] #store defualt 
        #setup ala
        if label:
          ala[0]=label
        param = re.split(",|=",operand)
        if "=" in operand: #keyword param 
          t = 0
          while t<len(param)-1:
            ala[ala.index(param[t])] = param[t+1]
            t+=2
          if len(param)/2 < len(ala)-1: #default present
            for ele in ala:
              if ele.startswith("&"):
                ala[ala.index(ele)] = default.get(ele,"")
        else: #positional
          for i in range(1,len(ala)):
            ala[i] = param[i-1]
      mdtp+=1
      newline = mdt[mdtp]
      while not "MEND" in newline and not "mend" in newline:
        if not "#" in newline:
          op2.write(newline)
        else:
          index = newline.index("#")
          tbr = newline[index:index+2]
          alaindex = int(newline[index+1])
          newline = newline.replace(tbr,ala[alaindex])
          op2.write(newline)
        mdtp+=1
        newline = mdt[mdtp]
      print("\nFor macro "+mnemonic)
      print("Index\t|\tArgument")
      for y in ala:
        print(str(ala.index(y))+"\t\t\t"+y)
  op2.close()
  

  print("\n.....Pass 2 over....\n")

fp = open('input.txt','r')
op = open('pass1op.txt','w')
fp2 = open('pass1op.txt','r')
op2 = open('final.txt','w')
pass1()
