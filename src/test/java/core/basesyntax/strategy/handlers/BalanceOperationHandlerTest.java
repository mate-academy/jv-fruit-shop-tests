package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler operationHandler = new BalanceOperationHandler();

    @Test
    void handle_putToMap_ok() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        fruitTransactionList.add(new FruitTransaction(Operation.BALANCE, "apple", 10));
        fruitTransactionList.stream()
                .forEach(fruitTransaction -> operationHandler.handle(fruitTransaction));
        int actual = FruitStorage.FRUITS.entrySet().size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void handle_nullInputParameter_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }
}
