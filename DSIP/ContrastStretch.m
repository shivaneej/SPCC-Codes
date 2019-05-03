clc;
ip = imread('taj.bmp');
r1 = input('Enter r1\n');
s1 = input('Enter s1\n');
r2 = input('Enter r2\n');
s2 = input('Enter s2\n');
op = zeros(128,'uint8');
alpha = s1/r1;
beta = (s2-s1)/(r2-r1);
gamma = (255-s2)/(255-r1);
for i=1:128
    for j=1:128
        r = ip(i,j);
        if r<r1
            op(i,j) = r*alpha;
        elseif r>=r1 && r<r2
            op(i,j) = s1 +beta*(r-r1);
        elseif r>=r2
            op(i,j) = s2+gamma*(r-r2);
        end
    end
end
vals = sprintf('a = %f,\n b = %f ,\n c = %f',alpha,beta,gamma);
vals1 = sprintf('r1 = %d, s1 = %d\n r2 = %d , s2 = %d',r1,s1,r2,s2);
subplot(2,2,1);imshow(ip);title('Input');
subplot(2,2,2);imshow(op);title('Output');
subplot(2,2,3);axis off;title(vals1);
subplot(2,2,4);axis off;title(vals);