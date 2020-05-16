package org.piyush.samples.paint;

import java.io.IOException;

public interface Canvas {

    /**
     * prints the canvas
     */
    void print();

    /**
     * Draws a line on the canvas
     * @param x1 starting x co-ordinate
     * @param y1 starting y co-ordinate
     * @param x2 ending x co-ordinate
     * @param y2 ending y co-ordinate
     */
    void drawLine(int x1, int y1, int x2, int y2); //draw a line from co-ordinates (x1,y1) to (x2,y2)

    /**
     * Draws a rectangle on the Canvas
     * @param x1 starting x co-ordinate
     * @param y1 starting y co-ordinate
     * @param x2 ending x co-ordinate
     * @param y2 ending y co-ordinate
     */
    void drawRectangle(int x1, int y1, int x2, int y2);

    /**
     * Saves this canvas to a file
     * @param filePath path of file (absolute path or relative to current directory)
     * @throws IOException
     */
    void saveToFile(String filePath) throws IOException;

    /**
     * Reads a file into this canvas
     * @param filePath path of file (absolute path or relative to current directory)
     * @throws IOException
     */
    void readFromFile(String filePath) throws IOException;

    /**
     * Undoes the previous operation and restores the canvas to previous saved snapshot
     */
    void undoLastCommand();
}
