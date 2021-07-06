package core.basesyntax;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterImplTest {
    @Test
    public void writer_fileCreated_Ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String fileName = "src/test/resources/result_test.csv";
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(fileName, report);
        Assert.assertTrue(Files.exists(Path.of(fileName)));
    }

    @Test
    public void writer_fileWritten_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String fileName = "src/test/resources/result_test.csv";
        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.writeToFile(fileName, expected);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(fileName));
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
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        new FileWriterImpl()
                .writeToFile("", report);
    }
}
