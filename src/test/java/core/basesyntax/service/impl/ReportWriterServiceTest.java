package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.service.ReportWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportWriterServiceTest {
    public static final String PATH_TO_CORRECT_FILE = "src/test/resources/test_output_report.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/fgrcj/test_input_report.csv";
    private static final String SEPARATOR = ",";
    private final ReportWriterService reportWriterService = new ReportWriterServiceImpl();
    private String report;

    @Before
    public void createReport() {
        report = "fruit,quantity" + System.lineSeparator()
                + "cherry" + SEPARATOR + 100 + System.lineSeparator()
                + "banana" + SEPARATOR + 100 + System.lineSeparator()
                + "apple" + SEPARATOR + 100;
    }

    @Test
    public void writeReport() {
        reportWriterService.writeReport(report, PATH_TO_CORRECT_FILE);
        List<String> expected = List.of("fruit,quantity", "cherry,100", "banana,100", "apple,100");
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(PATH_TO_CORRECT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file by path: " + PATH_TO_CORRECT_FILE);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeFile_wrongPath_notOk() {
        try {
            reportWriterService.writeReport(report, WRONG_PATH_TO_FILE);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown is pats was wrong");
    }
}
