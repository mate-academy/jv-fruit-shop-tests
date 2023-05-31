package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.FileReadService;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.StorageUpdateService;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;
    private static FileReadService fileReadService;
    private static DataParserService dataParserService;
    private static StorageUpdateService storageUpdateService;
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @BeforeClass
    public static void init() {
        storageUpdateService = new StorageUpdateServiceImpl();
        dataParserService = new DataParserServiceImpl();
        fileReadService = new FileReadServiceImpl();
        reportCreator = new ReportCreatorImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_correctReportCreate_ok() {
        String data = fileReadService.readFromFile("src/main/resources/data.csv");
        List<FruitTransaction> transactions = dataParserService.getTransactions(data);
        storageUpdateService.update(transactions);
        String actual = reportCreator.createReport();
        String expected = "fruit,amount"
                + System.lineSeparator() + "banana,70"
                + System.lineSeparator() + "apple,30";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_notOk() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Can`t create report from empty storage");
        reportCreator.createReport();
    }
}
