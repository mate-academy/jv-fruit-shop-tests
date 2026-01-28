package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.utility.FruitTransactionValidator;
import core.basesyntax.utility.ValidateData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private TestValidator testValidator;
    private Storage storage;
    private FruitTransaction fruitTransaction;
    private ReturnHandler handler;

    private static class TestValidator implements FruitTransactionValidator {
        @Override
        public ValidateData validate(Storage storage, FruitTransaction fruitTransaction) {
            return new ValidateData(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        }
    }

    @BeforeEach
    void setUp() {
        testValidator = new TestValidator();
        storage = new InMemoryStorage();
        fruitTransaction = new FruitTransaction();
        handler = new ReturnHandler(testValidator);
    }

    @Test
    void successReturnTransaction_Ok() {
        storage.putQuantity("apple", 30);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(50);
        handler.handle(storage, fruitTransaction);
        Assertions.assertEquals(80, storage.getQuantity("apple"));
    }

    @Test
    void transactionReturnNullValidator_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new ReturnHandler(null));
        Assertions.assertTrue(ex.getMessage().contains("can't be null"));
    }
}
