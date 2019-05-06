img = imread('taj.bmp');
temp = zeros(130);
for i=2:129
    for j=2:129
        temp(i,j) = img(i-1,j-1);
    end
end
%Robert
for i=2:129
    for j=2:129
        m = 0;
        n = 0;
        m = temp(i,j)-temp(i+1,j+1);
        n = temp(i,j+1)-temp(i+1,j);
        if m<0
            m=0;
        elseif m>255
            m=255;
        end
        if n<0
            n=0;
        elseif n>255
            n=255;
        end
        RobX(i-1,j-1) = m;
        RobY(i-1,j-1) = n;
    end
end
Rob = sqrt(RobX.^2+RobY.^2);

%Prewitt
for i=2:129
    for j=2:129
        m = 0;
        n = 0;
        m = temp(i+1,j-1)+temp(i+1,j)+temp(i+1,j+1)-temp(i-1,j-1)-temp(i-1,j)-temp(i-1,j-1);
        n = temp(i-1,j+1)+temp(i,j+1)+temp(i+1,j+1)-temp(i-1,j-1)-temp(i,j-1)-temp(i+1,j-1);
        if m<0
            m=0;
        elseif m>255
            m=255;
        end
        if n<0
            n=0;
        elseif n>255
            n=255;
        end
        PreX(i-1,j-1) = m;
        PreY(i-1,j-1) = n;
    end
end
Pre = sqrt(PreX.^2+PreY.^2);

%Sobel
for i=2:129
    for j=2:129
        m = 0;
        n = 0;
        m = temp(i+1,j-1)+temp(i+1,j)+2*(temp(i+1,j+1))-temp(i-1,j-1)-2*(temp(i-1,j))-temp(i-1,j-1);
        n = temp(i-1,j+1)+2*(temp(i,j+1))+temp(i+1,j+1)-temp(i-1,j-1)-2*(temp(i,j-1))-temp(i+1,j-1);
        if m<0
            m=0;
        elseif m>255
            m=255;
        end
        if n<0
            n=0;
        elseif n>255
            n=255;
        end
        SobX(i-1,j-1) = m;
        SobY(i-1,j-1) = n;
    end
end
Sob = sqrt(SobX.^2+SobY.^2);
%Laplacian
for i=2:129
    for j=2:129
        m = 0;
        m = 4*(temp(i,j))-temp(i+1,j)-temp(i-1,j)-temp(i,j-1)-temp(i,j+1);
        if m<0
            m=0;
        elseif m>255
            m=255;
        end
        if n<0
            n=0;
        elseif n>255
            n=255;
        end
        Lap(i-1,j-1) = m;
    end
end

subplot(3,4,1);imshow(img);title('Input');
subplot(3,4,2);imshow(RobX,[]);title('Robert Fx');
subplot(3,4,3);imshow(RobY,[]);title('Rober Fy');
subplot(3,4,4);imshow(Rob,[]);title('Robert Edge');
subplot(3,4,6);imshow(PreX,[]);title('Prewitt Fx');
subplot(3,4,7);imshow(PreY,[]);title('Prewitt Fy');
subplot(3,4,8);imshow(Pre,[]);title('Prewitt Edge');
subplot(3,4,9);imshow(Lap,[]);title('Laplacian Edge');
subplot(3,4,10);imshow(SobX,[]);title('Sobel Fx');
subplot(3,4,11);imshow(SobY,[]);title('Sobel Fy');
subplot(3,4,12);imshow(Sob,[]);title('Sobel Edge');