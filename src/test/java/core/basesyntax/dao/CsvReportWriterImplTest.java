package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvReportWriterImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FILE_PATH_FOR_REPORT =
            "src/test/resourcesTest/reportTest.csv";
    private CsvReportWriter csvReportWriter = new CsvReportWriterImpl();

    @Test
    void writeReport_Ok() throws IOException {
        File directory = new File("src/test/resourcesTest");
        directory.mkdir();
        File file = new File(FILE_PATH_FOR_REPORT);
        file.createNewFile();

        String reportData = HEADER + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();

        csvReportWriter.write(reportData, FILE_PATH_FOR_REPORT);
        String expected = reportData;
        List<String> list = Files.readAllLines(Path.of(FILE_PATH_FOR_REPORT));
        StringBuilder builder = new StringBuilder(list.get(0));
        builder.append(System.lineSeparator()).append(list.get(1)).append(System.lineSeparator())
                        .append(list.get(2)).append(System.lineSeparator());
        String actual = builder.toString();
        assertEquals(expected, actual);
    }
}
