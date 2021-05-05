package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.interfaces.FileReaderService;
import java.util.List;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private final FileReaderService fileReaderService = new FileReaderServiceImpl();
    private final String incorrectPath = "main/fail.csv";
    private final String correctPath = "src/test/resources/readFile.csv";

    @Test (expected = RuntimeException.class)
    public void readFromFile_readFromIncorrectPath_NotOk() {
        fileReaderService.readFromFile(incorrectPath);
    }

    @Test
    public void readFromFile_readFromCorrectPath_Ok() {
        List<String> actual = fileReaderService.readFromFile(correctPath);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5",
                "s,banana,50");
        assertEquals(expected, actual);
    }
}
