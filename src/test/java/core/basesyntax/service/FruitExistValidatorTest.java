package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitExistValidatorTest {

    private StorageInterface storageInterface;
    private FruitExistValidator fruitExistValidator;

    @BeforeEach
    void setUp() {
        storageInterface = new Storage();
        storageInterface.put("banana", 20);
        storageInterface.put("kiwi", 0);
        fruitExistValidator = new FruitExistValidator(storageInterface);
    }

    @Test
    void getCurrentQuantity_goodOutput_ok() {
        Assertions.assertEquals(20, fruitExistValidator.getCurrentQuantity("banana"));
    }

    @Test
    void getCurrentQuantity_amountZero_ok() {
        Assertions.assertEquals(0, fruitExistValidator.getCurrentQuantity("kiwi"));
    }

    @Test
    void getCurrentQuantity_fruitAbsent_ok() {
        Assertions.assertEquals(0, fruitExistValidator.getCurrentQuantity("orange"));
    }
}
