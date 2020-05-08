package org.piyush.samples.paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandLineRunner implements Runnable {

    private Canvas canvas;
    Scanner commandPrompt = new Scanner(System.in);

    /**
     * If the command is invalid, it prints the error message on command line
     * @return true if the current command is valid else false
     */
    public static boolean isValidCommand(List<String> command) {
        String operation = command.get(0);
        switch (operation) {
            case "C":
                //make sure two more arguments,
                // other possible checks not yet covered, for eg. if the arguments are all integers
                if(command.size() != 3) {
                    System.out.println("Invalid number of arguments");
                    return false;
                }
                return true;
            case "L":
            case "R":
                //make sure 4 arguments,
                // other possible checks not yet covered, for eg. if the arguments are all integers
                if(command.size() != 5) {
                    System.out.println("Invalid number of arguments");
                    return false;
                }
                return true;
            case "S":
            case "O":
                //make sure 1 argument
                if(command.size() != 2) {
                    System.out.println("Invalid number of arguments");
                    return false;
                }
                return true;
            case "P":
            case "Q":
                return true;
            default:
                System.out.println("Unsupported command");
                return false;
        }
    }

    /**
     * Prints command line help
     */
    public static void printCommandLineHelp() {
        System.out.println("Command Line Help");
        System.out.println("**************************************************************");
        System.out.println("Create canvas: C width height (Example: C 20 10)");
        System.out.println("Create a line: L x1 y1 x2 y2 (Example horizontal line: L 2 3 4 3. Example vertical line: L 2 3 2 8)");
        System.out.println("Create a rectangle: R x1 y1 x2 y2 (Example: R 15 2 20 5)");
        System.out.println("Save to file: S filePath (Example: S /file/path)");
        System.out.println("Open file: O filePath (Example: O /file/path)");
        System.out.println("Print canvas: P");
        System.out.println("Quit: Q");
        System.out.println("**************************************************************");
    }

    //gets the next command from command prompt
    public List<String> inputNextCommand() {
        System.out.print("\nEnter command: ");
        String cmdStr = commandPrompt.nextLine();
        return Arrays.asList(cmdStr.split(" "));
    }

    /**
     * Runs the command line paint application
     */
    @Override
    public void run() {
        List<String> command = null;
        boolean quitProgram = false;
        //loop ends when the command entered is Q
        while (!quitProgram) {
            command = inputNextCommand();
            //check if command is valid
            if (!isValidCommand(command)) {
                printCommandLineHelp();
                continue;
            }
            try {
                switch (command.get(0)) {
                    case "C":
                        //create a new canvas
                        canvas = new AsciiCanvas(Integer.parseInt(command.get(1)), Integer.parseInt(command.get(2)));
                        canvas.print();
                        break;

                    case "L":
                        if (null == canvas) {
                            System.out.println("Please create a canvas first");
                            break;
                        }
                        //draw line
                        canvas.drawLine(Integer.parseInt(command.get(1)), Integer.parseInt(command.get(2)),
                                Integer.parseInt(command.get(3)), Integer.parseInt(command.get(4)));
                        canvas.print();
                        break;

                    case "R":
                        if (null == canvas) {
                            System.out.println("Please create a canvas first");
                            break;
                        }
                        //draw rectangle
                        canvas.drawRectangle(Integer.parseInt(command.get(1)), Integer.parseInt(command.get(2)),
                                Integer.parseInt(command.get(3)), Integer.parseInt(command.get(4)));
                        canvas.print();
                        break;

                    case "S":
                        if (null == canvas) {
                            System.out.println("Please create a canvas first");
                            break;
                        }
                        //save canvas to file
                        canvas.saveToFile(command.get(1));
                        System.out.println(String.format("Canvas saved to %s", command.get(1)));
                        break;

                    case "O":
                        //if canvas is not initialized, create a dummy canvas
                        if (null == canvas) {
                            canvas = new AsciiCanvas(1, 1);
                        }
                        //read canvas from file
                        canvas.readFromFile(command.get(1));
                        canvas.print();
                        break;

                    case "Q":
                        quitProgram = true;
                        break;

                    case "P":
                        if (null == canvas) {
                            System.out.println("Please create a canvas first");
                            break;
                        }
                        canvas.print();
                        break;

                    default:
                        printCommandLineHelp();
                        break;
                }
            } catch(NumberFormatException e) {
                System.out.println(String.format("Invalid arguments. Numbers should be integers: %s ", e.getMessage()));
                printCommandLineHelp();
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(String.format("Invalid arguments: %s ", e.getMessage()));
                printCommandLineHelp();
                continue;
            } catch (IOException e) {
                System.out.println(String.format("File read/write error: %s", e.toString()));
                continue;
            } catch (Exception e) {
                System.out.println("Oops...Unexpected exception. " + e.toString());
                System.out.println("Maybe start again by creating a fresh canvas.");
                continue;
            }
        }
        //close the scanner before exit
        commandPrompt.close();
    }
}
