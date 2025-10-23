package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitDto;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitAddition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitAdditionTest {
    private FruitAddition fruitAddition;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        fruitAddition = new FruitAddition();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void apply_positiveQuantity_addsFruitsToStorage() {
        FruitDto dto = new FruitDto(FruitTransaction.Operation.SUPPLY, "banana", 3);

        fruitAddition.apply(dto);

        assertEquals(3, Storage.fruits.size());
        assertEquals("banana", Storage.fruits.get(0).getName());
    }

    @Test
    void apply_zeroQuantity_storageUnchanged() {
        FruitDto dto = new FruitDto(FruitTransaction.Operation.BALANCE, "apple", 0);

        fruitAddition.apply(dto);

        assertEquals(0, Storage.fruits.size());
    }
}
