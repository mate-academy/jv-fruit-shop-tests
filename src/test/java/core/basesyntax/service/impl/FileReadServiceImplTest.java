package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReadService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadServiceImplTest {
    private static final String READER_FROM_CORRECT_PATH
            = "src/test/java/resources/inputCorrectData.csv";
    private static final String READER_EMPTY_DATA_FROM_PATH
            = "src/test/java/resources/emptyFile.csv";
    private static final String READER_FROM_WRONG_PATH
            = "src/test/java/resources/wrong.csv";
    private static FileReadService fileReadService;

    @BeforeClass
    public static void init() {
        fileReadService = new FilerReadServiceImpl();
    }

    @Test
    public void readFromFile_WithValidData_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReadService.readFromFile(READER_FROM_CORRECT_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_EmptyData_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReadService.readFromFile(READER_EMPTY_DATA_FROM_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_WrongPath_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> fileReadService.readFromFile(READER_FROM_WRONG_PATH));
        String expectedMessage = "Can't read file " + READER_FROM_WRONG_PATH;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
