package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.impl.ReportSenderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportSenderImplTest {
    private static final String CORRECT_REPORT_FILE_PATH =
            "src/test/resources/report.csv";
    private static final String INCORRECT_REPORT_FILE_PATH =
            "incorrect-directory/report.csv";
    private static final String REPORT =
            "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    private ReportSender reportSender;

    @BeforeEach
    void setUp() {
        reportSender = new ReportSenderImpl();
    }

    @Test
    void send_correctFilePath_ok() throws IOException {
        Path path = Path.of(CORRECT_REPORT_FILE_PATH);
        Files.createDirectories(path.getParent());
        reportSender.send(CORRECT_REPORT_FILE_PATH, REPORT);
        String actual = Files.readString(path);
        Files.deleteIfExists(path);
        assertTrue(actual.contains(REPORT));
    }

    @Test
    void send_incorrectFilePath_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reportSender.send(INCORRECT_REPORT_FILE_PATH, REPORT),
                "Incorrect path to file: " + INCORRECT_REPORT_FILE_PATH);
        assertTrue(exception.getMessage().contains("Error writing report"));
    }
}
