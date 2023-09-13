package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderServiceTest {
    private static final String CORRECT_INPUT_DATA_FILEPATH = "src/test/resources/test.csv";
    private static final String INCORRECT_INPUT_DATA_FILEPATH = "src/test/resources/incorrect.csv";
    private static FileReaderService fileReaderService;
    private static List<String> expectedInputList;

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
        expectedInputList = new ArrayList<>();
        expectedInputList.add("type,fruit,quantity");
        expectedInputList.add("b,apple,60");
        expectedInputList.add("b,banana,50");
        expectedInputList.add("s,banana,100");
        expectedInputList.add("p,banana,50");
        expectedInputList.add("r,apple,30");
        expectedInputList.add("s,banana,200");
    }

    @Test
    void readFromFile_correctFilePathReading_Ok() {
        List<String> actual = fileReaderService.readFromFile(CORRECT_INPUT_DATA_FILEPATH);
        assertEquals(expectedInputList, actual);
    }

    @Test
    void readFromFile_NotExistingFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(INCORRECT_INPUT_DATA_FILEPATH));
    }
}
