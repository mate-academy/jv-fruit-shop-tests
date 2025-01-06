package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

    @BeforeEach
    void before() {
        operationHandlers.clear();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
    }

    @Test
    void getOperationBalance_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, OperationStrategyImpl.getOperation("b"));
    }

    @Test
    void getOperationSupply_Ok() {
        assertEquals(FruitTransaction.Operation.SUPPLY, OperationStrategyImpl.getOperation("s"));
    }

    @Test
    void getOperationPurchase_Ok() {
        assertEquals(FruitTransaction.Operation.PURCHASE, OperationStrategyImpl.getOperation("p"));
    }

    @Test
    void getOperationReturn_Ok() {
        assertEquals(FruitTransaction.Operation.RETURN, OperationStrategyImpl.getOperation("r"));
    }

    @Test
    void makeOperationBalance_Ok() {
        int quantity = 10;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(0);
        operationStrategy.makeOperation(
                FruitTransaction.Operation.BALANCE,
                fruitTransaction, quantity);
        assertEquals(quantity, fruitTransaction.getQuantity());
    }

    @Test
    void makeOperationSupply_Ok() {
        int quantity = 10;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(0);
        operationStrategy.makeOperation(
                FruitTransaction.Operation.SUPPLY,
                fruitTransaction, quantity);
        assertEquals(quantity, fruitTransaction.getQuantity());
    }

    @Test
    void makeOperationPurchase_Ok() {
        int quantity = 10;
        int balance = 10;
        int result = balance - quantity;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(balance);
        operationStrategy.makeOperation(
                FruitTransaction.Operation.PURCHASE,
                fruitTransaction, quantity);
        assertEquals(result, fruitTransaction.getQuantity());
    }

    @Test
    void makeOperationReturn_Ok() {
        int quantity = 10;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(0);
        operationStrategy.makeOperation(
                FruitTransaction.Operation.RETURN,
                fruitTransaction, quantity);
        assertEquals(quantity, fruitTransaction.getQuantity());
    }
}