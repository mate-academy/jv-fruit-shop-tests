package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.BalanceHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseHandler;
import core.basesyntax.handlers.ReturnHandler;
import core.basesyntax.handlers.SupplyHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OperationStrategyTest {
    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private static OperationHandler balanceOperation;
    private static OperationHandler returnOperation;
    private static OperationHandler supplyOperation;
    private static OperationHandler purchaseOperation;

    @BeforeClass
    public static void beforeClass() {
        strategy = new OperationStrategyImpl();
        balanceOperation = new BalanceHandler();
        returnOperation = new ReturnHandler();
        supplyOperation = new SupplyHandler();
        purchaseOperation = new PurchaseHandler();
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceOperation);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, supplyOperation);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseOperation);
        handlerMap.put(FruitTransaction.Operation.RETURN, returnOperation);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void process_properData_ok() {
        strategy.process(FruitTransaction.Operation.BALANCE, "apple", 10);
        strategy.process(FruitTransaction.Operation.PURCHASE, "apple", 5);
        strategy.process(FruitTransaction.Operation.RETURN, "apple", 1);
        strategy.process(FruitTransaction.Operation.SUPPLY, "apple", 4);

        Map<String, Integer> actual = Storage.fruits;
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 10);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void process_nullData_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> strategy.process(null, null, null));
    }

    @Test
    public void process_balanceHandler_ok() {
        OperationHandler expected = balanceOperation;
        OperationHandler actual = handlerMap.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void process_returnHandler_ok() {
        OperationHandler expected = returnOperation;
        OperationHandler actual = handlerMap.get(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void process_supplyHandler_ok() {
        OperationHandler expected = supplyOperation;
        OperationHandler actual = handlerMap.get(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void process_purchaseHandler_ok() {
        OperationHandler expected = purchaseOperation;
        OperationHandler actual = handlerMap.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expected, actual);
    }
}
