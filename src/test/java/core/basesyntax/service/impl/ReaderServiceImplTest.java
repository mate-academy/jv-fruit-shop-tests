package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private String filePath;

    @BeforeClass
    public static void setUpBeforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void read_FilePathIsNull_notOk() {
        filePath = null;
        readerService.read(filePath);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentFile_notOk() {
        filePath = "src/test/resources/input";
        readerService.read(filePath);
    }

    @Test
    public void read_EmptyFile_ok() {
        filePath = "src/test/resources/inputEmpty";
        List<String> actual = readerService.read(filePath);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void read_FileHasOnlyHeader_ok() {
        filePath = "src/test/resources/inputHeaderOnly";
        List<String> actual = readerService.read(filePath);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void read_FileHasValidContent_ok() {
        List<String> expected = List.of(
                "b,banana,20",
                "b,apple,100");
        filePath = "src/test/resources/inputValid";
        List<String> actual = readerService.read(filePath);
        assertEquals(expected, actual);
    }
}
