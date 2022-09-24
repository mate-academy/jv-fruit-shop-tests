package core.basesyntax.service.report;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {

    private static final String REPORT_TITLE =
            "fruit,quantity" + System.lineSeparator();
    private FruitStorageDao storageDao;
    private ReportService reportService;

    public ReportServiceImplTest() {
        this.storageDao = new FruitStorageDaoImpl();
        this.reportService = new ReportServiceImpl(storageDao);
    }

    @Test
    public void emptyStorage_Ok() {
        Storage.storage.clear();
        String expected = REPORT_TITLE;
        String actual = reportService.getReport();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addOneFruit_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",10);
        String expected = REPORT_TITLE + "banana,10";
        Storage.storage.put(fruit.getName(), fruit.getQuantity());
        String actual = reportService.getReport();
        Assert.assertEquals("Reports are different: ", expected, actual);
    }

    @Test
    public void add60kFruits_Ok() {
        for (int i = 0; i < 60000; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana " + i,10 + i);
            Storage.storage.put(fruit.getName(), fruit.getQuantity());
        }
        String storageFruits = Storage.storage.entrySet()
                .stream()
                .map(c -> c.getKey() + "," + c.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
        String expected = REPORT_TITLE + storageFruits;
        String actual = reportService.getReport();
        Assert.assertEquals("Reports are different: ", expected, actual);
    }

    @Test
    public void addSameNameFruit_Ok() {
        for (int i = 0; i < 6; i++) {
            FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana",i);
            Storage.storage.put(fruit.getName(), fruit.getQuantity());
        }
        String expected = REPORT_TITLE + "banana,5";
        String actual = reportService.getReport();
        Assert.assertEquals("Reports are different: ",expected, actual);
    }

    @Test
    public void ignoreNullNameFruit_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                null,10);
        Storage.storage.put(fruit.getName(), fruit.getQuantity());
        String expected = REPORT_TITLE;
        String actual = reportService.getReport();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addMaxQuantity_Ok() {
        FruitTransaction fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange",Integer.MAX_VALUE);
        Storage.storage.put(fruit.getName(), fruit.getQuantity());
        String expected = REPORT_TITLE + "orange," + Integer.MAX_VALUE;
        String actual = reportService.getReport();
        Assert.assertEquals(expected,actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
