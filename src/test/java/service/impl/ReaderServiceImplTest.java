package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ReaderService;

class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_validFile_Ok() {
        final String pathFile = "src/test/resources/goodFile.csv";
        final List<String> actual = readerService.readFromFile(pathFile);
        final List<String> expected = Arrays.asList("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        assertEquals(expected, actual);
    }

    @Test
    void readFromFile_wrongPath_notOk() {
        final String pathFile = "src/test/resources/g.csv";
        assertThrows(RuntimeException.class, () -> readerService.readFromFile(pathFile));
    }

    @Test
    void readFromFile_pathNull_notOk() {
        assertThrows(NullPointerException.class, () -> readerService.readFromFile(null));
    }
}
