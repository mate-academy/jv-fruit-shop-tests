package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String VALID_FILE_NAME = "src/test/resources/reportTest.csv";
    private static final String INVALID_FILE_NAME = "///invalid\\\\file///name\\cssv";
    private static final List<String> EXPECTED = List.of("fruit,quantity","banana,200","apple,100");
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator() + "banana,200"
            + System.lineSeparator() + "apple,100";

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_validFileName_Ok() {
        writerService.writeToFile(REPORT, VALID_FILE_NAME);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(VALID_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data to file " + VALID_FILE_NAME, e);
        }
        assertEquals(EXPECTED, actual);
    }

    @Test (expected = RuntimeException.class)
    public void write_invalidFileName_notOk() {
        writerService.writeToFile(REPORT, INVALID_FILE_NAME);
    }

    @Test (expected = RuntimeException.class)
    public void write_nullReport_notOk() {
        writerService.writeToFile(null, VALID_FILE_NAME);
    }

    @Test (expected = RuntimeException.class)
    public void write_nullFileName_notOk() {
        writerService.writeToFile(REPORT, null);
    }
}
