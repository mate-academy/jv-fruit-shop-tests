package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static String inputFile;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        inputFile = "src/test/resources/inputTest.csv";
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
        List<String> actualList = readerService.read(inputFile);
        assertEquals(expectedList, actualList);
    }

    @Test(expected = RuntimeException.class)
    public void readInvalidPath_notOk() {
        readerService.read("src/test/java/invalidPath/inputTest.csv");
    }

    @Test(expected = RuntimeException.class)
    public void readPathWithWhitespace_notOk() {
        readerService.read("src/test/resources/ inputTest.csv");
    }
}
