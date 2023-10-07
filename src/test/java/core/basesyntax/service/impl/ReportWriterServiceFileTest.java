package core.basesyntax.service.impl;

import static core.basesyntax.Base.REPORT_LINE_00;
import static core.basesyntax.Base.REPORT_LINE_01;
import static core.basesyntax.Base.REPORT_LINE_02;
import static core.basesyntax.Base.REPORT_TXT;
import static core.basesyntax.Base.SAMPLE_REPORT_TXT;
import static core.basesyntax.Base.UNKNOWN_TXT;
import static core.basesyntax.Base.WRONG_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.ReportWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportWriterServiceFileTest {

    @Test
    void writeReport_isOk() {
        final ReportWriterService reportWriterService = new ReportWriterServiceFile();
        List<String> report = List.of(REPORT_LINE_00, REPORT_LINE_01, REPORT_LINE_02);
        ((ReportWriterServiceFile) reportWriterService).setFileName(REPORT_TXT);
        reportWriterService.writeReport(report);
        List<String> linesRead;
        try {
            linesRead = Files.readAllLines(Paths.get(SAMPLE_REPORT_TXT));
        } catch (IOException e) {
            fail("Error with test execution: " + SAMPLE_REPORT_TXT + " not found.");
            return;
        }
        assertEquals(report, linesRead, report + " must be equals " + linesRead);
    }

    @Test
    void writeReport_wrongFileName_notOk() {
        final ReportWriterService reportWriterService = new ReportWriterServiceFile();
        ((ReportWriterServiceFile) reportWriterService).setFileName(REPORT_TXT);
        assertThrows(NullPointerException.class,
                () -> reportWriterService.writeReport(null),
                "report == null: it must throws NullPointerException.");

        List<String> report = List.of(REPORT_LINE_00, REPORT_LINE_01, REPORT_LINE_02);
        ((ReportWriterServiceFile) reportWriterService).setFileName(null);
        assertThrows(NullPointerException.class,
                () -> reportWriterService.writeReport(report),
                "fileName == null: it must throws NullPointerException.");

        ((ReportWriterServiceFile) reportWriterService).setFileName(WRONG_PATH + UNKNOWN_TXT);
        assertThrows(RuntimeException.class,
                () -> reportWriterService.writeReport(report),
                "Must throws RuntimeException.");
    }
}
