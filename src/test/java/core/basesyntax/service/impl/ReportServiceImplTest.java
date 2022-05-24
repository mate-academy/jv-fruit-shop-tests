package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static List<FruitTransaction> fruitTransactionList;
    private static FruitTransaction appleTransaction;
    private static FruitTransaction bananaTransaction;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        fruitTransactionList = new ArrayList<>();
        appleTransaction = new FruitTransaction();
        bananaTransaction = new FruitTransaction();
    }

    @Before
    public void setUp() {
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(10);
        appleTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setQuantity(20);
        bananaTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransactionList.add(appleTransaction);
        fruitTransactionList.add(bananaTransaction);
        exception = new Exception();
    }

    @After
    public void tearDown() {
        fruitTransactionList.clear();
    }

    @Test
    public void getReport_nullList_notOK() {
        try {
            reportService.getReport(null);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getReport_goodList_OK() {
        String report = null;
        try {
            report = reportService.getReport(fruitTransactionList);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals("fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,20" + System.lineSeparator(), report);        
    }
}
