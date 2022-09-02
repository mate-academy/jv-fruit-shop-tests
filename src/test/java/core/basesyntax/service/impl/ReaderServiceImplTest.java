package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String TEST_FILE_PATH = "src/test/resources/read.csv";
    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void read_File_OK() throws IOException {
        List<String> expected = Files.readAllLines(Path.of(TEST_FILE_PATH));
        List<String> actual = readerService.readFromFile(TEST_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void read_File_NotOK() {
        List<String> actual = readerService.readFromFile(null);
    }
}
