package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler operationHandler = new ReturnOperationHandler();

    @AfterEach
    void tearDown() {
        FruitStorage.FRUITS.clear();
    }

    @Test
    void handle_putToMap_ok() {
        FruitStorage.FRUITS.put("banana", 20);
        FruitStorage.FRUITS.put("apple", 20);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(Operation.RETURN, "banana", 100));
        fruitTransactionList.add(new FruitTransaction(Operation.RETURN, "apple", 100));
        fruitTransactionList.stream()
                .forEach(fruitTransaction -> operationHandler.handle(fruitTransaction));
        int actual = FruitStorage.FRUITS.size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void handle_firstOperationIsReturn_notOk() {
        FruitTransaction transactionReturn = new FruitTransaction(Operation.RETURN, "banana", 54);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(transactionReturn);
        });
    }

    @Test
    void handle_nullInputParameter_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }
}
