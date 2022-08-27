package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.WrongDataException;
import core.basesyntax.exceptions.WrongFileNameException;
import core.basesyntax.service.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CsvFileWriteServiceImplTest {
    private static final String FILE_NAME_TEST
            = "src/main/resources/test/testReport.csv";
    private CsvFileWriterService csvFileWriterService;
    private String reportData;

    @Before
    public void setUp() throws Exception {
        csvFileWriterService = new CsvFileWriteServiceImpl();
        reportData = "fruit,quantity\\r\\norange,1520\\r\\nkiwi,900";
    }

    @Test (expected = WrongFileNameException.class)
    public void writeReportToFile_nullFileNAme_notOk() {
        csvFileWriterService.writeReportToFile(reportData, null);
    }

    @Test (expected = WrongFileNameException.class)
    public void writeReportToFile_emptyFileNAme_notOk() {
        csvFileWriterService.writeReportToFile(reportData,"");
    }

    @Test (expected = WrongDataException.class)
    public void writeReportToFile_nullReportName_notOk() {
        csvFileWriterService.writeReportToFile(null, FILE_NAME_TEST);
    }

    @Test
    public void writeReportToFile_Ok() {
        String expected = reportData;
        List<String> actual = new ArrayList<>();
        csvFileWriterService.writeReportToFile(reportData, FILE_NAME_TEST);
        try {
            actual = Files.readAllLines(Path.of(FILE_NAME_TEST));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file in test", e);
        }
        assertEquals(reportData, actual.get(0));
    }
}
