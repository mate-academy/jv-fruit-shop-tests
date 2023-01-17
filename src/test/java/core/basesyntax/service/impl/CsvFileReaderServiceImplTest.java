package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/file.csv";
    private static CsvFileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test
    public void fileReader_filePathRight_Ok() {
        String expected = "";
        Path path = Path.of(VALID_FILE_PATH);
        try {
            expected = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file");
        }
        String actual = fileReaderService.readFromFile(path);
        Assert.assertEquals("Result is not equal", expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void fileReader_invalidFilePath_NotOk() {
        fileReaderService.readFromFile(Path.of("src/test/resources/fl.csv"));
    }

    @Test (expected = NullPointerException.class)
    public void fileReader_nullFilePath_NotOk() {
        fileReaderService.readFromFile(Path.of(null));
    }
}
