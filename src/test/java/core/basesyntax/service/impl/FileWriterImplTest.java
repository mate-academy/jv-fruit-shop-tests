package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
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
        String actualOutputPath = "src/test/resources/actualOutput.csv";
        String expected = new StringBuilder()
                .append("fruit").append(",")
                .append("quantity").append(System.lineSeparator())
                .append("apple").append(",")
                .append("15").append(System.lineSeparator())
                .toString();
        fileWriter.writeToFile(expected, actualOutputPath);
        StringBuilder stringBuilder = new StringBuilder();
        String actual;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(actualOutputPath))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine).append(System.lineSeparator());
            }
            actual = stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Cant read data from file" + actualOutputPath, e);
        }
        Assert.assertEquals(expected, actual);
    }
}
