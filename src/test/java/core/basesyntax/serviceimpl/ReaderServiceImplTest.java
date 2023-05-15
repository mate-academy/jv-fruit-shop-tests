package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INVALID_PATH =
            "wrong";
    private static final String VALID_PATH =
            "src/test/java/core/basesyntax/resources/testreport";
    private ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void read_validPath_ok() {
        final List<String> actual = readerService.readFromFile(VALID_PATH);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void read_invalidPath_notOk() {
        readerService.readFromFile(INVALID_PATH);
    }

}
