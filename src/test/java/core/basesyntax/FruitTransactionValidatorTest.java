package core.basesyntax;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.utility.FruitTransactionValidator;
import core.basesyntax.utility.FruitTransactionValidatorImpl;
import core.basesyntax.utility.ValidateData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionValidatorTest {
    private Storage storage;
    private FruitTransactionValidator validator;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage();
        validator = new FruitTransactionValidatorImpl();
        fruitTransaction = new FruitTransaction();
    }
@AfterEach
public void afterEachTest() {
    Storage.storage.clear();
}
    @Test
    void validateTransaction_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(40);
        ValidateData data = validator.validate(storage, fruitTransaction);
        Assertions.assertEquals("apple", data.getFruit());
        Assertions.assertEquals(40, data.getQuantity());
    }

    @Test
    void validateStorageNull_notOk() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(null, fruitTransaction));
        Assertions.assertTrue(ex.getMessage().contains("can't be null"));
    }

    @Test
    void validateFruitTransactionNull_notOk() {
        storage.putQuantity("banana", 50);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(storage, null));
        Assertions.assertTrue(ex.getMessage().contains("FruitTransaction can't be null"));
    }

    @Test
    void validateFruitIsNull_notOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(20);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(storage, fruitTransaction));
        Assertions.assertTrue(ex.getMessage().contains("Fruit can't be null"));
    }

    @Test
    void validateFruitIsEmpty_notOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("");
        fruitTransaction.setQuantity(20);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(storage, fruitTransaction));
        Assertions.assertTrue(ex.getMessage().contains("can't be empty"));
    }

    @Test
    void validateQuantityLessZero_notOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(-20);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> validator.validate(storage, fruitTransaction));
        Assertions.assertTrue(ex.getMessage().contains("must be >= 0"));
    }
}
