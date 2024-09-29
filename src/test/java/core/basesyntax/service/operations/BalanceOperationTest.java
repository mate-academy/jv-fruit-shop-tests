package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransaction transaction;

    @Test
    void apply_correctOperation_Ok() {
        Storage.clear();
        transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,new Fruit("apple"),1);
        OperationHandler operation = new BalanceOperation();
        operation.apply(transaction);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"),1);
        Assertions.assertEquals(expected,Storage.copy());
    }
}
