package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private BalanceHandler balanceHandler;
    private SupplyHandler supplyHandler;
    private PurchaseHandler purchaseHandler;
    private ReturnHandler returnHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
        supplyHandler = new SupplyHandler();
        purchaseHandler = new PurchaseHandler();
        returnHandler = new ReturnHandler();
        fruitTransaction = new FruitTransaction();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, balanceHandler,
                FruitTransaction.Operation.SUPPLY, supplyHandler,
                FruitTransaction.Operation.PURCHASE, purchaseHandler,
                FruitTransaction.Operation.RETURN, returnHandler
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
        OperationHandler expected = balanceHandler;
        OperationHandler actual = operationStrategy.getHandler(fruitTransaction);
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing fruitTransaction with SUPPLY operation")
    @Test
    void getHandler_supplyTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        OperationHandler expected = supplyHandler;
        OperationHandler actual = operationStrategy.getHandler(fruitTransaction);
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing fruitTransaction with PURCHASE operation")
    @Test
    void getHandler_purchaseTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        OperationHandler expected = purchaseHandler;
        OperationHandler actual = operationStrategy.getHandler(fruitTransaction);
        assertEquals(expected, actual);
    }

    @DisplayName("Checking for passing fruitTransaction with RETURN operation")
    @Test
    void getHandler_returnTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        OperationHandler expected = returnHandler;
        OperationHandler actual = operationStrategy.getHandler(fruitTransaction);
        assertEquals(expected, actual);
    }
}
