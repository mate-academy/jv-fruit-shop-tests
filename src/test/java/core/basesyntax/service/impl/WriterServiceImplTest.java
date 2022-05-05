package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.storage.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    public static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
    private static WriterService writerService;
    private static ReportService reportService;
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
        reportService = new ReportServiceImpl();
        readerService = new ReaderServiceImpl();
        Storage.fruits.put(new Fruit("banana"), 100);
        Storage.fruits.put(new Fruit("apple"), 100);
    }

    @Test
    public void writeToFile_ok() {
        writerService.writeToFile(OUTPUT_FILE_PATH, reportService.getReport());
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,100");
        expected.add("apple,100");
        Assert.assertEquals(expected, readerService.readFromFile(OUTPUT_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notOk() {
        writerService.writeToFile("folder/incorrectFilePath", reportService.getReport());
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
