package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String INPUT_FILE_OK = "src/test/resources/shop_operations_test_ok.csv";
    private static final String INPUT_FILE_NOT_OK
            = "src/test/resources/shop_operations_test_not_ok.csv";
    private static final String INPUT_FILE_DOES_NOT_EXIST = "";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = NullPointerException.class)
    public void read_nullFile_NotOk() {
        fileReader.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_fileDoesNotExist_NotOk() {
        fileReader.read(INPUT_FILE_DOES_NOT_EXIST);
    }

    @Test
    public void read_fileExists_Ok() {
        fileReader.read(INPUT_FILE_OK);
    }

    @Test
    public void read_sameContent_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(INPUT_FILE_OK));
        } catch (IOException e) {
            throw new RuntimeException("no such file");
        }
        List<String> actual = fileReader.read(INPUT_FILE_OK);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void read_differentContent_NotOk() {
        List<String> expected = fileReader.read(INPUT_FILE_NOT_OK);
        List<String> actual = fileReader.read(INPUT_FILE_OK);
        Assert.assertNotEquals(expected, actual);
    }
}

