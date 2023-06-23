package fruit.shop.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import fruit.shop.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INPUT1 = "src/test/resources/input.csv";
    private static final String INPUT2 = "src/test/resources/empty_input.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void getRecords_correctFile_Ok() {
        List<String> expected = List.of("test1", "test2", "", "empty line above");
        assertEquals(expected, readerService.getRecords(INPUT1));
    }

    @Test
    void getRecords_incorrectFileName_NotOk() {
        assertThrows(RuntimeException.class, () -> readerService.getRecords("input"));
    }

    @Test
    void getRecords_emptyFile_Ok() {
        List<String> expected = List.of();
        assertEquals(expected, readerService.getRecords(INPUT2));
    }
}
