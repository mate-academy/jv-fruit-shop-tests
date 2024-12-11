package core.basesyntax.data.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.model.FruitTransaction;
import core.basesyntax.data.model.OperationType;
import core.basesyntax.data.servise.DataConverter;
import core.basesyntax.data.servise.ipl.DataConverterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String HEADER = "type,fruit,quantity";
    private static final String BALANCE_APPLE = "b," + APPLE + ",20";
    private static final String SUPPLY_BANANA = "s," + BANANA + ",20";
    private static final String RETURN_APPLE = "r," + APPLE + ",20";
    private static final String PURCHASE_BANANA = "p," + BANANA + ",20";
    private static final String INVALID_OPERATION_CODE = "y," + APPLE + ",20";
    private static final String INVALID_QUANTITY = "b," + APPLE + ",abc";
    private static final int EXPECTED_SIZE = 4;
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int THIRD_INDEX = 2;
    private static final int FORTH_INDEX = 3;
    private static final int QUANTITY = 20;
    private static DataConverter dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToOperation_convertValidData_Ok() {
        List<String> data = List.of(
                HEADER, BALANCE_APPLE, SUPPLY_BANANA, RETURN_APPLE, PURCHASE_BANANA);
        List<FruitTransaction> operations = dataConverter.convertToOperation(data);

        assertEquals(EXPECTED_SIZE, operations.size());
        assertEquals(OperationType.BALANCE, operations.get(FIRST_INDEX).getOperation());
        assertEquals(APPLE, operations.get(FIRST_INDEX).getFruit());
        assertEquals(QUANTITY, operations.get(FIRST_INDEX).getQuantity());
        assertEquals(OperationType.SUPPLY, operations.get(SECOND_INDEX).getOperation());
        assertEquals(BANANA, operations.get(SECOND_INDEX).getFruit());
        assertEquals(QUANTITY, operations.get(SECOND_INDEX).getQuantity());
        assertEquals(OperationType.RETURN, operations.get(THIRD_INDEX).getOperation());
        assertEquals(APPLE, operations.get(THIRD_INDEX).getFruit());
        assertEquals(QUANTITY, operations.get(THIRD_INDEX).getQuantity());
        assertEquals(OperationType.PURCHASE, operations.get(FORTH_INDEX).getOperation());
        assertEquals(BANANA, operations.get(FORTH_INDEX).getFruit());
        assertEquals(QUANTITY, operations.get(FORTH_INDEX).getQuantity());
    }

    @Test
    void convertToOperation_invalidQuantity_NotOk() {
        List<String> data = List.of(HEADER, INVALID_QUANTITY);
        NumberFormatException exception = assertThrows(NumberFormatException.class,
                () -> dataConverter.convertToOperation(data));
        String expected = "For input string: \"abc\"";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void convertToOperation_convertEmptyList_NotOk() {
        List<String> data = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToOperation(data));
    }
}
