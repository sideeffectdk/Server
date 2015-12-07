package pack;


import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Created by MED3-4, on 7/12/2015.
 */

//Converting image to binary using thresholding.
public class Converter {
    public  static BufferedImage convert(BufferedImage image) throws IOException {
        // For every pixel in the Y axis
        for (int y = 0; y < image.getHeight(); y++) {
            // And for every pixel in the X axis
            for (int x = 0; x < image.getWidth(); x++) {
                int colour = image.getRGB(x, y);
                Color colour2 = new Color(colour);
                int red = colour2.getRed();
                int green = colour2.getGreen();
                int blue = colour2.getBlue();
                // This is the threshold, if the pixel is above the greyscale value of 127 will change to white,
                // everything under, will change to black.
                if (((red + green + blue) / 3) < 128) {
                    image.setRGB(x, y, new Color(0, 0, 0).getRGB());
                } else {
                    image.setRGB(x, y, new Color(255, 255, 255).getRGB());
                }
            }
        }
        File outputfile = new File("blackNwhite.png");
        ImageIO.write(image, "png", outputfile);
        File f2 = new File("blackNwhite.png");
        BufferedImage image2 = ImageIO.read(f2);
        File f3 = new File("blackNwhite.png");
        BufferedImage image3 = ImageIO.read(f3);

        // This is where you decide what you want to do to the picture
        // Change the bool 'erode' to true if you want to erode the picture
        boolean erode = true;
        // Change the bool 'dilate' to true if you want to dilate the picture
        boolean dilate = false;

        // Here we have a failsafe, a check if BOTH dilate and erode are true, if they are it sets them to false,
        // and tells the console that they cannot both be true.
        if (erode == true && dilate == true) {
            System.out.println("both erode and dilate cannot be true at the same time");
            erode = false;
            dilate = false;
        }

        if (erode == true || dilate == true) {
            for (int y = 1; y < image2.getHeight() - 1; y++) {
                // This is the kernel, it checks the neighboring pixels in the x and y axis
                for (int x = 1; x < image2.getWidth() - 1; x++) {
                    int colour1 = image2.getRGB(x, y - 1);Color colour11 = new Color(colour1);
                    int colour2 = image2.getRGB(x - 1, y);Color colour22 = new Color(colour2);
                    int colour3 = image2.getRGB(x + 1, y);Color colour33 = new Color(colour3);
                    int colour4 = image2.getRGB(x, y + 1);Color colour44 = new Color(colour4);
                    int colour5 = image2.getRGB(x, y + 1);Color colour55 = new Color(colour5);

                    int red1 = colour11.getRed();int green1 = colour11.getGreen();int blue1 = colour11.getBlue();
                    int red2 = colour22.getRed();int green2 = colour22.getGreen();int blue2 = colour22.getBlue();
                    int red3 = colour33.getRed();int green3 = colour33.getGreen();int blue3 = colour33.getBlue();
                    int red4 = colour44.getRed();int green4 = colour44.getGreen();int blue4 = colour44.getBlue();
                    int red5 = colour55.getRed();int green5 = colour55.getGreen();int blue5 = colour55.getBlue();

                    // This is erode, it checks all the neighboring pixels to see if they are black, if ANY one of them are, it changes the pixel to black.
                    if (erode == true) {
                        if ((red1 + green1 + blue1) == 0 || (red2 + green2 + blue2) == 0 || (red3 + green3 + blue3) == 0 || (red4 + green4 + blue4) == 0 || (red5 + green5 + blue5) == 0) {
                            image3.setRGB(x, y, new Color(0, 0, 0).getRGB());
                        }
                    // This is dilate, it checks all the neighboring pixels to see if they are white, if ANY one of them are white, it changes it to white.
                    } if (dilate == true) {
                        if ((red1 + green1 + blue1) / 3 == 255 || (red2 + green2 + blue2) / 3 == 255 || (red3 + green3 + blue3) / 3 == 255 || (red4 + green4 + blue4) / 3 == 255 || (red5 + green5 + blue5) / 3 == 255) {
                            image3.setRGB(x, y, new Color(255, 255, 255).getRGB());
                        }
                    }
                }
            }
        }
        return image3;
    }

}
