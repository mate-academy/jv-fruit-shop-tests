package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exceptions.InvalidFruitException;
import core.basesyntax.service.exceptions.InvalidOperationException;
import core.basesyntax.service.exceptions.InvalidResultException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitTransaction transaction;

    @BeforeEach
    void beforeAll() {
        Storage.clear();
        Storage.put(new Fruit("apple"), 10);
    }

    @Test
    void apply_correctOperation_Ok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,new Fruit("apple"),1);
        OperationHandler operation = new PurchaseOperation();
        operation.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"),9);
        Assertions.assertEquals(expected,Storage.copy());
    }

    @Test
    void apply_invalidOperation_Nok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,new Fruit("apple"),1);
        OperationHandler operation = new PurchaseOperation();
        Assertions.assertThrows(InvalidOperationException.class,
                () -> operation.apply(transaction));
    }

    @Test
    void apply_invalidFruit_Nok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,new Fruit("banana"),1);
        OperationHandler operation = new PurchaseOperation();
        Assertions.assertThrows(InvalidFruitException.class,
                () -> operation.apply(transaction));
    }

    @Test
    void apply_invalidResult_Nok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,new Fruit("apple"),20);
        OperationHandler operation = new PurchaseOperation();
        Assertions.assertThrows(InvalidResultException.class, () -> operation.apply(transaction));
    }
}
