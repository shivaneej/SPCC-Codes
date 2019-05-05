%rachel was murdered after 10pm in california
%monica and emily are jealous of rachel
%rachel is more successful than ross,emily and monica
%rachel had arguments with ross and emily
%emily hates rachel
%monica and emily were in california at that time

%weapon(X): X carries weapon
weapon(ross).
weapon(emily).

%jealous(X,Y): X is jealous of Y
jealous(monica,rachel).
jealous(monica,chandler).
jealous(ross,chandler).
jealous(emily,rachel).

%success(X,Y): X is more successful than Y
success(chandler,rachel).
success(chandler,ross).
success(chandler,emily).
success(chandler,monica).
success(rachel,ross).
success(rachel,emily).
success(rachel,monica).
success(ross,emily).
success(ross,monica).
success(emily,monica).

%argue(X,Y): X and Y had an argument
argue(ross,rachel).
argue(ross,chandler).
argue(emily,rachel).

%hatred(X,Y): X hates Y
hatred(ross,chandler).
hatred(emily,rachel).

%place(X,Y): X was at place Y when rachel was killed
place(chandler,tulsa).
place(ross,florida).
place(monica,california).
place(emily,california).
place(rachel,california).


%suspect(Y): X might have killed Y
tempa(X,Y) :- hatred(X,Y) ; success(Y,X) ; argue(X,Y) ; jealous(X,Y).
tempb(X,Y) :- tempa(X,Y) , weapon(X).
suspect(X,Y) :- tempb(X,Y) ; (place(X,A) , place(Y,A)).
killed(X,Y) :- hatred(X,Y) , success(Y,X) , argue(X,Y) , jealous(X,Y) , weapon(X) , place(X,A) , place(Y,A).
