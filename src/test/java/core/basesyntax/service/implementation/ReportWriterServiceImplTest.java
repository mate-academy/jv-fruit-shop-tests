package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterServiceImplTest {
    private static final String CORRECT_FILE_NAME = "src/test/resources/CorrectToFile.csv";
    private static final String WRONG_FILE_NAME = "";
    private static final String TABLE_HEADING = "fruit,quantity";
    private static ReportWriterService reportWriterService;
    private static List<String> infoToWrite;
    private static List<String> expectedList;

    @BeforeClass
    public static void setUp() {
        reportWriterService = new ReportWriterServiceImpl();
        infoToWrite = List.of("banana,10" + System.lineSeparator(),
                "apple,85" + System.lineSeparator());
        expectedList = List.of(TABLE_HEADING, "banana,10", "apple,85");
    }

    @Test
    public void createReportFile_correctFileName_Ok() throws IOException {
        List<String> expected = expectedList;
        reportWriterService.createReportFile(infoToWrite, CORRECT_FILE_NAME);
        List<String> actual = Files.readAllLines(Path.of(CORRECT_FILE_NAME));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void createReportFile_wrongFileName_Ok() {
        reportWriterService.createReportFile(infoToWrite, WRONG_FILE_NAME);
    }
}
