package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readerService_readFromFile_OK() {
        List<String> actual = readerService.readFromFile("src/test/resources/test.csv");
        List<String> expected = List.of("Hallo World!");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_readFromInvalidFilePath_NotOK() {
        readerService.readFromFile("123");
    }

    @Test(expected = RuntimeException.class)
    public void readerService_readFromFilePathNull_NotOK() {
        readerService.readFromFile(null);
    }
}
