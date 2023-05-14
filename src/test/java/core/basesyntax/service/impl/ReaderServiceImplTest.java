package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String READER_TEST_FILE_PATH = "src/test/java/resources/data.csv";
    private static final String TEST_EMPTY_FILE_PATH = "src/test/java/resources/EmptyTest.csv";
    private static final String WRONG_FILE_PATH = "wrong";
    private static ReaderService readerService;

    @BeforeClass
    public static void init() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_ValidData_Ok() {
        List<String> expected = List.of("b,apple,10","b,banana,20");
        List<String> actual = readerService.readFromFile(READER_TEST_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_EmptyData_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFromFile(TEST_EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_WrongPath_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(WRONG_FILE_PATH));
        String expectedMessage = "Can't read file from " + WRONG_FILE_PATH;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
