package service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriteReportServiceImplTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator()
            + "apple,100" + System.lineSeparator();
    private static final String FILENAME = "report.txt";
    private WriteReportServiceImpl writeReportService;

    @Before
    public void setUp() {
        writeReportService = new WriteReportServiceImpl();
    }

    @After
    public void tearDown() {
        File file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void writeReport_validData_Ok() throws IOException {
        writeReportService.writeReport(REPORT, FILENAME);
        String content = Files.readString(new File(FILENAME).toPath());
        Assert.assertEquals(REPORT, content);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_invalidFilename_notOk() {
        String invalidFilename = "nonexistentDirectory/report.txt";
        writeReportService.writeReport(REPORT, invalidFilename);
    }

    @Test(expected = NullPointerException.class)
    public void writeReport_nullData_notOk() {
        writeReportService.writeReport(null, FILENAME);
    }
}
