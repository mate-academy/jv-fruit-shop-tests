package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void fileWriter_usualState_ok() {
        String expectedOutputPath = "src/test/resources/expectedOutput.csv";
        String actualOutputPath = "src/test/resources/actualOutput.csv";
        String data = new StringBuilder()
                .append("fruit").append(",")
                .append("quantity").append(System.lineSeparator())
                .append("apple").append(",")
                .append("15").append(System.lineSeparator())
                .toString();
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new java.io.FileWriter(expectedOutputPath))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Cant write data to file" + expectedOutputPath, e);
        }
        fileWriter.writeToFile(data, actualOutputPath);
        StringBuilder stringBuilder = new StringBuilder();
        String expected;
        String actual;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(expectedOutputPath))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine).append(System.lineSeparator());
            }
            expected = stringBuilder.toString();
            stringBuilder.setLength(0);
        } catch (IOException e) {
            throw new RuntimeException("Cant read data from file" + expectedOutputPath, e);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(actualOutputPath))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine).append(System.lineSeparator());
            }
            actual = stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Cant read data from file" + expectedOutputPath, e);
        }

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_invalidPath_notOk() {
        fileWriter.writeToFile("sdfs", "sdfdfsd");
    }
}
