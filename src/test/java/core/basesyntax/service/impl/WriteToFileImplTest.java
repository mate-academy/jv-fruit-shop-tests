package core.basesyntax.service.impl;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Native;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.Assert.assertEquals;

public class WriteToFileImplTest {
    private static final String PATH = "src/test/resources/test_writeToFile.csv";
    private static final String INCORRECT_PATH = "src/main/resources/report.csv";
    WriteToFileImpl writeToFile;
    private ReportCreatorService reportCreatorService;

    @Before
    public void setUp() throws Exception {
        writeToFile = new WriteToFileImpl();
        reportCreatorService = new ReportCreatorService();
    }

    @Test
    public void writeToFile_Ok() {
        String expected = null;
        try {
            expected = Files.readString(
                    Path.of("src/test/resources/test_writeToFile.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String actual = reportCreatorService.createReport();
        writeToFile.writeToFile(PATH, actual);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeReport_NullValue_NotOk() {
        writeToFile.writeToFile(PATH, null);
    }

    @Test (expected = RuntimeException.class)
    public void wrongPath_WriteToFile_NotOK() {
        writeToFile.writeToFile(INCORRECT_PATH, reportCreatorService.createReport());
    }



}