package core.basesyntax.service.impl;

import static core.basesyntax.Base.REPORT_LINE_00;
import static core.basesyntax.Base.REPORT_LINE_01;
import static core.basesyntax.Base.REPORT_LINE_02;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportWriterService;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportWriterServiceConsoleTest {

    @Test
    void writeReport_isOk() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        final ReportWriterService consoleWriter = new ReportWriterServiceConsole();
        List<String> report = List.of(REPORT_LINE_00, REPORT_LINE_01, REPORT_LINE_02);
        consoleWriter.writeReport(report);
        final String actual = baos.toString();
        final StringBuilder stringBuilder = new StringBuilder();
        report.stream().forEach(s -> stringBuilder.append(s).append(System.lineSeparator()));
        String expected = stringBuilder.toString();
        assertEquals(expected, actual, expected + " must be same as " + actual);

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}
