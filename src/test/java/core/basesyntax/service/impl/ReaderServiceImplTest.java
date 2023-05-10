package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    void getInformationFromFile_Ok() {
        final List<String> actual = readerService.getInformationFromFile(
                "src/test/resources/data.txt");
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
    void getInformationFromFile_WrongPath_NotOk() {
        String path = "src/test/resources/data0.txt";
        assertThrows(RuntimeException.class, () -> readerService.getInformationFromFile(path));
    }

    @Test
    void getInformationFromFile_PathIsNull_NotOk() {
        String path = null;
        assertThrows(RuntimeException.class, () -> readerService.getInformationFromFile(path));
    }

    @Test
    void getInformationFromFile_EmptyFile_NotOk() {
        String path = "src/test/resources/emptyFile.txt";
        assertThrows(RuntimeException.class, () -> readerService.getInformationFromFile(path));
    }
}
