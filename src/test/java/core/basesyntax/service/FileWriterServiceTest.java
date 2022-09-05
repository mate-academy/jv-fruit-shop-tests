package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceTest {
    private FileWriterService writerServiceTest;
    private ReportService reportTest;

    @Before
    public void beforeClass() {
        writerServiceTest = new FileWriterServiceImpl();
    }

    @Before
    public void setUp() {
        reportTest = new ReportServiceImpl();
    }

    @Test
    public void writeToFile_ok() {
        String path = "src/test/resources/testReport";
        String expected = "fruit ,quantity";
        writerServiceTest.write(reportTest.getReport(), path);
        try {
            assertEquals(
                    expected, Files.readString(Path.of(path)).trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test (expected = NullPointerException.class)
    public void nullPath_WriteToFile_NotOk() {
        writerServiceTest.write(null, reportTest.getReport());
    }

    @Test (expected = NullPointerException.class)
    public void nullReport_WriteToFile_NotOk() {
        String path = "src/main/resources/report.csv";
        writerServiceTest.write(path, null);
    }

    @Test (expected = NullPointerException.class)
    public void nullValues_WriteToFile_NotOk() {
        writerServiceTest.write(null, null);
    }
}
