package core.basesyntax.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static WriterImpl writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
    }

    @Test
    public void write_correctPath_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,1";
        writer.writeToFile("src/test/resources/output.csv", expected);
        String actual = readFromFile("src/test/resources/output.csv");
        Assert.assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void write_incorrectFilePath_notOk() {
        writer.writeToFile("", "abc");
    }

    private String readFromFile(String filePath) {
        File file = new File(filePath);
        StringBuilder textFromFile = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textFromFile.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + filePath, e);
        }
        return textFromFile.toString().trim();
    }
}
