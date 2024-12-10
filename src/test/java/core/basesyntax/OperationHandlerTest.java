package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void operationHandler_balanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 0);
        OperationHandler balanceOperation = new BalanceOperation();
        balanceOperation.apply(transaction);
        Assertions.assertEquals(0, Storage.fruits.get("banana"));
    }

    @Test
    void operationHandler_supplyOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);
        OperationHandler supplyOperation = new SupplyOperation();
        supplyOperation.apply(transaction);
        Assertions.assertEquals(10, Storage.fruits.get("apple"));
    }

    @Test
    void operationHandler_purchaseOperation_ok() {
        Storage.fruits.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 50);
        OperationHandler purchaseOperation = new PurchaseOperation();
        purchaseOperation.apply(transaction);
        Assertions.assertEquals(50, Storage.fruits.get("apple"));
    }

    @Test
    void operationHandler_returnOperation_ok() {
        Storage.fruits.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 10);
        OperationHandler returnOperation = new ReturnOperation();
        returnOperation.apply(transaction);
        Assertions.assertEquals(20, Storage.fruits.get("banana"));
    }
}
