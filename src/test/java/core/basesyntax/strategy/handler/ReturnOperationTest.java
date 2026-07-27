package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void serUp() {
        returnOperation = new ReturnOperation();
        Storage.getFruits().clear();
    }

    @Test
    void apply_ok() {

        returnOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10
        ));

        returnOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                25
        ));

        assertEquals(35, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_notOk() {
        Storage.getFruits().clear();

        returnOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                10
        ));

        returnOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                25
        ));

        assertNotEquals(45, Storage.getFruits().get("apple"));
    }
}
