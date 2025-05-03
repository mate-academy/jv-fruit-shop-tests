package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INVALID_PATH =
            "src/test/java/resources/12345.csv";
    private static final String VALID_PATH =
            "src/test/java/resources/transactionstest.csv";
    private static final List<String> CORRECT_DATA = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void read_validPath_ok() {
        List<String> actual = readerService.read(VALID_PATH);
        assertEquals(CORRECT_DATA, actual);
    }

    @Test (expected = RuntimeException.class)
    public void read_invalidPath_notOk() {
        readerService.read(INVALID_PATH);
    }
}
