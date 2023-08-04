package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataFormatException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionDataConverterServiceTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String STANDARD_HEADER = "type,fruit,quantity";
    private static final String VALID_FRUIT_DATA = "b,banana,20";
    private static final String INVALID_FRUIT_NAME_DATA = "b,banana@,20";
    private static final String BLANK_FRUIT_NAME_DATA = "b,,20";
    private static final String INVALID_FRUIT_QUANTITY_DATA = "b,banana@,twenty";
    private static final String NEGATIVE_FRUIT_QUANTITY_DATA = "b,banana,-20";
    private static final String INVALID_FRUIT_OPERATION_DATA = "q,banana,20";
    private static final String INVALID_FRUIT_NAME_ERROR_MESSAGE = "Fruit name shouldn't consist "
            + "any digits and special characters!";
    private static final String BLANK_FRUIT_NAME_ERROR_MESSAGE = "Fruit name "
            + "shouldn't be a blank!";
    private static final String NEGATIVE_FRUIT_QUANTITY_ERROR_MESSAGE = "The number of fruits "
            + "can't be less than zero!";
    private static DataConverterService dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new FruitTransactionDataConverterService();
    }

    @Test
    void convertDataToObjects_validData_ok() {
        FruitTransaction expected = new FruitTransaction("banana", 20, Operation.BALANCE);
        String fruitData = generateValidDataFormat(VALID_FRUIT_DATA);
        FruitTransaction actual = dataConverter.convertDataToObjects(fruitData).get(0);
        assertEquals(expected, actual);
    }

    @Test
    void convertDataToObjects_invalidFruitNameData_notOk() {
        String fruitData = generateValidDataFormat(INVALID_FRUIT_NAME_DATA);
        InvalidDataFormatException actualException = assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(fruitData));
        assertEquals(INVALID_FRUIT_NAME_ERROR_MESSAGE, actualException.getMessage());
    }

    @Test
    void convertDataToObjects_blankFruitNameData_notOk() {
        String fruitData = generateValidDataFormat(BLANK_FRUIT_NAME_DATA);
        InvalidDataFormatException actualException = assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(fruitData));
        assertEquals(BLANK_FRUIT_NAME_ERROR_MESSAGE, actualException.getMessage());
    }

    @Test
    void convertDataToObjects_invalidFruitQuantityData_notOk() {
        String fruitData = generateValidDataFormat(INVALID_FRUIT_QUANTITY_DATA);
        assertThrows(NumberFormatException.class,
                () -> dataConverter.convertDataToObjects(fruitData));
    }

    @Test
    void convertDataToObjects_negativeFruitQuantity_notOk() {
        String fruitData = generateValidDataFormat(NEGATIVE_FRUIT_QUANTITY_DATA);
        InvalidDataFormatException actualException = assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(fruitData));
        assertEquals(NEGATIVE_FRUIT_QUANTITY_ERROR_MESSAGE, actualException.getMessage());
    }

    @Test
    void convertDataToObjects_notExistingFruitOperationData_notOk() {
        String fruitData = generateValidDataFormat(INVALID_FRUIT_OPERATION_DATA);
        assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(fruitData));
    }

    private String generateValidDataFormat(String fruitData) {
        return STANDARD_HEADER + LINE_SEPARATOR + fruitData;
    }
}
