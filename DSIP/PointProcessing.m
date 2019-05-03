ip = imread('taj.bmp');
t = input('Enter threshold');
r1 = input('Enter r1');
r2 = input('Enter r2');
neg = zeros(128,'uint8');
th = zeros(128,'uint8');
gbg = zeros(128,'uint8');
gwbg = zeros(128,'uint8');
bit0 = zeros(128,'uint8');
bit1 = zeros(128,'uint8');
bit2 = zeros(128,'uint8');
bit3 = zeros(128,'uint8');
bit4 = zeros(128,'uint8');
bit5 = zeros(128,'uint8');
bit6 = zeros(128,'uint8');
bit7 = zeros(128,'uint8');
for i=1:128
    for j=1:128
        neg(i,j) = 255-ip(i,j);
        gbg(i,j) = ip(i,j);
        if ip(i,j)>= t
          th(i,j) = 255;
        end
        if ip(i,j)>=r1 && ip(i,j)<=r2
            gbg(i,j) = 255;
            gwbg(i,j) = 255;
        end
        x = ip(i,j);
        bit0(i,j)=mod(x,2);
        x = x/2;
        bit1(i,j)=mod(x,2);
        x = x/2;
        bit2(i,j)=mod(x,2);
        x = x/2;
        bit3(i,j)=mod(x,2);
        x = x/2;
        bit4(i,j)=mod(x,2);
        x = x/2;
        bit5(i,j)=mod(x,2);
        x = x/2;
        bit6(i,j)=mod(x,2);
        x = x/2;
        bit7(i,j)=mod(x,2);
        x = x/2;
    end
end
b0 = imbinarize(bit0);
b1 = imbinarize(bit1);
b2 = imbinarize(bit2);
b3 = imbinarize(bit3);
b4 = imbinarize(bit4);
b5 = imbinarize(bit5);
b6 = imbinarize(bit6);
b7 = imbinarize(bit7);
subplot(3,4,1);imshow(neg);title('negation');
txt = sprintf('Threshold for t = %d',t);
subplot(3,4,2);imshow(th);title(txt);
txt = sprintf('GLBG for r1 = %d, r2= %d',r1,r2);
subplot(3,4,3);imshow(gbg);title(txt);
txt = sprintf('GLWBG for r1 = %d, r2= %d',r1,r2);
subplot(3,4,4);imshow(gwbg);title(txt);
subplot(3,4,5);imshow(b0);title('Bit 0');
subplot(3,4,6);imshow(b1);title('Bit 1');
subplot(3,4,7);imshow(b2);title('Bit 2');
subplot(3,4,8);imshow(b3);title('Bit 3');
subplot(3,4,9);imshow(b4);title('Bit 4');
subplot(3,4,10);imshow(b5);title('Bit 5');
subplot(3,4,11);imshow(b6);title('Bit 6');
subplot(3,4,12);imshow(b7);title('Bit 7');
