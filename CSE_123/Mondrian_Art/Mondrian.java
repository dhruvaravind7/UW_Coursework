// Dhruv Aravind
// 11/05/2024
// CSE 123
// C2: Mondrian Art
// TA: Laura Khotemlyansky

import java.util.*;
import java.awt.*;

// Class that represents a Mondrian Image that is greater than 300 x 300 pixels.
public class Mondrian {
    
    private final int SMALLESTAREA = 10; 
    
    /* Behavior:
     *  - Creates a basic Mondrian image 
     * Parameters:
     *  - pixels: The pixels of the image that is going to be turned into the Mondrian picture.
     *            Size is greater than 300x300.
     *            Must be non-null
     * Returns:
     *  - N/A
     * Exceptions:
     *  - N/A
     */
    public void paintBasicMondrian(Color[][] pixels) {
        Random rand = new Random();
        paintMondrian(pixels, 0, 0, pixels.length, pixels[0].length, false, rand);
    }

    /* Behavior:
     *  - Creates a Mondrian image which gets darker as you travel closer to the center.
     * Parameters:
     *  - pixels: The pixels of the image that is going to be turned into the Mondrian picture.
     *            Size is greater than 300x300.
     *            Must be non-null
     * Returns:
     *  - N/A
     * Exceptions:
     *  - N/A
     */
    public void paintComplexMondrian(Color[][] pixels) {
        Random rand = new Random();
        paintMondrian(pixels, 0, 0, pixels.length, pixels[0].length, true, rand);
    }

    /* Behavior:
     *  - Private method that returns a number that represents how dark the image should be
     *    depending on coordinates of the section.
     * Parameters:
     *  - pixels: The pixels of the Mondrian Image
     *            Must be non-null
     *  - x1: The x-coordinate of the top left corner of the section.
     *  - y1: The y-coordinate of the top left corner of the section.
     *  - height: The x-coordinate of the bottom right corner of the section.
     *  - width: The y-coordinate of the bottom right corner of the section.
     * Returns:
     *  - int: A number that represents how dark the section should be. 
     *         1 is the lightest, 2 is slightly darker, and 4 represents the darkest. 
     * Exceptions:
     *  - N/A
     */
    private int darknessLevel(Color[][] pixels, int x1, int y1, int height, int width) {
        int regionHeight = height - x1;
        int regionWidth = width - y1;
        int middleHeight = regionHeight/2 + x1;
        int middleWidth = regionWidth/2 + y1; 
        if (middleWidth >= pixels[0].length/3 && middleWidth <= 2*pixels[0].length/3 && 
            middleHeight >= pixels.length/3 && middleHeight <= 2*pixels.length/3) {
            return 4;
        } else if (middleWidth <= pixels[0].length/6 || middleWidth >= 5*pixels[0].length/6 || 
                   middleHeight <= pixels.length/6 || middleHeight >= 5*pixels.length/6) {
            return 1;
        } else {
            return 2;
        }
    }

    /* Behavior: 
     *  - Private method that creates either a basic or complex Mondrian Image.
     * Parameter:
     *  - pixels: The pixels of the Mondrian Image
     *            Must be non-null
     *  - x1: The x-coordinate of the top left corner of the section.
     *  - y1: The y-coordinate of the top left corner of the section.
     *  - height: The x-coordinate of the bottom right corner of the section.
     *  - width: The y-coordinate of the bottom right corner of the section.
     *  - complex: Whether the Mondrian image is going to be basic or complex.
     *             True if the Mondrian image is complex i.e. incorporate the extension.
     *  - rand: Random object that is used to generate random numbers
     *          Must be non-null
     * Returns:
     *  - N/A
     * Exceptions:
     *  - N/A
     */
    private void paintMondrian(Color[][] pixels, int x1, int y1, int height, int width, 
                               boolean complex, Random rand){
        int regionHeight = height - x1;
        int regionWidth = width - y1;
        int totalHeight = pixels.length;
        int totalWidth = pixels[0].length; 
        if (regionHeight < totalHeight/4 && regionWidth < totalWidth/4) {
            int darkness = 1;
            if (complex) {
                darkness = darknessLevel(pixels, x1, y1, height, width);
            }
            int colorPicker = rand.nextInt(4);
            if (colorPicker == 0) {
                fill(pixels, x1, height, y1, width, new Color(255/darkness, 0, 0)); // red
            } else if (colorPicker == 1){
                fill(pixels, x1, height, y1, width, new Color(255/darkness, 255/darkness, 
                                                            0)); // yellow
            } else if (colorPicker == 2) {
                fill(pixels, x1, height, y1, width, new Color(0, 255/darkness,
                                                                255/darkness)); // cyan
            } else {
                fill(pixels, x1, height, y1, width, new Color(255/darkness, 255/darkness, 
                                                              255/darkness)); // white
            }
        } else if (regionHeight >= totalHeight/4 && regionWidth >= totalWidth/4) {
            int newWidth = rand.nextInt(width-SMALLESTAREA - (y1+SMALLESTAREA) + 1)
                                        + y1 + SMALLESTAREA;
            int newHeight = rand.nextInt(height-SMALLESTAREA - (x1+SMALLESTAREA) + 1)
                                         + x1 + SMALLESTAREA;
            paintMondrian(pixels, x1, y1, newHeight, newWidth, complex, rand);
            paintMondrian(pixels, x1, newWidth, newHeight, width, complex, rand);
            paintMondrian(pixels, newHeight, y1, height, newWidth, complex, rand);
            paintMondrian(pixels, newHeight, newWidth, height, width, complex, rand);
        } else if (regionWidth >= totalWidth/4) {
            int newWidth = rand.nextInt(width-SMALLESTAREA - (y1+SMALLESTAREA) + 1) + y1 + 
                                        SMALLESTAREA;
            paintMondrian(pixels, x1, y1, height, newWidth, complex, rand);
            paintMondrian(pixels, x1, newWidth, height, width,complex, rand);
        }
        else {
            int newHeight = rand.nextInt(height-SMALLESTAREA - (x1+SMALLESTAREA) + 1) + x1 + 
                                         SMALLESTAREA;
            paintMondrian(pixels, x1, y1, newHeight, width,complex, rand);
            paintMondrian(pixels, newHeight, y1, height, width, complex, rand);
        }
    }

    /* Behavior:
     *  - Fills in the region that is inputted with the given color. Creates a black border 
     *    around the shape.
     * Parameters:
     *  - pixels: The pixels of the Mondrian Image
     *            Must be non-null
     *  - x1: The x-coordinate of the top left corner of the section.
     *  - y1: The y-coordinate of the top left corner of the section.
     *  - height: The x-coordinate of the bottom right corner of the section.
     *  - width: The y-coordinate of the bottom right corner of the section.
     *  - color: The color that the section is going to be filled in.
     *           Must be non-null
     * Returns:
     *  - N/A
     * Exceptions:
     *  - N/A 
     */
    
    private void fill(Color[][] pixels, int x1, int x2, int y1, int y2, Color color) {
        for(int row = x1+1; row < x2-1; row++) {
            for (int col = y1+1; col < y2-1; col++) {
                pixels[row][col] = color;
            }
        }
    }
}
