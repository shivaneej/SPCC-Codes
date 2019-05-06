clc;
n = input('Enter number of samples: ');
ip = zeros(n,1);
for i=1:n
    txt = sprintf('Enter f(x) for n = %d: ',(i-1));
    ip(i) = input(txt);
end
dftmatrix = ones(n);
theta = -2*pi/n;
itheta = -2*pi/n;
for i=1:n
    for j=1:n
        dftmatrix(i,j) = complex(cos(theta*(i-1)*(j-1)),sin(theta*(i-1)*(j-1)));
        idftmatrix(i,j) = complex(cos(itheta*(i-1)*(j-1)),sin(itheta*(i-1)*(j-1)));
    end
end
disp('DFT Matrix:');
disp(dftmatrix);
u = dftmatrix*ip;
disp('Final Result');
disp(u);

v = idftmatrix*u;
v = v/n;
disp('IDFT');
disp(v)
