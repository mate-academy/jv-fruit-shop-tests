package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceCsvImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/nonExisted.csv";
    private static final String EXISTING_FILE_PATH = "src/test/resources/test.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceCsvImpl();
    }

    @Test
    void readFrom_nonExistedFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(NON_EXISTING_FILE_PATH)
        );
    }

    @Test
    void readFrom_existedFile_ok() {
        List<String> actual = readerService.readFromFile(EXISTING_FILE_PATH);
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        assertEquals(expected, actual);
    }
}
