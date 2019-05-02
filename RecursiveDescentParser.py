# E -> x+T
# T -> (E) | x

n = 0
def e():
	global n
	if ip[n:n+2]=='x+':
		n+=2
		return t()
	else:
		return False

def t():
	global n
	if ip[n]=='(':
		n+=1
		return e()
		if ip[n]==')':
			n+=1
			return True
		else:
			return False
	elif ip[n]=='x':
		n+=1
		return True
	else:
		return False

ip = input("String\n")
if ip.count('(')!=ip.count(')') or len(ip)<3:
	print("Invalid")
	exit()
if e():
	print("Valid")
else:
	print("Invalid")
