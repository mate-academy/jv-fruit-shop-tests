package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/TestInputData";
    private static final String INVALID_PATH = "src/test/resources/TestData";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/testEmptyFile";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReaderService.readFromFile(VALID_PATH);;
        assertEquals(expected, actual);
    }

    @Test
    public void readFromEmptyFile_ok() {
        List<String> expected = List.of();
        fileReaderService.readFromFile(PATH_TO_EMPTY_FILE);
        List<String> actual = fileReaderService.readFromFile(PATH_TO_EMPTY_FILE);;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFileInvalidPath_notOk() {
        fileReaderService.readFromFile(INVALID_PATH);
    }
}
