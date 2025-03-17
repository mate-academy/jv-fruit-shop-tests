package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FruitStorage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private OperationHandler balanceOperation = new BalanceOperation();
    private OperationHandler purchaseOperation = new PurchaseOperation();
    private OperationHandler returnOperation = new ReturnOperation();
    private OperationHandler supplyOperation = new SupplyOperation();

    private FruitTransaction createFruitTransaction(
            FruitTransaction.Operation operation, String fruit, int quantity) {
        return new FruitTransaction(operation, fruit, quantity);
    }

    private FruitStorage createFruitStorage(String fruit, int quantity) {
        return new FruitStorage(fruit, quantity);
    }

    @Test
    void operationHandler_checkBalanceOperationWorkingGood_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        FruitStorage fruitStorage = createFruitStorage("empty", 0);
        FruitStorage expected = createFruitStorage("banana", 100);
        FruitStorage result = balanceOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);

    }

    @Test
    void operationHandler_checkBalanceOperationWithNull_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.BALANCE, null, 100);
        FruitStorage fruitStorage = createFruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> balanceOperation.operation(fruitTransaction, fruitStorage));
    }

    @Test
    void operationHandler_checkPurchaseOperationWorkingGood_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 100);
        FruitStorage fruitStorage = createFruitStorage("banana", 200);
        FruitStorage expected = createFruitStorage("banana", 100);
        FruitStorage result = purchaseOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

    @Test
    void operationHandler_checkPurchaseOperationWithNull_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.PURCHASE, null, 100);
        FruitStorage fruitStorage = createFruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> purchaseOperation.operation(fruitTransaction, fruitStorage));
    }

    @Test
    void operationHandler_checkReturnOperationWorkingGood_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100);
        FruitStorage fruitStorage = createFruitStorage("banana", 0);
        FruitStorage expected = createFruitStorage("banana", 100);
        FruitStorage result = returnOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

    @Test
    void operationHandler_checkReturnOperationWithNull_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.RETURN, null, 100);
        FruitStorage fruitStorage = createFruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> returnOperation.operation(fruitTransaction, fruitStorage));
    }

    @Test
    void operationHandler_checkSupplyOperationWorkingGood_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100);
        FruitStorage fruitStorage = createFruitStorage("banana", 0);
        FruitStorage expected = createFruitStorage("banana", 100);
        FruitStorage result = supplyOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

    @Test
    void operationHandler_checkSupplyOperationWithNull_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.SUPPLY, null, 100);
        FruitStorage fruitStorage = createFruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> supplyOperation.operation(fruitTransaction, fruitStorage));
    }

    @Test
    void operationHandler_supportEdgeCases_Ok() {
        FruitTransaction fruitTransaction = createFruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 1000000);
        FruitStorage fruitStorage = createFruitStorage("banana", -1000000);
        FruitStorage expected = createFruitStorage("banana", 0);
        FruitStorage result = supplyOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

}
