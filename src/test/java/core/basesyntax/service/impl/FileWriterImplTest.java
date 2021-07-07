package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @AfterClass
    public static void afterClass() {
        File file = new File("src/test/java/testresources/bnbv.csv");
        file.delete();
        File fileFoCreateAndWrite = new File("src/test/java/testresources/test_report.csv");
        fileFoCreateAndWrite.delete();
    }

    @Test(expected = RuntimeException.class)
    public void writeNullData_NotOk() {
        String data = null;
        fileWriter.writeToFile(data, "src/test/java/testresources/bnbv.csv");
    }

    @Test(expected = RuntimeException.class)
    public void writeNullFileName_NotOk() {
        String fileName = null;
        fileWriter.writeToFile("smth", fileName);
    }

    @Test
    public void createAndWriteToFile_Ok() {
        String fileName = "src/test/java/testresources/test_report.csv";
        String expected = "Test line!";
        fileWriter.writeToFile(expected, fileName);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't find file.");
        }
        String actual = null;
        try {
            actual = bufferedReader.readLine().toString();
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file:" + fileName);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToIncorrectFile_NotOk() {
        String fileName = "";
        String data = "Test line!";
        fileWriter.writeToFile(data, fileName);
    }
}
