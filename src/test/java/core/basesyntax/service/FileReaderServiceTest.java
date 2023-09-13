package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceTest {
    private static final String CORRECT_PATH = "src/test/resources/transactions.csv";
    private static final String INCORRECT_PATH = "src/test/resources/newTransactions.csv";
    private static FileReaderService fileReaderService;
    private static List<String> expectedList;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,130");
        expectedList.add("s,banana,100");
        expectedList.add("s,apple,50");
        expectedList.add("p,banana,40");
        expectedList.add("p,apple,40");
        expectedList.add("r,apple,20");
        expectedList.add("r,banana,30");
    }

    @Test
    void readFromFile_validFilePath_Ok() {
        List<String> actual = fileReaderService.readFromFile(CORRECT_PATH);
        assertEquals(expectedList, actual);
    }

    @Test
    void readFromFile_inValidFilePath_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReaderService.readFromFile(INCORRECT_PATH));
    }
}
