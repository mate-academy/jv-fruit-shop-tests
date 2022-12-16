package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService reader;
    private static final String PATH_OF_TEST_FILE = "src/main/resources/testReader.csv";

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NullFile_NotOk() {
        reader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotExistedFile_NotOk() {
        reader.readFromFile("src/main/resources/notExistedFile.csv");
    }

    @Test
    public void readFromFile_File_Ok() {
        List<String> expected;
        String data = new StringBuilder().append("apple")
                .append(System.lineSeparator()).append("peach").toString();
        Path path = Path.of(PATH_OF_TEST_FILE);
        try {
            Files.write(path, data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            expected = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> actual = reader.readFromFile(PATH_OF_TEST_FILE);
        Assert.assertEquals(expected, actual);
    }
}
