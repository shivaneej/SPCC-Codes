fp = open('cfile.txt','r')
op = open('lexop.txt','w')
def getToken(line):
	start = 0
	end = 0
	current = 0
	output = ""
	while current!=len(line):
		if start==end:
			c = line[start]
			if c in operators:
				output=output+'<op#'+str(operators.index(c))+'>'
				start+=1
			elif c in symbols:
				output=output+'<spSym#'+str(symbols.index(c))+'>'
				start+=1
			end+=1
		else:
			while not isDelim(line[end]):
				end+=1
			toCheck = str(line[start:end]).strip()
			if isDigit(toCheck):
				if not toCheck in numConst:
					numConst.append(toCheck)
				output+="<const(num)#"+str(numConst.index(toCheck))+'>'
			elif toCheck in keywords:
				output+="<keyword#"+str(keywords.index(toCheck))+'>'
			elif toCheck in headers:
				output+="<headerFile#"+str(headers.index(toCheck))+'>'
			elif isCharConst(toCheck,line):
				if not toCheck in charConst:
					charConst.append(toCheck);
				output+="<const(char)#"+str(charConst.index(toCheck))+'>'
			elif not isCharConst(toCheck,line):
				if validIdentifier(toCheck):
					if not toCheck in identifiers:
						identifiers.append(toCheck);
					output+="<identifier#"+str(identifiers.index(toCheck))+'>'
				else:
					if not '.' in toCheck:
						print("Invalid identifier")
					else:
						print("Invalid header file")
					output+="__ERROR__ "
			start = end
		current = end
	op.write(output)
	op.write('\n')


def isDigit(token):
    try:
        float(token)
        return True
    except ValueError:
        return False

def isDelim(token): #to check if delimiter
	if token in operators or token in symbols or token==' ':
		return True
	return False

def validIdentifier(token): #check if identifier is valid
	if len(token)>9:
		return False
	if token.isalnum() and token[0].isalpha():
		return True
	return False

def isCharConst(token,line): #check if char constant
	pos = line.index(token)
	start = pos - 1
	end = pos + len(token)
	if token in identifiers:
		return False
	if (line[start]=='"' and line[end]=='"') or (line[start]=='\'' and line[end]=='\''):
		return True
	return False

keywords=['break','char','const','continue','do','double','else','float','for','if','int','long','return',
'void','while','include','main','printf','scanf']
headers=['stdio.h','conio.h']
operators=['+','-','*','/','%','<','>','=']
symbols=[';','#','{','}','"','\'','(',')',',']
identifiers=[]
numConst =[]
charConst =[]
for line in fp:
	print(line)
	getToken(line.strip())
print("Identifier Table",identifiers);
print("Numeric Constant Table",numConst);
print("Character Constant Table",charConst);	
