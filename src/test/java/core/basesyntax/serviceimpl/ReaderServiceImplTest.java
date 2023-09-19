package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String VALID_INPUT = "src/test/resources/valid_input.csv";
    private static final String INVALID_PATH = "dev/null";

    private static ReaderService readerService;

    @BeforeAll
    static void init() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void when_FileNotExists_NotOk() {
        assertThrows(RuntimeException.class,
                () -> readerService.readOperations(INVALID_PATH));
    }

    @Test
    void when_FileValid_Ok() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,apple,100",
                "p,apple,15");
        List<String> expected = lines.stream().skip(1).toList();
        List<String> actual = readerService.readOperations(VALID_INPUT);
        assertEquals(expected, actual);
    }
}
