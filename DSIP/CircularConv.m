clc;
clear all;
close all;
x=[];
h=[];
n1 = input('Enter no of x:');
fprintf('Enter x\n');
for i=1:n1
    value = input('Enter value');
    x = [x value];
end

n2 = input('Enter no of h:');
fprintf('Enter h:')
for i=1:n2
    value = input('Enter value:');
    h = [h value];
end

if n1>n2
    large = n1;
    for i=n2:n1-1
        h=[h 0];
    end
else
    large =n2;
    for i=n1:n2-1
        x=[x 0];
    end
end
temp =h;
in =(1:large);
mat = [];
for i=1:large
    mat = [mat;temp];
    temp=[temp(large) temp(1:large-1)];
end
mat = mat';
x2 = x';
y= mat * x2;
y=y';
subplot(3,1,1);
stem(in,x);
title('x Signal');
ylabel('x(n)');
subplot(3,1,2);
stem(in,h);
title('h Signal');
ylabel('h(n)');
subplot(3,1,3);
stem(in,y);
title('y Signal');
ylabel('y(n)');