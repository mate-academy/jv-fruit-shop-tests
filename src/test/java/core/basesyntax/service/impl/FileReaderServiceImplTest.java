package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String PATH_TO_VALID_INPUT_DATA
            = "src/test/resources/validInput.csv";
    private static final String INVALID_PATH_TO_INPUT_DATA
            = "src/test/resources/invalidPath.csv";
    private static FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validInputData_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.readFromFile(PATH_TO_VALID_INPUT_DATA);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_invalidPathToInputData_NotOk() {
        fileReaderService.readFromFile(INVALID_PATH_TO_INPUT_DATA);
    }
}
