package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void afterEach() {
        Storage.clear();
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
    void apply_newFruit_Ok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                new Fruit("banana"),
                20);
        operation.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"),20);
        expected.put(new Fruit("apple"), 10);
        Map<Fruit, Integer> actual = Storage.copy();
        Assertions.assertEquals(expected.get("apple"),actual.get("apple"));
        Assertions.assertEquals(expected.get("banana"),actual.get("banana"));
        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.keySet(), actual.keySet());
    }
}
