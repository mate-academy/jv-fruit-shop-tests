package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.TransactionDao;
import core.basesyntax.dao.TransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportService;
import core.basesyntax.strategy.TransactionStrategyImpl;
import core.basesyntax.strategy.transaction.BalanceHandler;
import core.basesyntax.strategy.transaction.SupplyHandler;
import core.basesyntax.strategy.transaction.TransactionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static TransactionDao transactionDao;
    private static Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;
    private static ReportService reportService;

    private static FruitTransaction firstTransaction;
    private static FruitTransaction secondTransaction;
    private static FruitTransaction thirdTransaction;
    private static List<FruitTransaction> transactionList;

    private static String reportInfo;
    private static List<FruitTransaction> emptyList;

    @BeforeClass
    public static void beforeClass() {
        transactionDao = new TransactionDaoImpl();
        transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(transactionDao));
        transactionHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(transactionDao));
        reportService = new ReportServiceImpl(
                new TransactionStrategyImpl(transactionHandlerMap));

        firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("banana");
        firstTransaction.setQuantity(20);
        secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondTransaction.setFruit("apple");
        secondTransaction.setQuantity(100);
        thirdTransaction = new FruitTransaction();
        thirdTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdTransaction.setFruit("banana");
        thirdTransaction.setQuantity(100);
        transactionList = List.of(firstTransaction, secondTransaction, thirdTransaction);
        reportInfo = "fruit,quantity" + System.lineSeparator()
                + "banana,120" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        emptyList = new ArrayList<>();
    }

    @Test
    public void countAmountOfFruits_listOfTransaction_Ok() {
        String actual = reportService.countAmountOfFruits(transactionList);
        assertEquals(actual, reportInfo);
    }

    @Test(expected = RuntimeException.class)
    public void countAmountOfFruits_emptyList_notOk() {
        reportService.countAmountOfFruits(emptyList);
    }

    @Test(expected = NullPointerException.class)
    public void countAmountOfFruits_null_notOk() {
        reportService.countAmountOfFruits(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
