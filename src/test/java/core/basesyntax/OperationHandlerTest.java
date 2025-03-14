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

    @Test
    void operationHandler_checkBalanceOperationWorkingGood() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        FruitStorage fruitStorage = new FruitStorage("empty", 0);

        FruitStorage expected = new FruitStorage("banana", 100);
        FruitStorage result = balanceOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

    @Test
    void operationHandler_checkBalanceOperationWithNull() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, null, 100);
        FruitStorage fruitStorage = new FruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> balanceOperation.operation(fruitTransaction, fruitStorage));
    }

    @Test
    void operationHandler_checkPurchaseOperationWorkingGood() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 100);
        FruitStorage fruitStorage = new FruitStorage("banana", 200);

        FruitStorage expected = new FruitStorage("banana", 100);
        FruitStorage result = purchaseOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

    @Test
    void operationHandler_checkPurchaseOperationWithNull() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, null, 100);
        FruitStorage fruitStorage = new FruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> purchaseOperation.operation(fruitTransaction, fruitStorage));
    }

    @Test
    void operationHandler_checkReturnOperationWorkingGood() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 100);
        FruitStorage fruitStorage = new FruitStorage("banana", 0);

        FruitStorage expected = new FruitStorage("banana", 100);
        FruitStorage result = returnOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

    @Test
    void operationHandler_checkReturnOperationWithNull() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, null, 100);
        FruitStorage fruitStorage = new FruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> returnOperation.operation(fruitTransaction, fruitStorage));
    }

    @Test
    void operationHandler_checkSupplyOperationWorkingGood() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100);
        FruitStorage fruitStorage = new FruitStorage("banana", 0);

        FruitStorage expected = new FruitStorage("banana", 100);
        FruitStorage result = supplyOperation.operation(fruitTransaction, fruitStorage);

        assertEquals(expected, result);
    }

    @Test
    void operationHandler_checkSupplyOperationWithNull() {

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, null, 100);
        FruitStorage fruitStorage = new FruitStorage("empty", 0);

        assertThrows(RuntimeException.class,
                () -> supplyOperation.operation(fruitTransaction, fruitStorage));
    }
}
