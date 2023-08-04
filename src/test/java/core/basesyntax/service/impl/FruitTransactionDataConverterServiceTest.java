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
    private static final String VALID_FRUIT_DATA = "type,fruit,quantity"
                                                    + System.lineSeparator()
                                                    + "b,banana,20";
    private static final String INVALID_FRUIT_NAME_DATA = "type,fruit,quantity"
                                                        + System.lineSeparator()
                                                        + "b,banana@,20";
    private static final String BLANK_FRUIT_NAME_DATA = "type,fruit,quantity"
                                                        + System.lineSeparator()
                                                        + "b,,20";
    private static final String INVALID_FRUIT_QUANTITY_DATA = "type,fruit,quantity"
                                                            + System.lineSeparator()
                                                            + "b,banana@,twenty";
    private static final String NEGATIVE_FRUIT_QUANTITY_DATA = "type,fruit,quantity"
                                                            + System.lineSeparator()
                                                            + "b,banana,-20";
    private static final String INVALID_FRUIT_OPERATION_DATA = "type,fruit,quantity"
                                                                    + System.lineSeparator()
                                                                    + "q,banana,20";
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
        FruitTransaction actual = dataConverter.convertDataToObjects(VALID_FRUIT_DATA).get(0);
        assertEquals(expected, actual);
    }

    @Test
    void convertDataToObjects_invalidFruitNameData_notOk() {
        InvalidDataFormatException thrown = assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(INVALID_FRUIT_NAME_DATA));
        assertEquals(INVALID_FRUIT_NAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void convertDataToObjects_blankFruitNameData_notOk() {
        InvalidDataFormatException thrown = assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(BLANK_FRUIT_NAME_DATA));
        assertEquals(BLANK_FRUIT_NAME_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void convertDataToObjects_invalidFruitQuantityData_notOk() {
        assertThrows(NumberFormatException.class,
                () -> dataConverter.convertDataToObjects(INVALID_FRUIT_QUANTITY_DATA));
    }

    @Test
    void convertDataToObjects_negativeFruitQuantity_notOk() {
        InvalidDataFormatException thrown = assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(NEGATIVE_FRUIT_QUANTITY_DATA));
        assertEquals(NEGATIVE_FRUIT_QUANTITY_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    void convertDataToObjects_notExistingFruitOperationData_notOk() {
        assertThrows(InvalidDataFormatException.class,
                () -> dataConverter.convertDataToObjects(INVALID_FRUIT_OPERATION_DATA));
    }
}
