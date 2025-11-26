package core.basesyntax.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadFileServiceTest {
    private static final String INCORRECT_FILE_PATH = "";
    private static final String CORRECT_FILE_PATH = "src/test/resources/fruits.csv";
    private ReadFileService readFileService;

    @BeforeEach
    public void setUp() throws Exception {
        readFileService = new ReadFileServiceImpl();
    }

    @Test
    public void readFromFile_incorrectPath_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> readFileService.readFromFile(INCORRECT_FILE_PATH),
                "Can't find file by path: " + INCORRECT_FILE_PATH);
    }

    @Test
    public void readFromFile_oK() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");

        List<String> readFileList = readFileService.readFromFile(CORRECT_FILE_PATH);

        Assertions.assertEquals(expected, readFileList);
    }
}
