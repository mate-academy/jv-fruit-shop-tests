package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;
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
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
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
        Storage.fruits.clear();
    }

    @Test
    public void processTransaction_correctData_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                fruitTransaction1, fruitTransaction2, fruitTransaction3, fruitTransaction4);
        fruitShopService.processTransaction(fruitTransactionList);
        int expected = 40;
        int actual = Storage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void processTransaction_emptyList_ok() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        fruitShopService.processTransaction(emptyList);
        int expected = 0;
        int actual = Storage.fruits.size();
        assertEquals(expected, actual);
    }
}
