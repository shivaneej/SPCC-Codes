distance(mumbai,pune,800).
distance(mumbai,ahemdabad,1200).
distance(pune,delhi,300).
distance(ahemdabad,delhi,500).
distance(delhi,kanpur,400).
distancedirect(X,Y,Z):-distance(Y,X,Z).
distancedirect(X,X,0).
distancedirect(X,Y,Z):-distance(X,Y,Z).
distancedirect(X,Z,C):-distance(X,Y,A),distancedirect(Y,Z,B),C is A + B.
fdis:-
	write('Enter source city:'),nl,
	read(Input1),nl,
	write('destination city:'),nl,
	read(Input2),nl,
	write('distance between cities is:'),nl,
	distancedirect(Input1,Input2,Output),nl,
	write(Output).
