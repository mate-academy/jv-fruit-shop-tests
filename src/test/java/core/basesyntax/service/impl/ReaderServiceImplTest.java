package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    public static final String INPUT_FILE_PATH = "src/test/java/resources/fruits.csv";
    public static final String INPUT_EMPTY_FILE_PATH = "src/test/java/resources/empty_file.csv";
    private ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readerService_EmptyPath_NotOk() {
        readerService.readFromFile("");
    }

    @Test
    public void readerService_EmptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = readerService.readFromFile(INPUT_EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readerService_OkFile_Ok() {
        Assert.assertNotNull(readerService.readFromFile(INPUT_FILE_PATH));
    }
}
