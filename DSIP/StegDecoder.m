ip = imread('encode.bmp');
opasciib(1:6,1:8) = 0;
for i=1:6
    for j=1:8
        val = ip(40+i,60+j);
        valb = de2bi(val,8,'left-msb');
        opasciib(i,j) = valb(1,8);
    end
end
opascii = bi2de(opasciib,'left-msb');
msg = native2unicode(opascii,'ASCII');
disp(msg);
