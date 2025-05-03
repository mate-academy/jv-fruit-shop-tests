package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final DataConverter dataConverter = new DataConverterImpl();
    private static final int FIRS_TRANSACTION = 0;
    private static final int SECOND_TRANSACTION = 1;
    private static final int THIRD_TRANSACTION = 2;
    private static List<FruitTransaction> transactions;

    @BeforeAll
    public static void setUp() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,10",
                "s,banana,20",
                "p,banana,15"
        );
        transactions = dataConverter.convertToTransaction(data);
    }

    @Test
    public void test_convertToTransaction_sizeValidation() {
        assertEquals(3, transactions.size());
    }

    @Test
    public void test_convertToTransaction_operationsValidation() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(FIRS_TRANSACTION).getOperation());
        assertEquals(FruitTransaction.Operation.SUPPLY,
                transactions.get(SECOND_TRANSACTION).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE,
                transactions.get(THIRD_TRANSACTION).getOperation());
    }

    @Test
    public void test_convertToTransaction_fruitsValidation() {
        assertEquals("apple", transactions.get(FIRS_TRANSACTION).getFruit());
        assertEquals("banana", transactions.get(SECOND_TRANSACTION).getFruit());
    }

    @Test
    public void test_convertToTransaction_quantitiesValidation() {
        assertEquals(10, transactions.get(FIRS_TRANSACTION).getQuantity());
        assertEquals(20, transactions.get(SECOND_TRANSACTION).getQuantity());
        assertEquals(15, transactions.get(THIRD_TRANSACTION).getQuantity());
    }

    @Test
    public void test_emptyData_throwsException() {
        List<String> data = Collections.emptyList();

        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransaction(data));
    }
}
