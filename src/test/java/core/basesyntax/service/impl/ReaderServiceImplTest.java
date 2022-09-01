package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FRUIT_EMPTY_FILE = "src/test/resources/empty_fruits_info.csv";
    private static final String FRUIT_FILE = "src/test/resources/fruits_info_test.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullFileName_NotOk() {
        readerService.readFromFile(null);
    }

    @Test
    public void readFromFile_emptyFile_Ok() {
        List<String> expected = List.of();
        List<String> actual = readerService.readFromFile(FRUIT_EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_filledFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> actual = readerService.readFromFile(FRUIT_FILE);
        assertEquals(expected, actual);
    }
}
