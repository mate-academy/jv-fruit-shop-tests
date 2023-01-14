package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.service.FileReadService;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.StorageUpdateService;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportCreatorImplTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private final ReportCreator reportCreator = new ReportCreatorImpl();
    private final FileReadService fileReadService = new FileReadServiceImpl();
    private final DataParserService dataParserService = new DataParserServiceImpl();
    private final StorageUpdateService storageUpdateService = new StorageUpdateServiceImpl();

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_ok() {
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
