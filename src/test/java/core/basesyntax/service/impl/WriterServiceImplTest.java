package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.WriterService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class WriterServiceImplTest {
    private WriterService writerService;
    private ReportCreatorService reportCreatorService;

    @Before
    public void setUp() throws Exception {
        writerService = new WriterServiceImpl();
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @Test
    public void writeToFile_OK() {
        String path = "src/main/resources/report.csv";
        String expected = null;
        try {
            expected = Files.readString(Path.of("src/main/resources/report.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String actual = reportCreatorService.createReport();
        writerService.writeToFile(path, actual);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void nullPath_WriteToFile_NotOK() {
        writerService.writeToFile(null, reportCreatorService.createReport());
    }

    @Test (expected = RuntimeException.class)
    public void wrongPath_WriteToFile_NotOK() {
        String path = "src/main/resources/smth/report.csv";
        writerService.writeToFile(path, reportCreatorService.createReport());
    }

    @Test (expected = NullPointerException.class)
    public void nullReport_WriteToFile_NotOK() {
        String path = "src/main/resources/report.csv";
        writerService.writeToFile(path, null);
    }

    @Test (expected = NullPointerException.class)
    public void nullValues_WriteToFile_NotOK() {
        writerService.writeToFile(null, null);
    }
}