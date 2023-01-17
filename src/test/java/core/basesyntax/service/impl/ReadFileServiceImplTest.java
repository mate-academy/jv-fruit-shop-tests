package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidPathException;
import core.basesyntax.service.ReadFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileServiceImplTest {
    private static final String VALID_CSV_TEST_PATH = "src/test/resources/read_test_data.csv";
    private static final String INVALID_CSV_PATH = "bad path to file";
    private static ReadFileService readFileService;

    @BeforeClass
    public static void setUp() {
        readFileService = new ReadFileServiceImpl();
    }

    @Test
    public void readFromFile_validPath_ok() {
        String expected = "";
        try {
            expected = Files.readString(Path.of(VALID_CSV_TEST_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
        String actual = readFileService.readFromFile(Path.of(VALID_CSV_TEST_PATH));
        Assert.assertEquals("Strings must be equals", expected, actual);
    }

    @Test(expected = InvalidPathException.class)
    public void readFromFile_invalidPath_notOk() {
        readFileService.readFromFile(Path.of(INVALID_CSV_PATH));
    }

    @Test(expected = NullPointerException.class)
    public void readFromFile_nullFilePath_notOk() {
        readFileService.readFromFile(Path.of(null));
    }
}
