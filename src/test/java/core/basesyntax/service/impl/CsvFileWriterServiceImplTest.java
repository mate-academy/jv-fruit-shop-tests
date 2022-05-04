package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.ReportService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String WRITE_TO_FILE = "src/main/resources/after.csv";
    private static final String INCORRECT_PATH = "incorrect/path";
    private static CsvFileWriterService csvFileWriterService;
    private static ReportService reportService;

    @Before
    public void setUp() {
        csvFileWriterService = new CsvFileWriterServiceImpl();
        reportService = new ReportServiceImpl(new FruitShopDaoImpl());
    }

    @Test
    public void csvFileWriter_isOk() {
        Storage.fruitStorage.put("apple", 35);
        Storage.fruitStorage.put("banana", 135);
        csvFileWriterService.writeToFile(WRITE_TO_FILE, reportService);
        String actual = readFromFile();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana," + 135 + System.lineSeparator()
                + "apple," + 35 + System.lineSeparator();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_isNotOk() {
        csvFileWriterService.writeToFile(INCORRECT_PATH, reportService);
    }

    @After
    public void afterEachTest() {
        Storage.fruitStorage.clear();
    }

    private String readFromFile() {
        try {
            return Files.readString(new File(WRITE_TO_FILE).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't get this file " + WRITE_TO_FILE);
        }
    }
}
