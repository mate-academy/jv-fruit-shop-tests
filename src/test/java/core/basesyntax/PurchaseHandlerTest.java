package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.utility.FruitTransactionValidator;
import core.basesyntax.utility.ValidateData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private TestValidator testValidator;
    private Storage storage;
    private FruitTransaction fruitTransaction;
    private PurchaseHandler handler;

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
        handler = new PurchaseHandler(testValidator);
    }

    @Test
    void purchaseTransaction_Ok() {
        storage.putQuantity("banana", 50);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        handler.handle(storage, fruitTransaction);
        Assertions.assertEquals(30, storage.getQuantity("banana"));
    }

    @Test
    void purchaseNullValidator_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> new PurchaseHandler(null));
        Assertions.assertTrue(ex.getMessage().contains("can't be null"));
    }

    @Test
    void purchaseStorageLessThanTransaction() {
        storage.putQuantity("banana", 30);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(40);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> handler.handle(storage, fruitTransaction));
        Assertions.assertTrue(ex.getMessage().contains("Not enough items"));
    }
}
