optable = {
	'+':{'+':'>', '*':'<', 'i':'<', '(':'<', ')':'>', '$':'>'},
	'*':{'+':'>', '*':'>', 'i':'<', '(':'<', ')':'>', '$':'>'},
	'i':{'+':'>', '*':'>', 'i':'ERR', '(':'ERR', ')':'>', '$':'>'},
	'(':{'+':'<', '*':'<', 'i':'<', '(':'<', ')':'=', '$':'ERR'},
	')':{'+':'>', '*':'>', 'i':'ERR', '(':'ERR', ')':'>', '$':'>'},
	'$':{'+':'<', '*':'<', 'i':'<', '(':'<', ')':'ERR', '$':'ACC'}
}
grammar = {'E':['E+E','E*E','(E)','i']}
ip = input('Enter a string:\n')
ip+=('$')
stack = ['$']
ipbuffer= list(ip)
print("Initial:")
print("Stack: ",' '.join(stack))
print("IB: ",' '.join(ipbuffer))
while True:
	ib = ipbuffer[0]
	st = stack[-1]
	print("\n\nStack top = ",st," Input Buffer = ",ib)
	precedence = optable.get(st).get(ib)
	print(st+" "+precedence+" "+ib)
	if precedence=='<':
		stack.append('<')
		stack.append(ipbuffer.pop(0))
	elif precedence=='=':
		stack.append(ipbuffer.pop(0))
	elif precedence=='ACC':
		print("String is valid")
		break
	elif precedence=='ERR':		
		print("String is invalid")
		break
	else:
		while precedence=='>':
			prod = ""
			while stack[-1]!='<':
				prod+=stack.pop(-1)
			stack.pop(-1)
			prod = prod[::-1]
			print("Prod is ",prod)
			if prod in grammar.get('E'):
				print("Stack: ",' '.join(stack))
				st =stack[-1]
				pre=optable.get(st).get(ib)
				if pre=='<':
					stack.append('<')
					stack.append('E')
					stack.append(ipbuffer.pop(0))
					precedence='<'
					print(st+" "+precedence+" "+ib)
				elif pre=='>':
					stack.append('E')
					print("Stack: ",' '.join(stack))
					precedence='>'
					print(st+" "+precedence+" "+ib)
				elif pre=='=':
					stack.append('E')
					stack.append(ipbuffer.pop(0))
					precedence='='
					print(st+" "+precedence+" "+ib)
				elif pre=='ACC':
					print("String is valid")
					break
				else:
					print("String is invalid")
					break
			else:
				print("No valid grammar")
				exit()
	print("Stack: ",' '.join(stack))
	print("IB: ",' '.join(ipbuffer))

