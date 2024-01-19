package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ReaderService;
import service.impl.ReaderServiceImpl;

public class ReaderServiceImplTest {
    private static final String VALID_INPUT_FILE_PATH = "src/test/resources/input.csv";
    private static List<String> INPUT_FILE_DATA;
    private static ReaderService readerService;

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
        INPUT_FILE_DATA = List.of(
                "b,banana,20",
                "b,grape,50",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50",
                "p,grape,30"
        );
    }

    @Test
    void readerService_Ok() {
        List<String> dataFromCsvFile = readerService.readFromFile(VALID_INPUT_FILE_PATH);
        assertEquals(INPUT_FILE_DATA, dataFromCsvFile);
    }

    @Test
    void readerService_readFromNullFilePath_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readFromFile(null));
    }
}
