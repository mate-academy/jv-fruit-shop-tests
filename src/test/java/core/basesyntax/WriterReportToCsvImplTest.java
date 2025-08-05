package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriterReportToCsv;
import core.basesyntax.service.WriterReportToCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriterReportToCsvImplTest {
    private static final String FILE_TO_WRITE = "src/test/resources/test_finalReport.csv";
    private static WriterReportToCsv writerReportToCsv;

    @BeforeAll
    static void setUp() {
        writerReportToCsv = new WriterReportToCsvImpl();
    }

    @Test
    public void writeReport_Ok() throws IOException {
        Path path = Paths.get(FILE_TO_WRITE);
        String outputFilePath = path.toString();
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        writerReportToCsv.writeReport(report, outputFilePath);
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,152",
                "apple,90"
        );
        List<String> actual = Files.readAllLines(path);
        assertEquals(expected, actual);
    }

    @Test
    public void writeReport_NullPath_NotOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        assertThrows(IllegalArgumentException.class, () ->
                writerReportToCsv.writeReport(report, null));
    }

    @Test
    public void writeReport_InvalidPath_NotOk() {
        String outputFilePath = "src/test/non_existing/test.csv";
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        assertThrows(RuntimeException.class, () ->
                writerReportToCsv.writeReport(report, outputFilePath));
    }
}
