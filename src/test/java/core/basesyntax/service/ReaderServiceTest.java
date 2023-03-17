package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String DEFAULT_FILE_PATH =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources\\reader.csv";
    private static final String CORRECT_DATA = "abcd";
    private static final String EMPTY_DATA_PATH =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources\\empty.csv";
    private static final String INCORRECT_PATH =
            "D:\\Progr\\jv-fruit-shop-tests\\src\\test\\resources";
    private static final String EMPTY_DATA = "";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void readFromFile_correctOutput_ok() {
        assertEquals(readerService.readFrom(DEFAULT_FILE_PATH), CORRECT_DATA);
    }

    @Test
    void readFromFile_emptyData_ok() {
        assertEquals(readerService.readFrom(EMPTY_DATA_PATH), EMPTY_DATA);
    }

    @Test
    void readFromFile_nullPath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFrom(null);
        });
    }

    @Test
    void readFromFile_incorrectPath_notOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readFrom(INCORRECT_PATH);
        });
    }
}
