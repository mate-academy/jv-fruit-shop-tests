package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.getAllAllowedOperationTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StringToListService;
import java.util.List;
import org.junit.jupiter.api.Test;

class StringToFruitTransactionListServiceTest {
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
    private final StringToListService<FruitTransaction> fruitTransactionListService =
            new StringToFruitTransactionListService();

    @Test
    void convert_validString_noException() {
        List<FruitTransaction> actual = fruitTransactionListService.convert(VALID_STRING);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        assertEquals(expected, actual);
    }

    @Test
    void convert_nullString_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> fruitTransactionListService.convert(null));
        assertEquals("Input string is null((", runtimeException.getMessage());
    }

    @Test
    void convert_invalidOperator_throwRuntimeException() {
        var runtimeException = assertThrows(RuntimeException.class,
                () -> fruitTransactionListService.convert(STRING_WITH_INVALID_OPERATOR));
        assertEquals("Invalid transaction type on line 2! It's %, but allowed types are: "
                + getAllAllowedOperationTypes(), runtimeException.getMessage());
    }

    @Test
    void convert_negativeAmountOfFruits_throwRuntimeException() {
        var runtimeExceptionWithNegativeAmount = assertThrows(RuntimeException.class,
                () -> fruitTransactionListService.convert(STRING_WITH_NEGATIVE_AMOUNT_OF_FRUITS));
        var runtimeExceptionWithZeroAmount = assertThrows(RuntimeException.class,
                () -> fruitTransactionListService.convert(STRING_WITH_ZERO_AMOUNT_OF_FRUITS));
        assertEquals("Invalid quantity on line 2! Quantity can't be below or equals zero! "
                + "Actual number is -5", runtimeExceptionWithNegativeAmount.getMessage());
        assertEquals("Invalid quantity on line 2! Quantity can't be below or equals zero! "
                + "Actual number is 0", runtimeExceptionWithZeroAmount.getMessage());
    }
}
