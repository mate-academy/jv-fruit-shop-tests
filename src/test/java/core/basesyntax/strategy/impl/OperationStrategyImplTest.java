package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategyImpl operationStrategy;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler()
        );
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @DisplayName("Checking for passing null as fruitTransaction value")
    @Test
    void getHandler_nullFruitTransaction_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                operationStrategy.getHandler(null));
    }

    @DisplayName("Checking for passing fruitTransaction with BALANCE operation")
    @Test
    void getHandler_balanceTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        Class<BalanceHandler> expected = BalanceHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(fruitTransaction).getClass();
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing fruitTransaction with SUPPLY operation")
    @Test
    void getHandler_supplyTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        Class<SupplyHandler> expected = SupplyHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(fruitTransaction).getClass();
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing fruitTransaction with PURCHASE operation")
    @Test
    void getHandler_purchaseTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        Class<PurchaseHandler> expected = PurchaseHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(fruitTransaction).getClass();
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing fruitTransaction with RETURN operation")
    @Test
    void getHandler_returnTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        Class<ReturnHandler> expected = ReturnHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(fruitTransaction).getClass();
        assertEquals(expected, actual);
    }
}
