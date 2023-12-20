package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String RECORD_PATH = "src\\test\\resources\\infoRecord.csv";
    private static ReaderService readerService;
    private static List<String> expectedList;

    @BeforeAll
    public static void setUp() {
        readerService = new ReaderServiceImpl();
        expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
    }

    @Test
    void read_notValidPath_notOk() {
        assertThrows(NullPointerException.class, () -> readerService.read(null));
        assertThrows(RuntimeException.class, () -> readerService.read(""));
    }

    @Test
    void read_allOk() {
        assertDoesNotThrow(() -> readerService.read(RECORD_PATH));

        List<String> actualList = readerService.read(RECORD_PATH);
        assertEquals(expectedList, actualList);
    }
}
