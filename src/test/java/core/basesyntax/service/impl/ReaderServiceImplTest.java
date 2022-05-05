package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE = "src/test/resources/inputTest.csv";
    private static final String INVALID_PATH = "src/test/java/invalidPath/inputTest.csv";
    private static final String PATH_WITH_WHITESPACE = "src/test/ resources/ inputTest.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expectedList = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actualList = readerService.read(INPUT_FILE);
        assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void readInvalidPath_notOk() {
        readerService.read(INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readPathWithWhitespace_notOk() {
        readerService.read(PATH_WITH_WHITESPACE);
    }
}
