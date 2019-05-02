%filters
ip = imread('taj.bmp');
t = zeros(130,'uint16');
lowop = zeros(128,'uint8');
highop = zeros(128,'uint8');
highboost = zeros(128,'uint8');
medop = zeros(128,'uint8');
for i=2:129
    for j=2:129
        t(i,j) = ip(i-1,j-1);
    end
end
%low pass
for i=2:129
    for j=2:129
        s = t(i-1,j-1) + t(i-1,j) + t(i-1,j+1) + t(i,j-1) + t(i,j) + t(i,j+1) + t(i+1,j-1) + t(i+1,j) + t(i+1,j+1);
        s = s/9;
        lowop(i-1,j-1) = s;
    end
end

%high pass
for i=2:129
    for j=2:129
        s = 8*t(i,j) - t(i-1,j-1) - t(i-1,j) - t(i-1,j+1) - t(i,j-1) - t(i,j+1) - t(i+1,j-1) - t(i+1,j) - t(i+1,j+1);
        s = s/9;
        highop(i-1,j-1) = s;
    end
end

%high boost
for i=2:129
    for j=2:129
        s = 8.9*t(i,j) - t(i-1,j-1) - t(i-1,j) - t(i-1,j+1) - t(i,j-1) - t(i,j+1) - t(i+1,j-1) - t(i+1,j) - t(i+1,j+1);
%         s = s/9; No idea of this statement
        highboost(i-1,j-1) = s;
    end
end

%median
noise = imnoise(ip,'salt & pepper');
for i=2:129
    for j=2:129
        t(i,j) = noise(i-1,j-1);
    end
end  
mask(1:9) = 0;
for i=2:129
    for j=2:129
        mask(1) = t(i-1,j-1);
        mask(2) = t(i-1,j);
        mask(3) = t(i-1,j+1);
        mask(4) = t(i,j-1);
        mask(5) = t(i,j);
        mask(6) = t(i,j+1);
        mask(7) = t(i+1,j-1);
        mask(8) = t(i+1,j);
        mask(9) = t(i+1,j+1);
        mask = sort(mask);
        medop(i-1,j-1) = mask(5);
    end
end


subplot(2,3,1);imshow(ip);title('input');
subplot(2,3,2);imshow(lowop);title('low pass');
subplot(2,3,3);imshow(highop);title('high pass');
subplot(2,3,4);imshow(highboost);title('high boost');
subplot(2,3,5);imshow(noise);title('noise');
subplot(2,3,6);imshow(medop);title('restored');