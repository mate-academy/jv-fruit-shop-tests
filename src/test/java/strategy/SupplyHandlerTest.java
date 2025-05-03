package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.strategy.impl.SupplyHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private SupplyHandlerImpl supplyHandler;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        supplyHandler = new SupplyHandlerImpl();
        transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(20);
        Storage.fruits.clear();
    }

    @Test
    void apply_suppliesGoods_increasesQuantity() {
        Storage.fruits.put("apple", 5);
        supplyHandler.apply(transaction);
        assertEquals(25, Storage.fruits.get("apple"));
    }
}
