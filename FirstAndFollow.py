def getFirst(var):
	pro = inp[var]
	l =[]
	for p in pro:
		if not p[0].isupper() and p[0] not in l:
			l.append(p[0])
		elif p[0]==var:
			continue
		else:
			a = 0
			while a<len(p):
				if not p[a].isupper():
					m = p[a]
					l.extend(m)
					break
				else:
					m = getFirst(p[a])
				l.extend(m)
				if "$" in m:
					a=a+1
				else:
					break

	first[var] = l
	return l

def getFollow(var):
	s = vars[0]
	l = follow.get(var)
	print("Initial for",var,l)
	if var==s:
		l.append('$')
	for v in vars:
		if v==var:
			continue
		plist = inp[v]
		for p in plist:
			if var in p:
				i = p.index(var)
				if i==len(p)-1:
					getFollow(v)
					l = follow.get(v)
				else:
					eps = False
					nex = p[i+1]
					f = first.get(nex,nex)
					print(var,"first",nex,f)
					if "$" in f:
						f.remove("$")
						eps = True
					l.extend(f)
					if eps:
						getFollow(v)
						l.extend(follow.get(v))
	follow[var] = l
	print("Final for",var,l)
	return l


fp = open("input.txt",'r')
inp = {} # grammar 
first={}
follow={}
for line in fp:
	print(line)
	l = line.split("->")
	inp[l[0].strip()] = list(map(str.strip, l[1].split("|")))
vars = list(inp.keys())
for var in vars:
	getFirst(var)
for var in vars:
	f = first.get(var)
	f = list(dict.fromkeys(f))
	first[var] = f
print("First",first)
# print(vars)
for var in vars:
	follow[var] = []
	getFollow(var)

for var in vars:
	f = follow.get(var)
	f = list(dict.fromkeys(f))
	follow[var] = f
print("Follow",follow)

#Example 1
# S -> AB
# A -> Aa|Ab|c
# B -> Bc|d

#Example 2
# E -> TA
# A -> +TA | $
# T -> FB
# B -> *FB | $
# F -> (E) | i
