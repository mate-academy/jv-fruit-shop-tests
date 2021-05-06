package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.interfaces.FileReaderService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String INCORRECT_PATH = "main/fail.csv";
    private static final String CORRECT_PATH = "src/test/resources/readFile.csv";
    private FileReaderService fileReaderService;

    @Before
    public void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_readFromIncorrectPath_NotOk() {
        fileReaderService.readFromFile(INCORRECT_PATH);
    }

    @Test
    public void readFromFile_readFromCorrectPath_Ok() {
        List<String> actual = fileReaderService.readFromFile(CORRECT_PATH);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5",
                "s,banana,50");
        assertEquals(expected, actual);
    }
}
