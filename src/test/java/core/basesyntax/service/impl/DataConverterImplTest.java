package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String HEADER = "type,fruit,quantity";
    private static final String BALANCE_APPLE_DATA = "b," + APPLE + ",20";
    private static final String SUPPLY_BANANA_DATA = "s," + BANANA + ",15";
    private static final String PURCHASE_ORANGE_DATA = "p," + ORANGE + ",5";
    private static final String INVALID_OPERATION_DATA = "x," + APPLE + ",10";
    private static final String INVALID_QUANTITY_DATA = "b," + APPLE + ",abc";

    private static final int EXPECTED_SIZE_WITH_HEADER = 1;
    private static final int EXPECTED_SIZE_VALID_DATA = 3;

    private static final int QUANTITY_APPLE = 20;
    private static final int QUANTITY_BANANA = 15;
    private static final int QUANTITY_ORANGE = 5;
    private static final int INDEX_FIRST = 0;
    private static final int INDEX_SECOND = 1;
    private static final int INDEX_THIRD = 2;
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_ShouldSkipHeader_WhenHeaderPresent() {
        List<String> data = List.of(
                HEADER,
                BALANCE_APPLE_DATA
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        assertEquals(EXPECTED_SIZE_WITH_HEADER, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(INDEX_FIRST).getOperation());
        assertEquals(APPLE, transactions.get(INDEX_FIRST).getFruit());
        assertEquals(QUANTITY_APPLE, transactions.get(INDEX_FIRST).getQuantity());
    }

    @Test
    void convertToTransaction_ShouldConvertValidData() {
        List<String> data = List.of(
                BALANCE_APPLE_DATA,
                SUPPLY_BANANA_DATA,
                PURCHASE_ORANGE_DATA
        );
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);
        assertEquals(EXPECTED_SIZE_VALID_DATA, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(INDEX_FIRST).getOperation());
        assertEquals(APPLE, transactions.get(INDEX_FIRST).getFruit());
        assertEquals(QUANTITY_APPLE, transactions.get(INDEX_FIRST).getQuantity());
        assertEquals(FruitTransaction.Operation.SUPPLY,
                transactions.get(INDEX_SECOND).getOperation());
        assertEquals(BANANA, transactions.get(INDEX_SECOND).getFruit());
        assertEquals(QUANTITY_BANANA, transactions.get(INDEX_SECOND).getQuantity());
        assertEquals(FruitTransaction.Operation.PURCHASE,
                transactions.get(INDEX_THIRD).getOperation());
        assertEquals(ORANGE, transactions.get(INDEX_THIRD).getFruit());
        assertEquals(QUANTITY_ORANGE, transactions.get(INDEX_THIRD).getQuantity());
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenOperationCodeIsInvalid() {
        List<String> data = List.of(INVALID_OPERATION_DATA);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data)
        );
        assertEquals("Invalid operation code: x", exception.getMessage());
    }

    @Test
    void convertToTransaction_ShouldReturnEmptyList_WhenInputListIsEmpty() {
        List<String> data = new ArrayList<>();

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_ShouldThrowException_WhenQuantityIsInvalid() {
        List<String> data = List.of(INVALID_QUANTITY_DATA);

        assertThrows(NumberFormatException.class, () -> dataConverter.convertToTransaction(data));
    }

}
