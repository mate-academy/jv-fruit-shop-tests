package core.basesyntax.service.impls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private static final String ILLEGAL_PATH = "//ty/illegal.csv";
    private static final String TEST_PATH = "src/test/resources/testlog.csv";
    private static final List<String> EXPECTED
            = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
    private static ReaderService readerService;

    @BeforeAll
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFile_illegalPath_RuntimeException() {
        assertThrows(RuntimeException.class, () -> readerService.readFile(ILLEGAL_PATH));
    }

    @Test
    public void readFile_validPath_isOk() {
        List<String> actual = readerService.readFile(TEST_PATH);
        assertEquals(EXPECTED, actual);
    }

}
