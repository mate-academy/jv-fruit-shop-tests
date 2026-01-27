package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.utility.FruitTransactionValidator;
import core.basesyntax.utility.ValidateData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private TestValidator testValidator;
    private Storage storage;
    private FruitTransaction fruitTransaction;
    private BalanceHandler handler;

    private static class TestValidator implements FruitTransactionValidator {
        @Override
        public ValidateData validate(Storage storage, FruitTransaction fruitTransaction) {
            fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
            fruitTransaction.setFruit("banana");
            fruitTransaction.setQuantity(20);
            return new ValidateData(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        }
    }

    @BeforeEach
    void setUp() {
        testValidator = new TestValidator();
        storage = new InMemoryStorage();
        fruitTransaction = new FruitTransaction();
        handler = new BalanceHandler(testValidator);
    }

    @Test
    void successBalanceTransaction_ok() {
        handler.handle(storage, fruitTransaction);
        Assertions.assertEquals(20, storage.getQuantity("banana"));
    }

    @Test
    void transactionBalanceNullValidator_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BalanceHandler(null));
        Assertions.assertTrue(ex.getMessage().contains("can't be null"));
    }
}
