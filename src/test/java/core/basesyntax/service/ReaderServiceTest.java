package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderServiceTest {
    private static final String CORRECT_FILE_NAME = "src/main/resources/input.csv";
    private static final String EMPTY_FILE_NAME = "src/main/resources/empty.csv";
    private static final String WRONG_FILE_NAME = "src/main/resources/wrong_input.csv";
    private static ReaderService readerService;
    private static final String EXPECTED_DATA = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";

    @BeforeAll
    static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void read_emptyFile_ok() {
        assertEquals(new ArrayList<String>(), readerService.readFile(EMPTY_FILE_NAME));
    }

    @Test
    void read_wrongFilePath_notOk() {
        assertThrows(RuntimeException.class, () -> readerService.readFile(WRONG_FILE_NAME));
    }

    @Test
    void read_Ok() {
        List<String> lines = readerService.readFile(CORRECT_FILE_NAME);
        String actual = lines.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(EXPECTED_DATA, actual);
    }
}
