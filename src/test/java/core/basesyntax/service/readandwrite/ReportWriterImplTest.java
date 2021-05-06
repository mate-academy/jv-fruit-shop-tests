package core.basesyntax.service.readandwrite;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/reportTest.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/resoces/reportTest.csv";
    private static final String CONTENT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static final String EXCEPTION = "Illegal argument";
    private static final List<String> EXPECTED =
            List.of("fruit,quantity", "banana,152", "apple,90");
    private static ReportWriter reportWriter;

    @BeforeClass
    public static void beforeClass() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    public void writeReport_Ok() {
        List<String> expected = EXPECTED;
        reportWriter.writeReport(PATH_TO_FILE, CONTENT);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(PATH_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION);
        }
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeReport_NotOk() {
        reportWriter.writeReport(WRONG_PATH_TO_FILE, CONTENT);
    }
}
