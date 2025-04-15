package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.DataConverter;

public class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    static FruitTransaction createTransaction(String operationCode, String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.getOperationFromCode(operationCode));
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    @Test
    void convertToTransaction_ok() {
        List<String> report = Arrays.asList(
                "operation,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );

        List<FruitTransaction> expectedTransactions = Arrays.asList(
                createTransaction("b", "banana", 20),
                createTransaction("s", "apple", 10)
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(report);
        assertEquals(expectedTransactions, transactions);
    }

    @Test
    void convertToTransactionEmptyList_notOk() {
        List<FruitTransaction> transactions = dataConverter
                .convertToTransaction(List.of("operation,fruit,quantity"));
        assertTrue(transactions.isEmpty(), "List should be empty when input contains only header");
    }
}
