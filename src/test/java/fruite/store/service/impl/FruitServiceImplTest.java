package fruite.store.service.impl;

import fruite.store.service.FileReaderService;
import fruite.store.service.FileWriterService;
import fruite.store.service.FruitService;
import fruite.store.service.strategy.StrategyHandler;
import org.junit.Assert;
import org.junit.Test;

public class FruitServiceImplTest {
    StrategyHandler strategyType = new StrategyHandler();
    FileReaderService readDateDao = new FileReaderServiceImpl();
    FileWriterService writeDateDao = new FileWriterServiceImpl();
    FruitService fruitService = new FruitServiceImpl(strategyType, readDateDao, writeDateDao);
    private static final String INVALID_DATA_FILE = "src/test/resources/invalid-data.csv";
    private static final String VALID_DATA_FILE = "src/test/resources/report-by-day.csv";
    private static final String VALID_DATA_IN_FILE =
            "src/test/resources/valid-data-activities-by-day.csv";

    @Test(expected = RuntimeException.class)
    public void makeReportByDay_invalidDataInFile_notOk() {
        fruitService.makeReportByDay(INVALID_DATA_FILE, VALID_DATA_FILE);
    }

    @Test
    public void makeReportByDay_validDataInFile_ok() {
        boolean actual = fruitService.makeReportByDay(VALID_DATA_IN_FILE, VALID_DATA_FILE);
        Assert.assertTrue(actual);
    }
}