package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static ReaderService readerService;
    private static final String FILE_NAME = "src/test/resources/test.csv";
    private static List<String> DEFAULT_DATA;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
        DEFAULT_DATA = List.of("b,banana,20",
                "b,apple,20",
                "p,banana,3",
                "p,apple,5",
                "r,banana,3",
                "s,apple,15",
                "p,banana,11");
    }

    @Test
    public void readreaderService_readFromFile_isOk() {
        List<String> actual = readerService.readFromFile(FILE_NAME);
        assertEquals(DEFAULT_DATA, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readreaderService_validFileName_NotOk() {
        readerService.readFromFile("WrongName.csv");
    }

}
