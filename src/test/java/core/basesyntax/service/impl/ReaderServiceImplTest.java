package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFile_correctPath_isOk() {
        List<String> actual = readerService.read("src/test/resources/inputfile.csv");
        List<String> expected = List.of("b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");

        assertEquals(expected, actual);
    }

    @Test
    void readFile_emptyFileWithTitle_isOk() {
        List<String> actual = readerService.read("src/test/resources/titleOnlyFile.csv");
        List<String> expected = List.of();

        assertEquals(expected, actual);
    }

    @Test
    void readFile_emptyFileWithNoTitle_isOk() {
        List<String> actual = readerService.read("src/test/resources/emptyFile.csv");
        List<String> expected = List.of();

        assertEquals(expected, actual);
    }

    @Test
    void readFile_unknownPath_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.read("src/test/randomFolder/randomFile.csv"));
    }

    @Test
    void readFile_nullInPath_isNotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> readerService.read(null));
    }
}
