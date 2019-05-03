ip = imread('taj.bmp');
op = ip;
txt = input('Enter 6 character word','s');
ascii = double(txt);
asciib = de2bi(ascii,8,'left-msb'); 
for i=1:6
    for j=1:8
        val = ip(40+i,60+j);
        ipbin = de2bi(val,8,'left-msb');
        y = asciib(i,j);
        ipbin(1,8) = y;
        x = bi2de(ipbin,'left-msb');
        op(40+i,60+j) = x;
    end
end
imwrite(op,'encode.bmp');
optitle= sprintf('Image after encoding text "%s"',txt);
subplot(2,2,1),imshow(ip);title('Original Image');
subplot(2,2,2);imshow(op);title(optitle);
optxt = sprintf('Input text is "%s"',txt);
subplot(2,2,3);axis off;title(optxt);
