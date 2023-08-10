package core.basesyntax.filedata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConvertorImplTest {
    private static final DataConvertor dataConvertor = new DataConvertorImpl();
    private static final String[] VALID_DATA_INPUT = {"b,banana,20", "s,apple,100"};
    private static final String[] EMPTY_LINE_INPUT = new String[]{""};
    private static final String[] NUNBERS_INPUT = {"1234567890"};
    private static final String[] SYMBOLS_INPUT = {"!@#$%^&*()_?|:;<>"};
    private static final String[] INVALID_DATA_TYPE_INPUT = {"b,20,banana"};
    private static final String[] INVALID_FRUIT_TYPE_INPUT = {"b,orange,20"};
    private static final String[] NEGATIVE_QUANTITY_INPUT = {"b,banana,-20"};
    private static final String[] NULL_INPUT = null;
    private static final String VALID_EXCEPTION_MESSAGE = "Maybe you specified "
            + "a non-existent operation, you entered ";
    private static final String INVALID_FRUIT_TYPE_EXCEPTION_MESSAGE = "Unfortunately, "
            + "we don't have orange in our store";
    private static final String NEGATIVE_QUANTITY_EXCEPTION_MESSAGE = "Incorrect data, "
            + "the quantity cannot be negative.";
    private static final int FIRST_ARRAY_ELEMENT = 0;
    private static final List<FruitTransaction> VALID_OUTPUT = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100));

    @Test
    void convertData_validInput_isOk() {
        List<FruitTransaction> actual = dataConvertor.convertData(VALID_DATA_INPUT);
        assertEquals(VALID_OUTPUT, actual);
    }

    @Test
    void convertData_nullInput_notOk() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            dataConvertor.convertData(NULL_INPUT);
        });
    }

    @Test
    void convertData_emptyInput_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            dataConvertor.convertData(EMPTY_LINE_INPUT);
        });
        assertEquals(VALID_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void convertData_invalidDataTypeInput_notOk() {
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> {
            dataConvertor.convertData(INVALID_DATA_TYPE_INPUT);
        });
    }

    @Test
    void convertData_invalidFruitTypeInput_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            dataConvertor.convertData(INVALID_FRUIT_TYPE_INPUT);
        });
        assertEquals(INVALID_FRUIT_TYPE_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void convertData_negativeQuantityInput_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            dataConvertor.convertData(NEGATIVE_QUANTITY_INPUT);
        });
        assertEquals(NEGATIVE_QUANTITY_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void convertData_numbersInput_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            dataConvertor.convertData(NUNBERS_INPUT);
        });
        assertEquals(VALID_EXCEPTION_MESSAGE
                + NUNBERS_INPUT[FIRST_ARRAY_ELEMENT], exception.getMessage());
    }

    @Test
    void convertData_symbolsInput_notOk() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            dataConvertor.convertData(SYMBOLS_INPUT);
        });
        assertEquals(VALID_EXCEPTION_MESSAGE
                + SYMBOLS_INPUT[FIRST_ARRAY_ELEMENT], exception.getMessage());
    }
}
