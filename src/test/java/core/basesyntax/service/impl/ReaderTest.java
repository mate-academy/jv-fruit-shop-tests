package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderTest {
    private static final String VALID_FILE_TO_READ_PATH
            = "src/test/resources/daily_report_test.csv";
    private static final String INVALID_PATH = "";
    private static final String EMPTY_FILE_PATH
            = "src/test/resources/emptyTest.csv";
    private static Reader reader;

    @BeforeClass
    public static void init() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFromValidSource_Ok() {
        Assert.assertNotNull(reader.read(VALID_FILE_TO_READ_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void readFromInvalidSource_NotOk() {
        reader.read(INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readFromEmptyFile_NotOk() {
        reader.read(EMPTY_FILE_PATH);
    }

    @Test
    public void readFromNonEmptyFile_Ok() {
        try {
            List<String> strings = Files.readAllLines(new File(VALID_FILE_TO_READ_PATH).toPath());
            Assert.assertEquals(strings, reader.read(VALID_FILE_TO_READ_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + VALID_FILE_TO_READ_PATH, e);
        }
    }
}
