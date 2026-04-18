package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.impl.FruitDaoImpl;
import db.FruitStorage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private final FruitDaoImpl fruitDao = new FruitDaoImpl();
    private final BalanceOperation balanceOperation = new BalanceOperation(fruitDao);

    @AfterEach
    void tearDown() {
        FruitStorage.fruits.clear();
    }

    @Test
    void handle_setNewBalance_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);

        balanceOperation.handle(transaction);

        assertEquals(50, FruitStorage.fruits.get("apple"));
    }
}
