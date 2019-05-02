%histogram
ip = imread('taj.bmp');
op =zeros(128,'uint8');
total = 16384;
count(1:256) = 0;
opcount(1:256) = 0;
pdf(1:256) = 0;
cdf(1:256) = 0;
opmap(1:256) = 0;
for i=1:128
    for j=1:128
        val = ip(i,j);
        count(val+1) = count(val+1) + 1;
    end
end
for i=1:256
    pdf(i) = count(i)/total;
    if i==1
        cdf(i) = pdf(i);
    else
        cdf(i) = cdf(i-1) + pdf(i);
    end
    opmap(i) = round(cdf(i)*255);
end
for i=1:128
    for j=1:128
        op(i,j) = opmap(ip(i,j)+1);
    end
end
for i=1:128
    for j=1:128
        val = op(i,j);
        opcount(val+1) = opcount(val+1) + 1;
    end
end
A = linspace(0,255,256);
subplot(2,3,1);imshow(ip);title('input');
subplot(2,3,2);imshow(op);title('output');
subplot(2,3,3);stem(A,count);title('input');
subplot(2,3,4);stem(A,opcount);title('output');
subplot(2,3,5);imhist(ip);title('input');
subplot(2,3,6);imhist(op);title('output');