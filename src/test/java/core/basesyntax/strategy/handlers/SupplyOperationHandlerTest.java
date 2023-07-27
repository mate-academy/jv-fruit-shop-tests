package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler operationHandler = new SupplyOperationHandler();

    @AfterEach
    void tearDown() {
        FruitStorage.FRUITS.clear();
    }

    @Test
    void handle_putToMap_ok() {
        FruitStorage.FRUITS.put("banana", 20);
        FruitStorage.FRUITS.put("apple", 20);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(Operation.SUPPLY, "banana", 20));
        fruitTransactionList.add(new FruitTransaction(Operation.SUPPLY, "apple", 20));
        fruitTransactionList.stream()
                .forEach(fruitTransaction -> operationHandler.handle(fruitTransaction));
        int actual = FruitStorage.FRUITS.entrySet().size();
        int expected = 2;
        Assertions.assertEquals(expected, actual);
        System.out.println(FruitStorage.FRUITS.entrySet());
    }

    @Test
    void handle_firstOperationIsSupply_notOk() {
        FruitTransaction transactionSupply = new FruitTransaction(Operation.SUPPLY, "banana", 10);
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(transactionSupply);
        });
    }

    @Test
    void handle_nullInputParameter_notOk() {
        Assert.assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }
}
