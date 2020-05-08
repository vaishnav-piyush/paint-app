package org.piyush.samples.paint;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AsciiCanvas implements Canvas {

    public static final char DEFAULT_BACKGROUND_CHAR = ' ';
    public static final char DEFAULT_LINE_CHAR = 'x';
    public static final char HORIZONTAL_BORDER_CHAR = '-';
    public static final char VERTICAL_BORDER_CHAR = '|';

    private int width, height;
    private char backgroundChar = DEFAULT_BACKGROUND_CHAR;
    private char lineChar = DEFAULT_LINE_CHAR;
    private char[][] canvas;

    public AsciiCanvas(int width, int height, char backgroundChar, char lineChar) {
        this.backgroundChar = backgroundChar;
        this.lineChar = lineChar;
        this.width = width;
        this.height = height;
        canvas = new char[height][width];
        this.fillBackgroud();
    }

    public AsciiCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new char[height][width];
        this.fillBackgroud();
    }

    /**
     * fills a canvas with its background char. Can also be used as an erase canvas method.
     */
    private void fillBackgroud() {
        //add background character
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                canvas[y][x] = backgroundChar;
            }
        }
    }

    //returns a string equivalent to the horizontal border of canvas
    private String getHorizontalBorder() {
        return String.join("", Collections.nCopies(width + 2, String.valueOf(HORIZONTAL_BORDER_CHAR)));
    }


     //returns true if the given co-ordinates fit into the canvas, else false
    private boolean fitsInCanvas(int x1, int y1, int x2, int y2) {
        return x1 >= 1 && x1 <= width &&
                x2 >= 1 && x2 <= width &&
                y1 >= 1 && y1 <= height &&
                y2 >= 1 && y2 <= height;
    }

    @Override
    public void print() {
        //first print top border
        System.out.println(getHorizontalBorder());
        //then print one row at a time
        for (int y = 0; y < height; y++) {
            System.out.print(VERTICAL_BORDER_CHAR);
            System.out.print(String.valueOf(canvas[y]));
            System.out.println(VERTICAL_BORDER_CHAR);
        }
        //finally print the bottom border
        System.out.println(getHorizontalBorder());
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        //check if co-ordinates fit into the canvas
        if (!fitsInCanvas(x1, y1, x2, y2))
            throw new IllegalArgumentException(String.format("Co-ordinates should be within the canvas (1, 1), (%d, %d)", width, height));

        //replace numbers if lower co-ordinates are specified first
        //this is to make sure that the loops function both ways
        if (x1 > x2) {
            int t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y1 > y2) {
            int t = y1;
            y1 = y2;
            y2 = t;
        }

        if (y1 == y2) { //horizontal line
            for (int x = x1 - 1; x < x2; x++)
                canvas[y1 - 1][x] = lineChar;
        } else if (x1 == x2) { //vertical line
            for (int y = y1 - 1; y < y2; y++)
                canvas[y][x1 - 1] = lineChar;
        } else {
            throw new IllegalArgumentException("Only horizontal and vertical lines are supported.");
        }
    }

    @Override
    public void drawRectangle(int x1, int y1, int x2, int y2) {
        //draw the 4 lines of a rectangle
        drawLine(x1, y1, x2, y1);
        drawLine(x1, y1, x1, y2);
        drawLine(x1, y2, x2, y2);
        drawLine(x2, y1, x2, y2);
    }

    @Override
    public String toString() {
        //note this method is not adding borders to the canvas. So it shouldn't be used to print it on command line.
        String str = "";
        for (int y = 0; y < height; y++) {
            str = str + String.valueOf(canvas[y]) + "\n";
        }
        return str;
    }

    @Override
    public void saveToFile(String filePath) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        //save width, height, backgroundChar and lineChar in line 1 delimited by ###
        fw.write(String.format("%d###%d###%c###%c\n", width, height, backgroundChar, lineChar));
        //next write the string equivalent of canvas
        fw.write(this.toString());
        fw.close();
    }

    @Override
    public void readFromFile(String filePath) throws IOException {
        BufferedReader fr = Files.newBufferedReader(Paths.get(filePath));
        int y = 0;
        String line = fr.readLine();
        //width, height, backgroundChar and lineChar are read from line 1 delimited by a ###
        List<String> tokens = Arrays.asList(line.split("###"));
        try {
            width = Integer.parseInt(tokens.get(0)); //first word is width
            height = Integer.parseInt(tokens.get(1)); //next word is height
            backgroundChar = (char) tokens.get(2).charAt(0); //next is background character
            lineChar = (char) tokens.get(3).charAt(0); //next is line char
        } catch (Exception e) {
            throw new IllegalArgumentException("Not a valid canvas file.");
        }
        //initialize the 2d canvas array
        canvas = new char[height][width];
        while ((line = fr.readLine()) != null) {
            //if number of lines in file is greater than the height of canvas
            //or if length of any line is not equal to width of canvas
            //then raise an exception
            if (y >= height || line.length() != width)
                throw new IllegalArgumentException("Not a valid canvas file.");

            canvas[y] = line.toCharArray();
            y++;
        }
        fr.close();
    }

}
