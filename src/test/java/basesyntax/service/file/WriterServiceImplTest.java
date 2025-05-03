package basesyntax.service.file;

import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    public static final String CORRECT_PATH = "src/test/resources/test.csv";
    public static final String INCORRECT_PATH = "";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_correctPath_ok() {
        try {
            writerService.write(CORRECT_PATH, "");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Test (expected = RuntimeException.class)
    public void write_incorrectPath_notOk() {
        writerService.write(INCORRECT_PATH, "");
    }
}
