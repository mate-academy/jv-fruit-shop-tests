package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitDto;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitReduction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitReductionTest {
    private FruitReduction fruitReduction;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        fruitReduction = new FruitReduction();
        Storage.fruits.add(new Fruit("banana"));
        Storage.fruits.add(new Fruit("banana"));
        Storage.fruits.add(new Fruit("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void apply_validQuantity_removesFruits() {
        FruitDto dto = new FruitDto(FruitTransaction.Operation.PURCHASE, "banana", 2);

        fruitReduction.apply(dto);

        assertEquals(1, Storage.fruits.size());
    }

    @Test
    void apply_insufficientQuantity_throwsException() {
        FruitDto dto = new FruitDto(FruitTransaction.Operation.PURCHASE, "banana", 5);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitReduction.apply(dto));

        assertEquals("Not enough fruits to reduce", exception.getMessage());
    }

    @Test
    void apply_nonPositiveQuantity_throwsException() {
        FruitDto dto = new FruitDto(FruitTransaction.Operation.PURCHASE, "banana", 0);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitReduction.apply(dto));

        assertEquals("Quantity should be positive", exception.getMessage());
    }
}
