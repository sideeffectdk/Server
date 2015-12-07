package pack;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sorin on 12/3/2015.
 */
public class Converter {
    public  static BufferedImage convert(BufferedImage image,int kernelChoice){
        int kernel=(kernelChoice-1)/2;
        int kernelSum=(kernelChoice*kernelChoice);
        int medianKernel =(kernelSum-1)/2;
        for (int y=kernel;y<image.getHeight()-kernel;y++){
            for (int x=kernel;x<image.getWidth()-kernel;x++) {
                int colorRGB[] = new int[kernelSum];
                Color colorArray[] = new Color[kernelSum];
                int randRED[] = new int[kernelSum];
                int randGREEN[] = new int[kernelSum];
                int randBLUE[] = new int[kernelSum];
                for (int i = 0; i < kernelChoice; i++) {
                    for (int j = 0; j < kernelChoice; j++) {
                        int size = i * kernelChoice + j;
                        colorArray[size] = new Color(colorRGB[size]);
                        colorRGB[size] = image.getRGB((x + j - kernel), (y + i - kernel));
                        randBLUE[size] = colorArray[size].getBlue();
                        randGREEN[size] = colorArray[size].getGreen();
                        randRED[size] = colorArray[size].getBlue();

                    }
                }
                for (int i = 0; i < kernelSum; i++) {
                    for (int j = 0; j < kernelSum; j++) {
                        int t = j + 1;
                        if (randRED[j] < randRED[t]) {
                            int aux = randRED[j];
                            randRED[j] = randRED[t];
                            randRED[t] = aux;
                        }
                        if (randBLUE[j] < randBLUE[t]) {
                            int aux = randBLUE[j];
                            randBLUE[j] = randBLUE[t];
                            randBLUE[t] = aux;
                        }
                        if (randGREEN[j] < randGREEN[t]) {
                            int aux = randGREEN[j];
                            randGREEN[j] = randGREEN[t];
                            randGREEN[t] = aux;
                        }
                    }
                }
                int red=randRED[medianKernel];
                int green=randGREEN[medianKernel];
                int blue=randBLUE[medianKernel];
                image.setRGB(x,y,new Color(red,green,blue).getRGB());
            }

        }
        return image;
    }

}
