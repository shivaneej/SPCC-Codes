def printprod(grammar):
	nonterm = list(grammar.keys())
	for t in nonterm:
		print(t+" -> "+"|".join(grammar.get(t)))

fp = open('grammarinput.txt','r')
grammar = {}

for line in fp:
	g = line.split("->")
	grammar[g[0].strip()]=list(g[1].strip().split("|"))
nonterm = list(grammar.keys())
printprod(grammar)
for i in nonterm:
	for j in nonterm[:nonterm.index(i)]:
		print("\n\ni =",i,"j =",j)
		iprods = grammar.get(i)
		jprods = grammar.get(j)
		for iprod in iprods:
			iprod = iprod.strip()
			j = j.strip()
			if iprod.startswith(j):
				print(iprod,"to be replaced")
				iprods.remove(iprod)
				for jprod in jprods:
					newiprod=iprod.replace(j,jprod,1)
					iprods.append(newiprod)
				grammar[i] = iprods
				printprod(grammar)
	prods = grammar.get(i)
	rec = [p for p in prods if p.strip().startswith(i)]
	nonrec = [p for p in prods if not p.startswith(i)]
	if rec:
		print("Direct recursion present")
		newiprod = [str(p+i+"`") for p in nonrec]
		grammar[i] = newiprod
		newterm = str(i+"`") 
		print(newterm)
		newtermprod = [str(p[1:]+i+"`") for p in rec]
		newtermprod.append('$')
		grammar[newterm] = newtermprod
		print("Direct recursion removed")
		printprod(grammar)
print("\nFinal result")
printprod(grammar)

#Example 1
# A -> Bxy|x
# B -> CD
# C -> A|c
# D -> d

#Example 2
# S ->AaB
# A ->Saa|b
# B->Aab|c

