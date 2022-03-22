package com.walmart.seating;

import com.walmart.seating.exception.InvalidFilePathException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;


public class MovieTheaterTest {
    private File workingDir;
    private String[] testArray;
    private String testId;
    @Before
    public void init() {
        this.workingDir = new File("MovieTheaterSeating/test/file");
    }

    // testO1: check one seat from one cohort
    // test02: check multiple seat holding in one row from one cohort
    // test03: check the invalid input
    // test04: check one seat from multiple cohort (3 space works)
    // test05: check multiple seat (more than one row) from one cohort
    // test06: check multiple seat (more than one row) from multiple cohort
    // test07: check multiple seat (with extreme request) from multiple cohort.If customer A request exceed the avaliable seat, it will not affect other customers request who order behind customer A.
    // test08: check request with maximum occupation
    // test09: check request exceed capcacity
    // test10: check multiple seat (with several extreme request) from multiple cohort
    @Test
    public void totalTest() throws FileNotFoundException{
        testArray = new String[] {"01", "02", "04", "05", "06", "07", "08", "09", "10"};
        for(int i = 0; i < testArray.length; i++){
            testId = testArray[i];
            individualTest();
        }
    }

    @Test
    public void individualTest() throws FileNotFoundException {
        testId = "10";
        String inputFileName = "input" + testId + ".txt";
        String expectedFileName = "expected" + testId + ".txt";
        String outputFileName = "output" + testId + ".txt";

        try{
            String[] args = new String[2];
            args[0] = workingDir.getPath() +  "/input/" + inputFileName;
            args[1] = workingDir.getPath() + "/output/" + outputFileName;
            MovieTheaterDriver.main(args);
            Path expectedPath = Paths.get(workingDir.getPath() + "/expected/" + expectedFileName);
            Path outputPath = Paths.get(workingDir.getPath() + "/output/" + outputFileName);
            String expectedResultStr = Files.readString(expectedPath);
            String outputResultStr = Files.readString(outputPath);
            assertEquals(expectedResultStr, outputResultStr);

        }  catch (InvalidFilePathException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
