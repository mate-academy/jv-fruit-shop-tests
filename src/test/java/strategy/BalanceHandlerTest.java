package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.strategy.impl.BalanceHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandlerImpl balanceHandler;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandlerImpl();
        transaction = new FruitTransaction();
        transaction.setFruit("apple");
        Storage.fruits.clear();
    }

    @Test
    void apply_createsNewRecordInDb() {
        transaction.setQuantity(10);
        balanceHandler.apply(transaction);
        assertEquals(10, Storage.fruits.get("apple"));
    }

    @Test
    void apply_updatesExistingRecordInDb() {
        Storage.fruits.put("apple", 5);
        transaction.setQuantity(15);
        balanceHandler.apply(transaction);
        assertEquals(15, Storage.fruits.get("apple"));
    }
}
