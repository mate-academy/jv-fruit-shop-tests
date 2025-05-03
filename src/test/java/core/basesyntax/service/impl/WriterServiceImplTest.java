package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String REPORT_CSV_FILEPATH = "src/main/resources/report.csv";
    private static final String INVALID_PATH = "";
    private static final String CANNOT_WRITE_TO_FILE_BY_PATH = "Can't write to the file by path: ";
    private static WriterService writerService;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private List<String> report;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Before
    public void setUp() throws Exception {
        report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add("banana,152");
        report.add("apple,90");
    }

    @Test
    public void writeToFile_filePathValid_ok() {
        writerService.writeToFile(report, REPORT_CSV_FILEPATH);
        List<String> expected = report;
        List<String> actual = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(REPORT_CSV_FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actual.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_filePathInvalid_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(CANNOT_WRITE_TO_FILE_BY_PATH);
        writerService.writeToFile(report, INVALID_PATH);
    }
}
