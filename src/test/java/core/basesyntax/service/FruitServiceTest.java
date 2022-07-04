package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.OperationServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static List<Transaction> transactions;
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        transactions = new ArrayList<>();
        Map<Transaction.Operation, OperationHandler> mapStrategy = new HashMap<>();
        mapStrategy.put(Transaction.Operation.BALANCE, new BalanceHandlerImpl(fruitDao));
        mapStrategy.put(Transaction.Operation.SUPPLY, new SupplyHandlerImpl(fruitDao));
        mapStrategy.put(Transaction.Operation.PURCHASE, new PurchaseHandlerImpl(fruitDao));
        mapStrategy.put(Transaction.Operation.RETURN, new ReturnHandlerImpl(fruitDao));
        OperationService operationService = new OperationServiceImpl(mapStrategy);
        fruitService = new FruitServiceImpl(operationService);
    }

    @Test
    public void setBalance_ok() {
        transactions.add(new Transaction(
                Transaction.Operation.BALANCE, "apple", 23));
        fruitService.process(transactions);
        int expected = 23;
        int actual = Storage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void setSupply_ok() {
        transactions.add(new Transaction(
                Transaction.Operation.SUPPLY, "orange", 5));
        fruitService.process(transactions);
        int expected = 5;
        int actual = Storage.fruits.get("orange");
        assertEquals(expected, actual);
    }

    @Test
    public void setPurchase_ok() {
        transactions.add(new Transaction(
                Transaction.Operation.BALANCE, "pineapple", 100));
        transactions.add(new Transaction(
                Transaction.Operation.PURCHASE, "pineapple", 45));
        fruitService.process(transactions);
        int expected = 55;
        int actual = Storage.fruits.get("pineapple");
        assertEquals(expected, actual);
    }

    @Test
    public void setReturn_ok() {
        transactions.add(new Transaction(
                Transaction.Operation.RETURN, "apple", 13));
        fruitService.process(transactions);
        int expected = 13;
        int actual = Storage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void setSupply_notOk() {
        transactions.add(new Transaction(
                Transaction.Operation.SUPPLY, "orange", -32));
        fruitService.process(transactions);
    }

    @Test(expected = RuntimeException.class)
    public void setPurchase_notOk() {
        transactions.add(new Transaction(
                Transaction.Operation.BALANCE, "pineapple", 30));
        transactions.add(new Transaction(
                Transaction.Operation.PURCHASE, "pineapple", 50));
        fruitService.process(transactions);
    }

    @Test(expected = RuntimeException.class)
    public void setReturn_notOk() {
        transactions.add(new Transaction(
                Transaction.Operation.RETURN, "apple", -12));
        fruitService.process(transactions);
    }

    @After
    public void clearAll() {
        transactions.clear();
        Storage.fruits.clear();
    }
}
