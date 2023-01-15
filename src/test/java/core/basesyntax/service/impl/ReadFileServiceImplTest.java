package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidPathException;
import core.basesyntax.service.ReadFileService;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class ReadFileServiceImplTest {
    private static final String VALID_CSV_TEST_PATH = "src/test/resources/test_data.csv";
    ReadFileService readFileService = new ReadFileServiceImpl();

    @Test
    public void readFromFile_validPath_ok() {
        String expected = "Just some string";
        String actual = readFileService.readFromFile(Path.of(VALID_CSV_TEST_PATH));
        Assert.assertEquals("Strings must be equals", expected, actual);
    }

    @Test(expected = InvalidPathException.class)
    public void readFromFile_invalidPath_notOk() {
        readFileService.readFromFile(Path.of("bad path"));
    }

}