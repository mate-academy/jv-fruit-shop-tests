package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FILE_NAME = "src/test/resources/result_test.csv";

    @Test
    public void writer_fileCreated_Ok() {
        String report = "";
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(FILE_NAME, report);
        Assert.assertTrue(Files.exists(Path.of(FILE_NAME)));
    }

    @Test
    public void writer_fileWritten_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,30" + System.lineSeparator()
                + "banana,9";
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(FILE_NAME, expected);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Error while running the test", e);
        }
        StringBuilder resultSB = new StringBuilder();
        for (String s : actual) {
            resultSB.append(s).append(System.lineSeparator());
        }

        Assert.assertEquals(expected, resultSB.toString().trim());
    }

    @Test(expected = RuntimeException.class)
    public void writer_writeToFile_notOk() {
        String report = "";
        new FileWriterImpl().writeToFile("", report);
    }
}
