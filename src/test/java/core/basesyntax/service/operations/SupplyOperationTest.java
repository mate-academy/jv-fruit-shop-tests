package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exceptions.InvalidFruitException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitTransaction transaction;
    private final OperationHandler operation = new SupplyOperation();

    @BeforeEach
    void beforeAll() {
        Storage.clear();
        Storage.put(new Fruit("apple"), 10);
    }

    @Test
    void apply_correctOperation_Ok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,new Fruit("apple"),1);
        operation.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"),11);
        Assertions.assertEquals(expected,Storage.copy());
    }

    @Test
    void apply_invalidFruit_NotOk() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"),
                20);
        Assertions.assertThrows(InvalidFruitException.class,
                () -> operation.apply(transaction));
    }
}
