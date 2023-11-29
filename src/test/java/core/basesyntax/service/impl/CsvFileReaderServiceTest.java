package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileReaderServiceTest {

    private static CsvFileReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvFileReaderService();
    }

    @Test
    void readFile_Ok() {
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"type", "fruit", "quantity"});
        expected.add(new String[]{"b", "banana", "20"});
        List<String[]> actual = readerService.readFile("src/test/resources/input-ok.csv");
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertArrayEquals(actual.get(i), expected.get(i));
        }
    }

    @Test
    void readFile_NoSuchFile_NotOk() {
        String fileName = "src/test/resources/input-no-such-file.csv";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> readerService.readFile("src/test/resources/input-no-such-file.csv"));
        assertEquals(exception.getMessage(), "File not found " + fileName);
    }
}
