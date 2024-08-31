package core.basesyntax.fileservise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterImplTest {
    private CsvFileReader csvFileReader;
    private CsvFileWriter csvFileWriter;
    private String toFileName = "src/test/resources/report.csv";
    private List<String> report;

    @BeforeEach
    void init() {
        csvFileReader = new CsvFileReaderImpl();
        csvFileWriter = new CsvFileWriterImpl();
        report = new ArrayList<>();
        report.add("fruit,quantity");
    }

    @Test
    void fileNamePathNull() {
        toFileName = null;
        assertThrows(RuntimeException.class, () -> {
            csvFileWriter.writeToFile(report, toFileName);
        });
    }

    @Test
    void fileNameEmptyString() {
        toFileName = "";
        assertThrows(RuntimeException.class, () -> {
            csvFileWriter.writeToFile(report, toFileName);
        });
    }

    @Test
    void passedReportNull() {
        report = null;
        assertThrows(RuntimeException.class, () -> {
            csvFileWriter.writeToFile(report, toFileName);
        });
    }

    @Test
    void writeToFile() {
        report.add("banana,152");
        report.add("apple,90");
        csvFileWriter.writeToFile(report, toFileName);
        List<String> actual = csvFileReader.getTransactionsFromFile(toFileName);
        assertEquals(actual, report, "file writer failed");
    }
}
