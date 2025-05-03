package core.basesyntax.service;

import core.basesyntax.service.impl.WriterImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static final String PATH = "src/test/resources/test.csv";
    private static final String MESSAGE = "Hello, mates!";
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
    }

    @Test
    public void write_correctFileName_ok() {
        writer.writeToFile(MESSAGE, PATH);
        Assert.assertEquals(MESSAGE, readFile(PATH));
    }

    @Test
    public void write_twoLine_ok() {
        String message = MESSAGE + System.lineSeparator() + "java = heaven";
        writer.writeToFile(message, PATH);
        Assert.assertEquals(message, readFile(PATH));
    }

    @Test(expected = NullPointerException.class)
    public void write_fileNameIsNull_notOk() {
        writer.writeToFile(MESSAGE, null);
    }

    @Test(expected = NullPointerException.class)
    public void write_messageIsNull_notOk() {
        writer.writeToFile(null, "");
    }

    private String readFile(String path) {
        StringBuilder information = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while (line != null) {
                information.append(line);
                line = reader.readLine();
                if (line != null) {
                    information.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + path, e);
        }
        return information.toString();
    }
}
