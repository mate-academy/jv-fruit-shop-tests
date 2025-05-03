package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    public static final String WRONG_FILE_PATH = "src/test/resources/data0.txt";
    public static final String FILE_PATH = "src/test/resources/data.txt";
    public static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.txt";
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void getInformationFromFile_Ok() {
        final List<String> actual = readerService.getInformationFromFile(FILE_PATH);
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        assertEquals(expected, actual);
    }

    @Test
    void getInformationFromFile_nullPath_throwsException() {
        assertThrows(RuntimeException.class, () ->
                readerService.getInformationFromFile(WRONG_FILE_PATH));
    }

    @Test
    void getInformationFromFile_nonExistingPath_throwsException() {
        assertThrows(RuntimeException.class, () ->
                readerService.getInformationFromFile(null));
    }

    @Test
    void getInformationFromFile_EmptyFile_throwsException() {
        assertThrows(RuntimeException.class, () ->
                readerService.getInformationFromFile(EMPTY_FILE_PATH));
    }
}
