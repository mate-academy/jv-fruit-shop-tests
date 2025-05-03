package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionEvaluatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitEvaluatorTest {
    private static TransactionEvaluatorImpl fruitEvaluator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitEvaluator = new TransactionEvaluatorImpl();
        Storage.storage.clear();
    }

    @Test
    public void processFruitTransaction_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10));
        fruitEvaluator.evaluate(list);
    }
}
