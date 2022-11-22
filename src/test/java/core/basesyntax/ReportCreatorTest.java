package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ReportCreator;
import core.basesyntax.dao.ReportCreatorImpl;
import core.basesyntax.dbreport.Report;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.FruitShopService;
import core.basesyntax.services.FruitShopServiceImpl;
import core.basesyntax.services.TransactionStrategy;
import core.basesyntax.services.TransactionStrategyImpl;
import core.basesyntax.services.transaction.BalanceOperationHandler;
import core.basesyntax.services.transaction.OperationHandler;
import core.basesyntax.services.transaction.PurchaseOperationHandler;
import core.basesyntax.services.transaction.ReturnOperationHandler;
import core.basesyntax.services.transaction.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorTest {
    private static final ReportCreator reportCreator = new ReportCreatorImpl();

    @Before
    public void before() {
        Report.report.clear();
    }

    @Test
    public void reportCreate_Ok() {
        Map<Operation, OperationHandler> transactionMap = new HashMap<>();
        transactionMap.put(Operation.BALANCE, new BalanceOperationHandler());
        transactionMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        transactionMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        transactionMap.put(Operation.RETURN, new ReturnOperationHandler());
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(transactionMap);
        FruitShopService fruitShopService = new FruitShopServiceImpl(transactionStrategy);

        FruitTransaction transactionBalance = new FruitTransaction(Operation.BALANCE,
                "banana", 100);
        FruitTransaction transactionPurchase = new FruitTransaction(Operation.PURCHASE,
                "banana", 10);
        FruitTransaction transactionReturn = new FruitTransaction(Operation.RETURN,
                "banana", 5);
        FruitTransaction transactionSupply = new FruitTransaction(Operation.SUPPLY,
                "banana", 10);

        fruitShopService.doTransaction(transactionBalance);
        fruitShopService.doTransaction(transactionPurchase);
        fruitShopService.doTransaction(transactionReturn);
        fruitShopService.doTransaction(transactionSupply);

        String actual = reportCreator.createReport();
        String eol = System.getProperty("line.separator");
        String expected = "fruit,quantity" + eol
                + "banana,105" + eol;
        assertEquals(expected, actual);
    }

    @Test
    public void reportCreate_EmptyData_Ok() {
        String actual = reportCreator.createReport();
        String eol = System.getProperty("line.separator");
        String expected = "fruit,quantity" + eol;
        assertEquals(expected, actual);
    }

    @Test
    public void reportCreate_Manual_Ok() {
        Report.report.put("banana", 100);
        Report.report.put("apple", 10);
        Report.report.put("plum", 50);
        String actual = reportCreator.createReport();
        String eol = System.getProperty("line.separator");
        String expected = "fruit,quantity" + eol
                + "banana,100" + eol
                + "plum,50" + eol
                + "apple,10" + eol;
        assertEquals(expected, actual);
    }
}
