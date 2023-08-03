package core.basesyntax.service.implemantation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String REPORT_TEXT = "fruit,quantity\nbanana,152";
    private static final String CORRECT_PATH_FILE = "src/main/resources/correct_report.csv";
    private static final int INDEX_FIRST_LINE = 0;
    private static final int INDEX_SECOND_LINE = 1;
    private ReportWriter reportWriter;

    @BeforeEach
    void setUp() {
        reportWriter = new ReportWriterImpl();
    }

    @Test
    void writer_Report_Ok() throws IOException {
        reportWriter.writeReport(REPORT_TEXT, CORRECT_PATH_FILE);
        List<String> actualLines;
        actualLines = Files.readAllLines(Path.of(CORRECT_PATH_FILE));
        assertEquals(REPORT_TEXT.split("\n")[INDEX_FIRST_LINE],
                actualLines.get(INDEX_FIRST_LINE));
        assertEquals(REPORT_TEXT.split("\n")[INDEX_SECOND_LINE],
                actualLines.get(INDEX_SECOND_LINE));
    }
}
