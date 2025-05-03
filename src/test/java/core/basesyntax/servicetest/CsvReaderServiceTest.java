package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.CsvReaderServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvReaderServiceTest {
    private static final String EXISTING_FILE_PATH = "src/test/resources/test.csv";
    private static final String NON_EXISTING_FILE_PATH = "src/test/resources/NonExisted.csv";
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new CsvReaderServiceImpl();
    }

    @Test
    void read_existingFile_ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100");
        List<String> actual = readerService.read(EXISTING_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    void read_nonExistedFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.read(NON_EXISTING_FILE_PATH));
    }
}
