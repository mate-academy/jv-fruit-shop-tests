package core.basesyntax.service;

import core.basesyntax.errors.ReportWriterError;
import core.basesyntax.service.impl.ReportWriterCsv;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportWriterTest {
    private static ReportWriter reportWriter;
    private static final String[] normalReport = {"fruit,quantity", "banana,100", "apple,50"};

    @BeforeAll
    static void beforeAll() {
        reportWriter = new ReportWriterCsv();
    }

    @Test
    void write_nullFileName_notOk() {
        Assertions.assertThrows(ReportWriterError.class,
                () -> reportWriter.write(normalReport, null));
    }

    @Test
    void write_emptyFileName_notOk() {
        Assertions.assertThrows(ReportWriterError.class,
                () -> reportWriter.write(normalReport, ""));
    }

    @Test
    void write_invalidFileName_notOk() {
        // Folder
        Assertions.assertThrows(ReportWriterError.class,
                () -> reportWriter.write(normalReport, "src/test/resources/"));
    }

    @Test
    void write_validReport_ok() {
        String fileName = "src/test/resources/report.csv";
        File file = new File(fileName);
        if (file.exists() && !file.delete()) {
            throw new RuntimeException("Can't delete test report");
        }

        // Empty report
        reportWriter.write(new String[0], fileName);
        Assertions.assertEquals(0, file.length(),
                "Does not match file length: expected "
                        + "0, exist " + file.length());
        if (!file.delete()) {
            throw new RuntimeException("Can't delete test report");
        }

        // Normal report
        reportWriter.write(normalReport, fileName);
        Assertions.assertEquals(32 + System.lineSeparator().length() * 3, file.length(),
                "Does not match file length: expected "
                        + "38, exist " + file.length());

        // If file exist
        reportWriter.write(normalReport, fileName);
        Assertions.assertEquals(32 + System.lineSeparator().length() * 3, file.length(),
                "Does not match file length: expected "
                        + "38, exist " + file.length());
    }
}
