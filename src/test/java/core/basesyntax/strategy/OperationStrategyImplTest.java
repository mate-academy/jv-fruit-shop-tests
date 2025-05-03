package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operations;

    @Before
    public void setUp() {
        operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operations.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operations.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    }

    @Test
    public void get_BalanceCheck_Ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.BALANCE).getClass();
        Class expected = BalanceOperationHandler.class;
        assertEquals(expected,actual);
    }

    @Test
    public void get_PurchaseCheck_Ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.PURCHASE).getClass();
        Class expected = PurchaseOperationHandler.class;
        assertEquals(expected,actual);
    }

    @Test
    public void get_SupplyCheck_Ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        Class expected = SupplyOperationHandler.class;
        assertEquals(expected,actual);
    }

    @Test
    public void get_ReturnCheck_Ok() {
        Class actual = new OperationStrategyImpl(operations)
                .get(FruitTransaction.Operation.RETURN).getClass();
        Class expected = ReturnOperationHandler.class;
        assertEquals(expected,actual);
    }
}
