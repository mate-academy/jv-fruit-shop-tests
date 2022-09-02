package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

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
    public void setUp() {
        writerServiceTest = new FileWriterServiceImpl();
        reportTest = new ReportServiceImpl();
    }

    @Test
    public void writeToFile_OK() {
        String path = "src/main/resources/report.csv";
        String expectedFile = "src/main/resources/report.csv";
        writerServiceTest.write(reportTest.getReport(), path);
        boolean expected;
        try {
            expected = Files.isSameFile(Path.of(path), Path.of(expectedFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(expected);
    }

    @Test (expected = NullPointerException.class)
    public void nullPath_WriteToFile_NotOK() {
        writerServiceTest.write(null, reportTest.getReport());
    }

    @Test (expected = NullPointerException.class)
    public void nullReport_WriteToFile_NotOK() {
        String path = "src/main/resources/report.csv";
        writerServiceTest.write(path, null);
    }

    @Test (expected = NullPointerException.class)
    public void nullValues_WriteToFile_NotOK() {
        writerServiceTest.write(null, null);
    }
}
