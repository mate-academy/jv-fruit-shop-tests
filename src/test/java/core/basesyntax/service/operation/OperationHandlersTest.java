package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlersTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void balanceShouldSetValue() {
        OperationHandler balance = new BalanceOperation(storage);
        balance.handle(new FruitTransaction(Operation.BALANCE, "banana", 20));
        assertEquals(20, storage.get("banana"));
    }

    @Test
    void supplyShouldIncrease() {
        storage.put("orange", 5);
        OperationHandler supply = new SupplyOperation(storage);
        supply.handle(new FruitTransaction(Operation.SUPPLY, "orange", 10));
        assertEquals(15, storage.get("orange"));
    }

    @Test
    void returnShouldIncrease() {
        storage.put("orange", 1);
        OperationHandler ret = new ReturnOperation(storage);
        ret.handle(new FruitTransaction(Operation.RETURN, "orange", 4));
        assertEquals(5, storage.get("orange"));
    }

    @Test
    void purchaseShouldDecrease() {
        storage.put("apple", 10);
        OperationHandler purchase = new PurchaseOperation(storage);
        purchase.handle(new FruitTransaction(Operation.PURCHASE, "apple", 3));
        assertEquals(7, storage.get("apple"));
    }
}
