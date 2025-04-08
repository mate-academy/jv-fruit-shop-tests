package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Converter;

public class ConverterImplTest {

    private Converter converter;

    @BeforeEach
    void setConverter() {
        converter = new ConverterImpl();
    }

    @Test
    void convertTransaction_ok() {
        List<String> input = Arrays.asList(
                "operation,fruit,quantity",
                "b,orange,10",
                "s,grape,20"
        );

        List<Transaction> transactionList = converter.convertTransaction(input);

        assertEquals(2, transactionList.size(), "Transaction list must contain 2 transactions");
        assertEquals(Transaction.Operation.BALANCE, transactionList.get(0).getOperation());
        assertEquals("orange", transactionList.get(0).getFruit());
        assertEquals(10, transactionList.get(0).getQuantity());
        assertEquals(Transaction.Operation.SUPPLY, transactionList.get(1).getOperation());
        assertEquals("grape", transactionList.get(1).getFruit());
        assertEquals(20, transactionList.get(1).getQuantity());
    }

    @Test
    void convertEmptyList_notOk() {
        List<Transaction> transactionList = converter
                .convertTransaction(List.of("operation,fruit,quantity"));
        assertTrue(transactionList.isEmpty(),
                "Transaction list must be empty when containing only header");
    }

    @Test
    void transactionWithLessThan3Fields_notOk() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "p,orange"
        );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convertTransaction(data));

        assertTrue(exception.getMessage().contains("Invalid CSV format"));
    }

    @Test
    void invalidOperationCode_notOk() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "v,banana,25"
        );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convertTransaction(data));

        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void invalidQuantity_notOk() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "p,grape,fifteen"
        );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convertTransaction(data));

        assertTrue(exception.getMessage().contains("Invalid number found"));
    }

    @Test
    void negativeQuantity_notOk() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "r,orange,-10"
        );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> converter.convertTransaction(data));

        assertTrue(exception.getMessage().contains("Quantity must be a positive integer"));
    }

}
