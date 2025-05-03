package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.enums.Operation;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionMapperTest {
    private static FruitTransactionMapper mapper;

    @BeforeAll
    static void beforeAll() {
        mapper = new FruitTransactionMapper();
    }

    @Test
    void toObject_validInput_ok() {
        String input = "r,banana,10";
        FruitTransaction expected = new FruitTransaction(Operation.RETURN, "banana", 10);

        FruitTransaction actual = mapper.toObject(input);

        Assertions.assertEquals(expected.getOperation(), actual.getOperation(),
                "Operations should match");
        Assertions.assertEquals(expected.getFruit(), actual.getFruit(),
                "Fruit names should match");
        Assertions.assertEquals(expected.getQuantity(), actual.getQuantity(),
                "Quantities should match");
    }

    @Test
    void toObject_invalidSplitLength_shouldThrowException() {
        String input = "r,banana";
        Assertions.assertThrows(IllegalArgumentException.class, () -> mapper.toObject(input),
                "Exception should be thrown if input string "
                        + "does not contain exactly three elements");
    }

    @Test
    void toObject_invalidOperation_shouldThrowException() {
        String input = "i,banana,10";
        Assertions.assertThrows(NoSuchElementException.class, () -> mapper.toObject(input),
                "Exception should be thrown if operation code is invalid");
    }

    @Test
    void toObject_invalidQuantityFormat_shouldThrowException() {
        String input = "r,banana,ten";
        Assertions.assertThrows(NumberFormatException.class, () -> mapper.toObject(input),
                "Exception should be thrown if quantity is not an integer");
    }

    @Test
    void toObject_negativeQuantity_shouldThrowException() {
        String input = "r,banana,-10";
        Assertions.assertThrows(IllegalArgumentException.class, () -> mapper.toObject(input),
                "Exception should be thrown if quantity is negative or zero");
    }

    @Test
    void toObject_zeroQuantity_shouldThrowException() {
        String input = "r,banana,0";
        Assertions.assertThrows(IllegalArgumentException.class, () -> mapper.toObject(input),
                "Exception should be thrown if quantity is negative or zero");
    }
}
