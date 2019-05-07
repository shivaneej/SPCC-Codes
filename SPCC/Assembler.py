def check(lit):
	for entryy in lit:
		if entryy['val']=='':
			return lit.index(entryy)
	return -1

def pass1():
	lc = 0
	lenn=0
	motlist = list(mot.keys())
	for line in fp1:
		label = mnemonic = operand = ""
		token = line.split()
		if len(token)==2:
			mnemonic = token[0]
			operand = token[1]
		elif len(token)==3:
			label = token[0]
			mnemonic = token[1]
			operand = token[2]
		else:
			mnemonic = token[0]
		curr = lc
		if mnemonic in pot:
			if mnemonic=='START':
				lc = int(operand)
				entryx = {'name':label,'val':lc,'len':1,'ar':'R'}
				sym.append(entryx)

			elif mnemonic=='DC':
				while lc%4!=0:
					lc+=1
				lenn = 4
				if ',' in operand:
					op = operand.split(',')
					l = len(op)
					lenn = 4*l

			elif mnemonic=='DS':
				while lc%4!=0:
					lc+=1
				lenn = 4
				if operand.endswith('F'):
					operand = operand[:len(operand)-1]
				lenn = 4*int(operand)

			elif mnemonic=='END':
				while(check(lit)!=-1):
					index = check(lit)
					c = lit[index]
					c['len'] = 4
					c['val'] = lc
					lc+=4

		elif mnemonic in motlist:
			lenn = mot[mnemonic]
			if mnemonic!='BNE' and mnemonic!='BR':
				op = operand.split(",")
				if "F'" in op[1]:
					op[1] = op[1][1:]
					entry1 = {'name':op[1],'val':'','len':'','ar':'R'}
					lit.append(entry1)

		if label and not any(label==entry['name'] for entry in sym):
			sym.append({'name':label,'val':lc,'len':4,'ar':'R'})
		lc+=lenn
		# op1.write(str(curr)+'\t'+line) lc processing
		op1.write(line)
	print('sym',sym)

	print('\n\nlit',lit)

def pass2():
	basetbl = [-1]*16
	for line in fp2:
		label = mnemonic = operand = ""
		token = line.split()
		if len(token)==2:
			mnemonic = token[0]
			operand = token[1]
		elif len(token)==3:
			label = token[0]
			mnemonic = token[1]
			operand = token[2]
		else:
			mnemonic = token[0]


fp1 = open('pass1.txt','r')
op1 = open('pass2.txt','w')
fp2 = open('pass2.txt','r')
op2 = open('final.txt','w')
mot = {'L':4,'A':4,'ST':4,'BNE':4,'BR':2,'LR':2,'SR':2}
pot = ['DC','DS','START','END']
sym=[]
lit=[]
pass1()
pass2()