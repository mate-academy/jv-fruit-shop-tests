package core.strategy;

import static org.junit.Assert.assertEquals;

import core.db.Storage;
import core.model.FruitTransaction;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void init() {
        Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
    }

    @Before
    public void fillStorage() {
        Storage.fruits.put("apple", 10);
    }

    @Test
    public void getOperationHandler_Purchase_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass();
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void getReturneOperation_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN).getClass();
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void getBalanceOperation_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE).getClass();
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test
    public void getSupplyOperation_Ok() {
        Class<? extends OperationHandler> actual = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass();
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getOperationHandler_Null_notOk() {
        operationStrategy.getOperationHandler(null);
    }
}
