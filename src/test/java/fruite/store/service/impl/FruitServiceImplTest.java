package fruite.store.service.impl;

import fruite.store.service.FileReaderService;
import fruite.store.service.FileWriterService;
import fruite.store.service.FruitService;
import fruite.store.service.strategy.StrategyHandler;
import org.junit.Assert;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String INVALID_DATA_IN_FILE =
            "src/test/resources/invalid-data.csv";
    private static final String VALID_FILE_NAME_AND_PATH =
            "src/test/resources/report-by-day.csv";
    private static final String VALID_DATA_IN_FILE =
            "src/test/resources/valid-data-activities-by-day.csv";
    private StrategyHandler strategyType = new StrategyHandler();
    private FileReaderService readDateDao = new FileReaderServiceImpl();
    private FileWriterService writeDateDao = new FileWriterServiceImpl();
    private FruitService fruitService =
            new FruitServiceImpl(strategyType, readDateDao, writeDateDao);

    @Test(expected = RuntimeException.class)
    public void makeReportByDay_invalidDataInFile_notOk() {
        fruitService.makeReportByDay(INVALID_DATA_IN_FILE, VALID_FILE_NAME_AND_PATH);
    }

    @Test
    public void makeReportByDay_validDataInFile_ok() {
        boolean actual = fruitService.makeReportByDay(VALID_DATA_IN_FILE, VALID_FILE_NAME_AND_PATH);
        Assert.assertTrue(actual);
    }
}
