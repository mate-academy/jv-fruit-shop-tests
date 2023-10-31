package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceTest {
    private static final String PATH = "src/test/resources/transactions.csv";
    private static final String WRONG_PATH = "src/test/resources/newTransactions.csv";
    private static FileReaderService fileReaderService;
    private static List<String> expectedList;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        expectedList.add("p,apple,20");
        expectedList.add("p,banana,5");
        expectedList.add("s,banana,50");
    }

    @Test
    void readFromFile_validFilePath_Ok() {
        List<String> actual = fileReaderService.readFromFile(PATH);
        assertEquals(expectedList, actual);
    }

    @Test
    void readFromFile_inValidFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile(WRONG_PATH));
    }
}
