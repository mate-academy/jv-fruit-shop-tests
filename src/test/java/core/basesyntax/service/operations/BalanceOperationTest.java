package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exceptions.InvalidOperationException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransaction transaction;
    private OperationHandler operation;

    @BeforeEach
    void beforeEach() {
        Storage.clear();
        Storage.put(new Fruit("apple"), 10);
        transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,new Fruit("apple"),1);
        operation = new BalanceOperation();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void apply_balanceOperationInTransaction_Ok() {
        operation.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"),1);
        Map<Fruit, Integer> actual = Storage.copy();
        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.keySet(), actual.keySet());
    }

    @Test
    void apply_differentOperationInTransaction_NotOk() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,new Fruit("apple"),1);
        Assertions.assertThrows(InvalidOperationException.class,
                () -> operation.apply(transaction));
    }
}
