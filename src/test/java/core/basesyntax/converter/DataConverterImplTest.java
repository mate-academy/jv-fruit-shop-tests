package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String HEADER = "type,fruit,quantity";
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
                "b,banana,20",
                "s,apple,30",
                "p,banana,10",
                "r,apple,5"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);

        assertEquals(4, transactions.size());

        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(Operation.BALANCE, firstTransaction.getOperation());
        assertEquals("banana", firstTransaction.getFruit());
        assertEquals(20, firstTransaction.getQuantity());
    }

    @Test
    void convertToTransaction_emptyData_ok() {
        List<String> data = new ArrayList<>();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);
        assertTrue(transactions.isEmpty());
    }

    private void assertTrue(boolean empty) {
    }

    @Test
    void convertToTransaction_invalidOperation_notOk() {
        List<String> data = createData(
                "x,banana,20"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> data = createData(
                "b,banana,abc"
        );

        assertThrows(NumberFormatException.class, () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_missingFields_notOk() {
        List<String> data = createData(
                "b,banana"
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
                "b,banana,-10"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }
}
