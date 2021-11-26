package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import service.FileReader;

public class FileReaderImplTest {
    private static final String FILE_PATH = "src/test/resources//testReadFile.csv";
    private FileReader reader;

    {
        reader = new FileReaderImpl();
    }

    @Test
    public void read_file_ok() {
        List<String> actual = reader.read(FILE_PATH);
        List<String> expected = null;
        try {
            expected = Files.readAllLines(Path.of(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void no_file_to_read() throws RuntimeException {
        try {
            reader.read("src/test/resources//testEmptyFile.csv");

        } catch (RuntimeException e) {
            Assert.assertEquals("file not read", e.getMessage());
        }
    }
}
