package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileWriter;

public class FileWriterImplTest {
    private static final String FILE_PATH = "src/test/resources//testWriteFile.csv";
    private static FileWriter writer;

    @BeforeClass
    public static void init() {
        writer = new FileWriterImpl();
    }

    @Test
    public void write_file_ok() {
        String actual = "Hello World";
        writer.write(actual, FILE_PATH);
        String expected = null;
        try {
            expected = Files.readString(Path.of(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void clearFile() {
        try {
            Files.deleteIfExists(Path.of(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
