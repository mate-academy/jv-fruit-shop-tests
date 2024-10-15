package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_APPLE = "apple";
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    private List<String> createData(String... lines) {
        List<String> data = new ArrayList<>();
        data.add(HEADER);
        data.addAll(Arrays.asList(lines));
        return data;
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> data = createData(
                "b," + FRUIT_BANANA + ",20",
                "s," + FRUIT_APPLE + ",30",
                "p," + FRUIT_BANANA + ",10",
                "r," + FRUIT_APPLE + ",5"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(4, transactions.size());
        assertEquals(Operation.BALANCE, firstTransaction.getOperation());
        assertEquals(FRUIT_BANANA, firstTransaction.getFruit());
        assertEquals(20, firstTransaction.getQuantity());
    }

    @Test
    void convertToTransaction_emptyData_ok() {
        List<String> data = new ArrayList<>();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);
        assertTrue(transactions.isEmpty());
    }

    @Test
    void convertToTransaction_invalidOperation_notOk() {
        List<String> data = createData(
                "x," + FRUIT_BANANA + ",20"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> data = createData(
                "b," + FRUIT_BANANA + ",abc"
        );

        assertThrows(NumberFormatException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_missingFields_notOk() {
        List<String> data = createData(
                "b," + FRUIT_BANANA
        );

        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_nullData_notOk() {
        assertThrows(NullPointerException.class, () -> dataConverter.convertToTransaction(null));
    }

    @Test
    void convertToTransaction_emptyStringInData_notOk() {
        List<String> data = createData(
                ""
        );

        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_negativeQuantity_notOk() {
        List<String> data = createData(
                "b," + FRUIT_BANANA + ",-10"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }
}
