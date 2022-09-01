package core.basesyntax.service;

import core.basesyntax.service.impl.WriterImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class WriterImplTest {
    @Test
    public void write_correctFileName_ok() {
        String path = "src/test/resources/test.csv";
        String message = "Hello, mates!";
        new WriterImpl().writeToFile(message, path);
        Assert.assertEquals(message, readFile(path));
    }

    @Test
    public void write_twoLine_ok() {
        String path = "src/test/resources/test.csv";
        String message = "Hello, mates!" + System.lineSeparator() + "java = heaven";
        new WriterImpl().writeToFile(message, path);
        Assert.assertEquals(message, readFile(path));
    }

    @Test(expected = NullPointerException.class)
    public void write_fileNameIsNull_notOk() {
        String information = "Hello, mates!";
        new WriterImpl().writeToFile(information, null);
    }

    @Test(expected = NullPointerException.class)
    public void write_messageIsNull_notOk() {
        new WriterImpl().writeToFile(null, "");
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
