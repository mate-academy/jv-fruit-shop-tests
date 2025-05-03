package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.strategy.impl.ReturnHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private ReturnHandlerImpl returnHandler;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        returnHandler = new ReturnHandlerImpl();
        transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        Storage.fruits.clear();
    }

    @Test
    void apply_returnsGoods_increasesQuantity() {
        Storage.fruits.put("apple", 5);
        returnHandler.apply(transaction);
        assertEquals(15, Storage.fruits.get("apple"));
    }
}
