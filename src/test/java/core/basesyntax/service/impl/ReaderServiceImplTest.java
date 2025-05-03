package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderServiceImpl readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> actual = readerService.readFromFile("src/test/resources/input.csv");
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void wrongPath_notOk() {
        readerService.readFromFile("wrong path");
    }

    @Test (expected = RuntimeException.class)
    public void nullPath_notOk() {
        readerService.readFromFile(null);
    }
}
