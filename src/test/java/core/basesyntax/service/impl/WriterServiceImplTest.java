package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String WRITE_TO = "src/test/java/resources/report.csv";
    private static final String WRONG_PATH = null;
    private ReaderService readerService;
    private ReportService reportService;
    private WriterService writerService;

    @Before
    public void init() {
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("banana", 81);
        writerService = new WriterServiceImpl();
        reportService = new ReportServiceImpl();
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void writeToFile_validData_Ok() {
        String report = reportService.generate();
        writerService.writeToFile(WRITE_TO, report);
        List<String> expected = List.of("banana,81", "apple,100");
        List<String> actual = readerService.readFromFile(WRITE_TO);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullPath_NotOk() {
        String report = reportService.generate();
        writerService.writeToFile(WRONG_PATH, report);
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}

