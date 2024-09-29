package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.exceptions.InvalidFruitException;
import core.basesyntax.service.exceptions.InvalidOperationException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitTransaction transaction;

    @BeforeEach
    void beforeAll() {
        Storage.clear();
        Storage.put(new Fruit("apple"), 10);
    }

    @Test
    void apply_correctOperation_Ok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,new Fruit("apple"),1);
        OperationHandler operation = new ReturnOperation();
        operation.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"),11);
        Assertions.assertEquals(expected,Storage.copy());
    }

    @Test
    void apply_invalidOperation_Nok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,new Fruit("apple"),1);
        OperationHandler operation = new ReturnOperation();
        Assertions.assertThrows(InvalidOperationException.class,
                () -> operation.apply(transaction));
    }

    @Test
    void apply_invalidFruit_Nok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,new Fruit("banana"),1);
        OperationHandler operation = new ReturnOperation();
        Assertions.assertThrows(InvalidFruitException.class,
                () -> operation.apply(transaction));
    }

    @Test
    void apply_tooBigQuantity_Ok() {
        transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,new Fruit("apple"),Integer.MAX_VALUE);
        OperationHandler operation = new ReturnOperation();
        Assertions.assertThrows(ArithmeticException.class, () -> operation.apply(transaction));
    }
}
