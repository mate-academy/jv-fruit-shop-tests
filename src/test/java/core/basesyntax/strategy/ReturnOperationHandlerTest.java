package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {

    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void apply_fruitDoesNotExistInStorage_addsFruitWithQuantity() {
        returnOperationHandler.apply("banana", 10);

        assertEquals(10, Storage.fruits.get("banana"));
    }

    @Test
    void apply_fruitExistsInStorage_increasesQuantity() {
        Storage.fruits.put("apple", 5);
        returnOperationHandler.apply("apple", 3);

        assertEquals(8, Storage.fruits.get("apple"));
    }

    @Test
    void apply_zeroQuantity_doesNotChangeStorage() {
        Storage.fruits.put("orange", 10);
        returnOperationHandler.apply("orange", 0);

        assertEquals(10, Storage.fruits.get("orange"));
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        Storage.fruits.put("banana", 10);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                returnOperationHandler.apply("banana", -5)
        );

        String expectedMessage = "Quantity cannot be negative";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
