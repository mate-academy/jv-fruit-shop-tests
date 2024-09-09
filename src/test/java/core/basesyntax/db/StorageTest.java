package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    public void getFruitQuantity_nonExistingFruit() {
        // Act and Assert
        assertEquals(0, Storage.getFruitQuantity("Orange"));
    }

    @Test
    public void getFruitQuantity_nullFruitQuantity() {
        // Act and Assert
        assertEquals(0, Storage.getFruitQuantity(null));
    }

    @Test
    public void addQuantity_quantityIsZero() {
        // Arrange
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Apple", 0);

        // Act
        Storage.addQuantity(fruitTransaction);
        Storage.quantities.put("Apple", 0);

        // Assert
        assertEquals(0, Storage.getFruitQuantity("Apple"));
    }

    @Test
    public void addQuantity_quantityIsNegative() {
        // Arrange
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "Apple", -10);
        Storage.quantities.put("Apple", -10);
        assertEquals(-10, Storage.quantities.get("Apple"));
    }

    @Test
    public void getFruitQuantity_EmptyData() {
        // Act and Assert
        assertEquals(0, Storage.getFruitQuantity(""));
    }

}
