package strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;
import strategy.OperationStrategy;

class OperationStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlerMap = Map.of(
                    FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                    FruitTransaction.Operation.RETURN, new ReturnOperation(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperation());

    @Test
    void applyOperation_NullTransaction_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(null);
        assertThrows(RuntimeException.class,
                () -> operationHandlerMap.get(transaction.getOperation()));
    }

    @Test
    void getOperation_shouldReturnCorrectHandler_Ok() {
        OperationHandler mockHandler = (data, amount) -> 0;
        Map<FruitTransaction.Operation, OperationHandler> map = Map.of(
                FruitTransaction.Operation.BALANCE, mockHandler);

        OperationStrategy strategy = new OperationStrategyImpl(map);
        OperationHandler result = strategy.getOperation(FruitTransaction.Operation.BALANCE);

        assertNotNull(result);
        assertEquals(mockHandler, result);
    }

    @Test
    void applyOperation_validHandler_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);
        OperationHandler mockHandler = (
                tx, currentAmount) -> currentAmount + tx.getQuantity();

        OperationStrategyImpl strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.SUPPLY, mockHandler)
        );

        int result = strategy.applyOperation(transaction, 5);

        assertEquals(15, result);
    }

    @Test
    void applyOperation_wrongHandler_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 7);
        OperationStrategyImpl strategy = new OperationStrategyImpl(Map.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                strategy.applyOperation(transaction, 3)
        );

        assertEquals("Unknown operation: RETURN", exception.getMessage());
    }
}
