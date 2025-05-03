package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final int FIRST_VALUE_FROM_LIST = 0;
    private static final int EXPECTED_TRANSACTION_LIST_SIZE = 2;
    private static final int EXPECTED_TRANSACTION_FRUIT_QUANTITY = 100;
    private static final String EXPECTED_TRANSACTION_FRUIT = "banana";

    @Test
    void convertToTransaction_ValidData_Ok() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> report = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,100",
                "s,apple,50");

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(report);
        assertEquals(EXPECTED_TRANSACTION_LIST_SIZE, transactions.size());
        assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(FIRST_VALUE_FROM_LIST).getOperation());
        assertEquals(EXPECTED_TRANSACTION_FRUIT,
                transactions.get(FIRST_VALUE_FROM_LIST).getFruit());
        assertEquals(EXPECTED_TRANSACTION_FRUIT_QUANTITY,
                transactions.get(FIRST_VALUE_FROM_LIST).getQuantity());
    }

    @Test
    void convertToTransaction_InvalidFormat_NotOk() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> report = Arrays.asList(
                "type,fruit,quantity",
                "s,10",
                "r,0"
        );

        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void convertToTransaction_NegativeQuantity_NotOk() {
        DataConverter dataConverter = new DataConverterImpl();
        List<String> report = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,-10");

        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(report));
    }
}
