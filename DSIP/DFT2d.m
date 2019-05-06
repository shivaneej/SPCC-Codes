img = imread('taj.bmp');
ip = double(img);
theta = -2*pi/128;
itheta = 2*pi/128;
twd = zeros(128,128);
itwd = zeros(128,128);
for i=1:128
    for j=1:128
        twd(i,j) = complex(cos(theta*(i-1)*(j-1)),sin(theta*(i-1)*(j-1)));
        itwd(i,j) = complex(cos(itheta*(i-1)*(j-1)),sin(itheta*(i-1)*(j-1)));
    end
end
a = twd*ip*twd;
b = itwd*a*itwd;
b=b/(128*128);
subplot(2,2,1);imshow(img);title('Input');
subplot(2,2,3);imshow(uint8(a));title('After DFT');
subplot(2,2,4);imshow(uint8(b));title('After IDFT');
