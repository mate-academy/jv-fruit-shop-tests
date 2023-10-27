package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;

    @BeforeAll
    static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_validData_ok() {
        writerService.writeToFile("output.csv", "type,fruit,quantity");

        List<String> expected = List.of("type,fruit,quantity");

        assertEquals(expected, new ReaderServiceImpl().readFile("output.csv"),
                "Only data must be written to file!");
    }

    @Test
    void writeToFile_invalidFileName_throwException() {
        try {
            writerService.writeToFile(null, null);
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        assertFalse(false, "Method must throw exception if value are incorrect!");
    }

}
