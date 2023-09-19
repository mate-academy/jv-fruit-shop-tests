package core.basesyntax.strategy.operation.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.StrategyImpl;
import core.basesyntax.strategy.operation.Operation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static FruitService fruitService;
    private static Transaction appleSupplyTransaction;
    private static Transaction applePurchaseTransaction;
    private static Transaction bananaBalanceTransaction;
    private static Transaction bananaPurchaseTransaction;

    @BeforeClass
    public static void setUp() {
        Map<Transaction.Operation, Operation> map = new HashMap<>();
        map.put(Transaction.Operation.BALANCE, new Balance());
        map.put(Transaction.Operation.SUPPLY, new Supply());
        map.put(Transaction.Operation.RETURN, new Return());
        map.put(Transaction.Operation.PURCHASE, new Purchase());
        fruitService = new FruitService(new StrategyImpl(map));
        Fruit apple = new Fruit("apple");
        appleSupplyTransaction = new Transaction();
        appleSupplyTransaction.setFruit(apple);
        appleSupplyTransaction.setOperation(Transaction.Operation.SUPPLY);
        appleSupplyTransaction.setSum(100);
        applePurchaseTransaction = new Transaction();
        applePurchaseTransaction.setFruit(apple);
        applePurchaseTransaction.setOperation(Transaction.Operation.PURCHASE);
        applePurchaseTransaction.setSum(50);
        Fruit banana = new Fruit("banana");
        bananaBalanceTransaction = new Transaction();
        bananaBalanceTransaction.setFruit(banana);
        bananaBalanceTransaction.setOperation(Transaction.Operation.BALANCE);
        bananaBalanceTransaction.setSum(300);
        bananaPurchaseTransaction = new Transaction();
        bananaPurchaseTransaction.setFruit(banana);
        bananaPurchaseTransaction.setOperation(Transaction.Operation.PURCHASE);
        bananaPurchaseTransaction.setSum(170);
    }

    @Before
    public void beforeAll() {
        Storage.storage.clear();
    }

    @Test
    public void fruitService_processCorrectTransactions_ok() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(appleSupplyTransaction);
        transactionList.add(applePurchaseTransaction);
        transactionList.add(bananaBalanceTransaction);
        transactionList.add(bananaPurchaseTransaction);
        fruitService.processTransaction(transactionList);
        String expectedString = "{Fruit{name='banana'}=130, Fruit{name='apple'}=50}";
        Assert.assertEquals(expectedString, Storage.storage.toString());
    }

    @Test(expected = RuntimeException.class)
    public void fruitService_processIncorrectTransactions_notOk() {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(applePurchaseTransaction);
        transactionList.add(bananaPurchaseTransaction);
        fruitService.processTransaction(transactionList);
    }

    @AfterClass
    public static void clearStorage() {
        Storage.storage.clear();
    }
}
