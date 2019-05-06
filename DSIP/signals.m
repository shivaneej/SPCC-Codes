clc;
clear all;
sig1 = zeros(1,9);
sig2 = zeros(1,9);
add = zeros(1,9);
mul = zeros(1,9);
lshift = zeros(1,9);
rshift = zeros(1,9);
scale = zeros(1,9);
n1 = input('Start of signal 1');
N1 = input('Samples in sig 1');
n2 = input('Start of signal 2');
N2 = input('Samples in sig 2');
f = input('Scaling factor');
q = input('Shift'); %negative means n+m shift left, positive means n-m shift right
for i=n1:n1+N1-1
	txt = sprintf('Enter x1(n) for n = %d',i);
	sig1(i+5) = input(txt);
end
for i=n2:n2+N2-1
	txt = sprintf('Enter x2(n) for n = %d',i);
	sig2(i+5) = input(txt);
end
temp1 = sig1(n1+5:n1+N1+4);
A = linspace(n1,n1+N1-1,N1);
temp2 = sig2(n2+5:n2+N2+4);
B = linspace(n2,n2+N2-1,N2);
for i=1:9
    add(i) = sig1(i)+sig2(i);
    mul(i) = sig1(i)*sig2(i);
    scale(i) = f*sig1(i);
end
end1 = n1+N1-1;
end2 = n2+N2-1;
for i=n1:end1
	lshift(i-q+5) = sig1(i+5);
	rshift(i+q+5) = sig1(i+5);
end
if n1>n2
    addstart = n2;
    mulstart = n1;
elseif n2>=n1
    addstart = n1;
    mulstart = n2;
end
if end1>end2
    addend = end1;
    mulend = end2;
elseif end2>=end1
    addend = end2;
    mulend = end1;
end
tempadd = add(addstart+5:addend+5);
C = linspace(addstart,addend,addend-addstart+1);
tempmul = mul(mulstart+5:mulend+5);
D = linspace(mulstart,mulend,mulend-mulstart+1);
tempscale = scale(n1+5:end1+5);
sctitle = sprintf('sf = %d',f);
templshift = lshift(n1-q+5:end1-q+5);
E = linspace(n1-q,end1-q,N1);
temprshift = rshift(n1+q+5:end1+q+5);
F = linspace(n1+q,end1+q,N1);
ls = sprintf('x(n+%d)',q);
rs = sprintf('x(n-%d)',q);
subplot(2,4,1);stem(A,temp1);title('x1(n)');
subplot(2,4,2);stem(B,temp2);title('x2(n)');
subplot(2,4,3);stem(C,tempadd);title('x1+x2');
subplot(2,4,4);stem(D,tempmul);title('x1*x2');
subplot(2,4,5);stem(A,tempscale);title(sctitle);
subplot(2,4,6);stem(E,templshift);title(ls);
subplot(2,4,7);stem(F,temprshift);title(rs);