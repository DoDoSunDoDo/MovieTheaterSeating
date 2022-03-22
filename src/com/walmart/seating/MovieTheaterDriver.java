package com.walmart.seating;

import com.walmart.seating.exception.InvalidFilePathException;
import com.walmart.seating.service.MovieTheater;

import java.io.*;
import java.security.InvalidParameterException;

public class MovieTheaterDriver {
    public static void main(String[] args) throws InvalidFilePathException {

        //sanity check: whether the argument is valid
        //args[0] is input file address and args[1] is output file address
        if(args.length != 2) {
            throw new InvalidFilePathException();
        }

        //initialize MovieTheater
        MovieTheater mt = new MovieTheater(10, 20);

        try(FileReader file = new FileReader(args[0])){
            BufferedReader inputFile = new BufferedReader(file);
            PrintWriter outputFile = new PrintWriter(args[1]);
            String readLine;
            while ((readLine = inputFile.readLine()) != null) {
                //read input file. eg "R001 2"
                String[] input = readLine.split(" ");

                //
                if(input.length != 2){
                    throw new InvalidParameterException("Please type in valid booking number!");
                }

                //reserve seat in theater
                String reservedPosition = mt.reserve(Integer.parseInt(input[1]));

                //If remaining seats is not enough, add warning message
                if (reservedPosition == null || reservedPosition.length() == 0) {
                    outputFile.append(input[0] + " not enough seat for this reservation!");
                    outputFile.append("\n");
                } else {
                    //If seats are avaliable, put results in file
                    outputFile.append(input[0] + " " + reservedPosition);
                    outputFile.append("\n");
                }
            }
            outputFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch(IOException e){
            System.out.println("Input file error");
            e.printStackTrace();
        }
    }
}
