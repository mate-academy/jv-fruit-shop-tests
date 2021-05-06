package core.basesyntax.service.impls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String ILLEGAL_PATH = "//ty/illegal.csv";
    private static final String TEST_PATH = "src/test/resources/testreport.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,12" + System.lineSeparator()
            + "apple,20" + System.lineSeparator();
    private static WriterService writerService;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        expected = List.of("fruit,quantity", "banana,12", "apple,20");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_illegalPath_RuntimeException() {
        writerService.writeToFile(REPORT, ILLEGAL_PATH);
    }

    @Test
    public void writeToFile_isOk() {
        writerService.writeToFile(REPORT, TEST_PATH);
        List<String> actual;
        try {
            actual = Files.readAllLines(Paths.get(TEST_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + TEST_PATH, e);
        }
        assertEquals(expected, actual);
    }
}
