package core.basesyntax.service.impl;

import static core.basesyntax.model.Operation.getAllAllowedOperationTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitConverter;
import java.util.List;
import org.junit.jupiter.api.Test;

class FruitConverterTest {
    private static final String VALID_STRING =
            "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator()
                    + "s,banana,100" + System.lineSeparator()
                    + "p,banana,13" + System.lineSeparator()
                    + "r,apple,10" + System.lineSeparator()
                    + "p,apple,20";
    private static final String STRING_WITH_INVALID_OPERATOR = "%,banana,20";
    private static final String STRING_WITH_NEGATIVE_AMOUNT_OF_FRUITS = "b,banana,-5";
    private static final String STRING_WITH_ZERO_AMOUNT_OF_FRUITS = "b,banana,-0";
    private final FruitConverter<FruitTransaction> fruitConverter =
            new FruitConverterImpl();

    @Test
    void convert_validString_Ok() {
        List<FruitTransaction> actual = fruitConverter.convert(VALID_STRING);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20));
        assertEquals(expected, actual);
    }

    @Test
    void convert_nullString_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> fruitConverter.convert(null));
        assertEquals("Input string is null", runtimeException.getMessage());
    }

    @Test
    void convert_invalidOperator_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> fruitConverter.convert(STRING_WITH_INVALID_OPERATOR));
        assertEquals("Invalid transaction type on line 2! It's %, but allowed types are: "
                + getAllAllowedOperationTypes(), runtimeException.getMessage());
    }

    @Test
    void convert_negativeAmountOfFruits_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> fruitConverter.convert(STRING_WITH_NEGATIVE_AMOUNT_OF_FRUITS));
        assertEquals("Invalid quantity on line 2! Quantity can't be below or equals zero! "
                + "Actual number is -5", runtimeException.getMessage());
    }

    @Test
    void convert_zeroAmountOfFruits_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> fruitConverter.convert(STRING_WITH_ZERO_AMOUNT_OF_FRUITS));
        assertEquals("Invalid quantity on line 2! Quantity can't be below or equals zero! "
                + "Actual number is 0", runtimeException.getMessage());

    }
}
