package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String APPLE = "Apple";
    private static final String BANANA = "Banana";
    private static final String ORANGE = "Orange";
    private static final String GRAPES = "Grapes";
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convert_emptyData_notOk() {
        List<String> emptyData = Collections.emptyList();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(emptyData);
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void convert_nullData_notOk() {
        assertThrows(NullPointerException.class,
                () -> dataConverter.convertToTransaction(null));
    }

    @Test
    public void convert_invalidOperation_notOk() {
        DataConverterImpl dataConverter = new DataConverterImpl();
        List<String> data = Arrays.asList(
                "INVALID,Apple,10",
                "PURCHASE,Banana,abc",
                "RETURN,Orange"
        );
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }

    @Test
    public void convert_ValidData_ok() {
        List<String> data = Arrays.asList(
                "BALANCE,Apple,10",
                "SUPPLY,Banana,5",
                "PURCHASE,Orange,20",
                "RETURN,Grapes,15"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(data);
        assertEquals(4, transactions.size());
        FruitTransaction transaction1 = transactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction1.getOperation());
        assertEquals(APPLE, transaction1.getFruit());
        assertEquals(10, transaction1.getQuantity());
        FruitTransaction transaction2 = transactions.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction2.getOperation());
        assertEquals(BANANA, transaction2.getFruit());
        assertEquals(5, transaction2.getQuantity());
        FruitTransaction transaction3 = transactions.get(2);
        assertEquals(FruitTransaction.Operation.PURCHASE, transaction3.getOperation());
        assertEquals(ORANGE, transaction3.getFruit());
        assertEquals(20, transaction3.getQuantity());
        FruitTransaction transaction4 = transactions.get(3);
        assertEquals(FruitTransaction.Operation.RETURN, transaction4.getOperation());
        assertEquals(GRAPES, transaction4.getFruit());
        assertEquals(15, transaction4.getQuantity());
    }

    @Test
    public void convert_InvalidQuantity_notOk() {
        List<String> data = Arrays.asList(
                "BALANCE,Apple,10",
                "SUPPLY,Banana,-5",
                "PURCHASE,Orange,20",
                "RETURN,Grapes,15"
        );

        Assertions.assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(data));
    }
}
