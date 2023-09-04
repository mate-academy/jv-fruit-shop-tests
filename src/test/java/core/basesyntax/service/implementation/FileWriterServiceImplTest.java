package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileWriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static String SEPARATOR = System.lineSeparator();
    private static FileWriterService fileWriterService;
    private String report;
    private String reportFile;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @BeforeEach
    void setUp() {
        report = "fruit,quantity" + SEPARATOR
                + "apricot,15" + SEPARATOR;
        reportFile = "src/test/resources/testReport.csv";
    }

    @Test
    void write_validData_ok() {
        fileWriterService.write(report, reportFile);
        String actualReport = read(reportFile);
        String expectedReport = "fruit,quantity" + SEPARATOR
                + "apricot,15" + SEPARATOR;
        assertEquals(actualReport, expectedReport);
    }

    @Test
    void write_nullReportFileData_notOk() {
        reportFile = "";
        assertThrows(RuntimeException.class, () -> fileWriterService.write(report, reportFile));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }

    private String read(String file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String value = bufferedReader.readLine();
            while (value != null) {
                stringBuilder.append(value).append(System.lineSeparator());
                value = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't find a file " + file, e);
        }
        return stringBuilder.toString();
    }
}
