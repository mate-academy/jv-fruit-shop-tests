package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exception.InvalidFruitTypeException;
import core.basesyntax.exception.InvalidQuantityException;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataValidatorImplTest {
    private static final Operation VALID_OPERATION = Operation.BALANCE;
    private static final String VALID_FRUIT = "banana";
    private static final String INVALID_FRUIT = "w00((";
    private static final int VALID_QUANTITY = 1;
    private static final int INVALID_QUANTITY = -1;
    private static final DataValidatorImpl dataValidator = new DataValidatorImpl();

    private static FruitTransactionDto INCORRECT_FRUIT_DATA;
    private static FruitTransactionDto INCORRECT_QUANTITY_DATA;

    @BeforeAll
    static void setUp() {
        INCORRECT_FRUIT_DATA = new FruitTransactionDto(VALID_OPERATION,
                INVALID_FRUIT,
                VALID_QUANTITY);
        INCORRECT_QUANTITY_DATA = new FruitTransactionDto(VALID_OPERATION,
                VALID_FRUIT,
                INVALID_QUANTITY);
    }

    @Test
    void validateFruitType_IncorrectFruitType_NotOk() {
        assertThrows(InvalidFruitTypeException.class,
                () -> dataValidator.validate(INCORRECT_FRUIT_DATA));
    }

    @Test
    void validateQuantity_IncorrectQuantity_NotOk() {
        assertThrows(InvalidQuantityException.class,
                () -> dataValidator.validate(INCORRECT_QUANTITY_DATA));
    }

}
