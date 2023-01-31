package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/main/java/resources/input.csv";
    private static final String NOT_EXIST_FILE_PATH = "notexist.csv";
    private final ReaderService readerService = new ReaderServiceImpl();

    @org.testng.annotations.Test
    public void readData_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        expectedList.add("p,apple,20");
        expectedList.add("p,banana,5");
        expectedList.add("s,banana,50");
        List<String> actual = readerService.readData(FILE_PATH);
        assertEquals(expectedList, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromFile_InvalidPath_NotOk() {
        readerService.readData(NOT_EXIST_FILE_PATH);
    }
}
