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

    static Transaction createTransaction(String operationCode, String fruit, int quantity) {
        Transaction transaction = new Transaction();
        transaction.setOperation(Transaction.Operation.operationFromCode(operationCode));
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    @BeforeEach
    void setConverter() {
        converter = new ConverterImpl();
    }

    @Test
    void convertTransaction_ok() {
        List<String> actual = Arrays.asList(
                "operation,fruit,quantity",
                "b,orange,10",
                "s,grape,25"
        );

        List<Transaction> expected = Arrays.asList(
                createTransaction("b", "orange", 10),
                createTransaction("s", "grape", 25)
        );

        List<Transaction> transactionList = converter.convertTransaction(actual);

        assertEquals(expected, transactionList);
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
