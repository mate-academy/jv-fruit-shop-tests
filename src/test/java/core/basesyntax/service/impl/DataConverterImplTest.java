package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverterService;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;


public class DataConverterImplTest {
    private DataConverterService dataConverter;

    @BeforeEach
    public void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_validData_success() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,100",
                "p,banana,50"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(inputReport);

        assertNotNull(transactions);
        assertEquals(3, transactions.size());

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("banana", transactions.get(1).getFruit());
        assertEquals(100, transactions.get(1).getQuantity());
    }

    @Test
    public void convertToTransaction_invalidOperation_throwsIllegalArgumentException() {
        List<String> inputReport = Arrays.asList(
                "type,fruit,quantity",
                "x,banana,20"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(inputReport);
        });

        assertEquals("Invalid code: x", exception.getMessage());
    }
}

