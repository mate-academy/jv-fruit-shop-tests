package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SupplyOperation();
        Storage.clear();
    }

    @Test
    void apply_validSupply_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 20);

        handler.apply(transaction);

        assertEquals(20, Storage.getFruits().get("apple"));
    }
}
