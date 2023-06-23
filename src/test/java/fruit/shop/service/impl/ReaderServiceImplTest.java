package fruit.shop.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String INPUT1 = "src/test/resources/input.csv";
    private static final String INPUT2 = "src/test/resources/empty_input.csv";

    @Test
    void getRecords_correctFile_Ok() {
        List<String> expected = List.of("test1", "test2", "", "empty line above");
        assertEquals(expected, new ReaderServiceImpl().getRecords(INPUT1));
    }

    @Test
    void getRecords_incorrectFileName_NotOk() {
        assertThrows(RuntimeException.class, () -> new ReaderServiceImpl().getRecords("input"));
    }

    @Test
    void getRecords_emptyFile_Ok() {
        List<String> expected = List.of();
        assertEquals(expected, new ReaderServiceImpl().getRecords(INPUT2));
    }
}
