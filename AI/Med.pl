%diagnose:-getSymptoms(List),
 %   forall(member(X,List),assertz(sym(X,_))),
%    writeln(Z).
sym(vomit,dengue).
sym(fever,dengue).
sym(headache,dengue).
sym(rashes,dengue).
sym(vomit,malaria).
sym(fever,malaria).
sym(headache,malaria).
sym(cough,malaria).
sym(weakness,typhoid).
sym(headache,typhoid).
sym(cough,typhoid).
getSymptoms([Symptom|List]):-
    re_flush/0,
    writeln('Enter symptom'),
    read(Symptom),
    dif(Symptom,done),
    getSymptoms(List).
getSymptoms([]).
diagnose:-getSymptoms(List),
    forall(member(X,List),assertz(symptom(X))),
    forall(disease(Z),writeln(Z)).

disease(Z):- symptom(X),sym(X,Z).


