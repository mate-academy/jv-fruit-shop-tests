package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler = new PurchaseOperationHandler();

    @AfterEach
    void tearDown() {
        FruitStorage.FRUITS.clear();
    }

    @Test
    void handle_putToMap_ok() {
        FruitStorage.FRUITS.put("banana", 20);
        FruitStorage.FRUITS.put("apple", 20);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(Operation.PURCHASE, "banana", 10));
        fruitTransactionList.add(new FruitTransaction(Operation.PURCHASE, "apple", 10));
        fruitTransactionList.stream()
                .forEach(fruitTransaction -> operationHandler.handle(fruitTransaction));
        int actual = FruitStorage.FRUITS.entrySet().size();
        int expected = 2;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handle_firstOperationOfEachFruitShouldBeBalance_notOk() {
        FruitTransaction transactionPurchase
                = new FruitTransaction(Operation.PURCHASE, "banana", 54);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(transactionPurchase);
        });
    }

    @Test
    void handle_expectedQuantityLessThanZero_notOk() {
        FruitStorage.FRUITS.put("banana", 100);
        FruitTransaction transactionPurchase
                = new FruitTransaction(Operation.PURCHASE, "banana", 101);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(transactionPurchase);
        });
    }

    @Test
    void handle_nullInputParameter_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }
}
