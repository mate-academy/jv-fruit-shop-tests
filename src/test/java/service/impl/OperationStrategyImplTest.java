package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.OperationStrategy;
import strategy.BalanceOperation;
import strategy.PurchaseOperation;
import strategy.ReturnOperation;
import strategy.SupplyOperation;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        operationStrategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()));
    }

    @Test
    void returnCorrectOperation_Ok() {
        assertEquals(BalanceOperation.class,
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
        assertEquals(PurchaseOperation.class,
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
        assertEquals(ReturnOperation.class,
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
        assertEquals(SupplyOperation.class,
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    void passOperationHandler_Null_notOk() {
        OperationStrategy emptyOperation = new OperationStrategyImpl(Map.of());
        assertNull(emptyOperation.get(FruitTransaction.Operation.BALANCE));
    }
}
