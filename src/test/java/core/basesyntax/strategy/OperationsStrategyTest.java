package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationsStrategyTest {
    private FruitDao fruitDao;
    private Map<FruitTransaction.Operation, OperationHandler> operations;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        operations.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operations.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        operations.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
    }

    @Test
    public void balanceClassCheck_ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.BALANCE).getClass();
        Class expected = BalanceOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void purchaseClassCheck_ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.PURCHASE).getClass();
        Class expected = PurchaseOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void returnClassCheck_ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.RETURN).getClass();
        Class expected = ReturnOperationHandler.class;
        assertEquals(actual, expected);
    }

    @Test
    public void supplyClassCheck_ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        Class expected = SupplyOperationHandler.class;
        assertEquals(actual, expected);
    }
}
