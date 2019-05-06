% -4 to +4
N1 = input('Enter number of samples in x(n):\n');
n1 = input('Enter starting value of n for x(n):\n');
N2 = input('Enter number of samples in h(n):\n');
n2 = -1;
opstart = n1-1;
opsam = N1 + N2 - 1;
op = zeros(1,opsam,'int32');
x = zeros(1,9,'int32');
h = zeros(1,9,'int32');
tempx = zeros(1,N1,'int32');
temph = zeros(1,N2,'int32');
mat(1:N1,1:N2) = 0;
for i=n1:n1+N1-1
    txt = sprintf('Enter x(n) for n = %d: ',i);
    x(5+i) = input(txt);
    tempx(i-n1+1) = x(5+i);
end

for i=n2:n2+N2-1
    txt = sprintf('Enter h(n) for n = %d: ',i);
    h(5+i) = input(txt);
    temph(i-n2+1) = h(5+i);
end


%matrix method
for i=1:N1
    for j=1:N2
        mat(i,j) = tempx(i) * temph(j);
    end
end

for i=1:N1
        row = i;
        col = 1;
        sum = mat(row,col);
        while row>1 && col<N2
            row = row - 1;
            col = col + 1;
            sum = sum + mat(row,col);
        end
        op(i) = sum;
end
for j=2:N2
    row = N1;
    col = j;
    sum = mat(row,col);
    while row>1 && col<N2
        row = row - 1;
        col = col + 1;
        sum = sum + mat(row,col);
    end
        op(N1+j-1) = sum;
end
newmat(1:N1+1,1:N2+1)=0;
for i=2:N1+1
    newmat(i,1) = tempx(i-1);
end
for j=2:N2+1
    newmat(1,j) = temph(j-1);
end
for i=2:N1+1
    for j=2:N2+1
       newmat(i,j) = mat(i-1,j-1);
    end
end
A = linspace(n1,n1+N1-1,N1);
B = linspace(-1,N2-2,N2);
C = linspace(opstart,opstart+opsam-1,opsam);
xptitle= strtrim(strcat('x(n) = \{',sprintf('%d, ',tempx),'\}'));
htitle= strtrim(strcat('h(n) = \{',sprintf('%d, ',temph),'\}'));
zeropos = (-1*opstart) +1;
ctitle= strtrim(strcat('y(n) = \{',sprintf('%d, ',op),'\} 0 at ',sprintf('%d',zeropos)));
mattitle = cellstr(num2str(newmat)); 
subplot(221);stem(A,tempx);title(xptitle);
subplot(222);stem(B,temph);title(htitle);
set(subplot(2,2,3),'visible','off');text(0.5,0.5,mattitle);
subplot(224);stem(C,op);title(ctitle);


