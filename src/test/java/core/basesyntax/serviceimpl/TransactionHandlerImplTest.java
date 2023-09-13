package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.impl.TransactionHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.BuyOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionHandlerImplTest {
    private TransactionHandler transactionHandler;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private FruitTransaction fruitTransaction1;
    private FruitTransaction fruitTransaction2;
    private FruitTransaction fruitTransaction3;
    private FruitTransaction fruitTransaction4;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new BuyOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionHandler = new TransactionHandlerImpl(operationStrategy);
        fruitTransaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 20);
        fruitTransaction2 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);
        fruitTransaction3 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);
        fruitTransaction4 = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruit.clear();
    }

    @Test
    public void processTransaction_correctData_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                fruitTransaction1, fruitTransaction2, fruitTransaction3, fruitTransaction4);
        transactionHandler.parse(fruitTransactionList);
        int expected = 40;
        int actual = Storage.fruit.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void processTransaction_emptyList_ok() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        transactionHandler.parse(emptyList);
        int expected = 0;
        int actual = Storage.fruit.size();
        assertEquals(expected, actual);
    }
}
