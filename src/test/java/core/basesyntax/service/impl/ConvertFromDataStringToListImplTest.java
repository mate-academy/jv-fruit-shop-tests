package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ConvertFromDataStringToList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvertFromDataStringToListImplTest {
    private static final String VALID_CSV_DATA = "operation,fruit,quantity" + System.lineSeparator()
            + "b,apple,10" + System.lineSeparator()
            + "p,orange,5" + System.lineSeparator()
            + "s,banana,15";
    private static final String INVALID_CSV_MISSING_FIELD = "operation,fruit,quantity"
            + System.lineSeparator()
            + "b,apple,10" + System.lineSeparator()
            + "p,orange" + System.lineSeparator()
            + "s,banana,15";
    private static final String INVALID_CSV_UNKNOWN_OPERATION = "operation,fruit,quantity"
            + System.lineSeparator()
            + "b,apple,10" + System.lineSeparator()
            + "q,orange,5" + System.lineSeparator()
            + "s,banana,15";
    private ConvertFromDataStringToList<FruitTransaction> converter;

    @BeforeEach
    public void setUp() {
        converter = new ConvertFromDataStringToListImpl();
    }

    @Test
    void convert_validDataToConvert_ok() {
        List<FruitTransaction> transactions = converter.convert(VALID_CSV_DATA);

        assertEquals(3, transactions.size());
        assertEquals(new FruitTransaction(Operation.BALANCE, "apple", 10), transactions.get(0));
        assertEquals(new FruitTransaction(Operation.PURCHASE, "orange", 5), transactions.get(1));
        assertEquals(new FruitTransaction(Operation.SUPPLY, "banana", 15), transactions.get(2));
    }

    @Test
    public void convert_invalidCsvDataMissingFields_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(INVALID_CSV_MISSING_FIELD);
        });
    }

    @Test
    public void convert_InvalidCsvDataUnknownOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(INVALID_CSV_UNKNOWN_OPERATION);
        });
    }
}
