package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.DataParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParserServiceTest {
    private static DataParserService dataParser;

    @BeforeAll
    static void beforeAll() {
        dataParser = new DataParserServiceImpl();
    }

    @Test
    void getTransactions_fromValidList_ok() {
        List<String> validList = List.of(
                "b,banana,20", "b,apple,100", "s,banana,100",
                "p,banana,13", "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<Transaction> transactions = dataParser.getTransactionsList(validList);
        assertEquals(validList.size(), transactions.size());
    }

    @Test
    void getTransaction_andCheckIt_ok() {
        List<String> validList = List.of("b,banana,20");
        List<Transaction> transactions = dataParser.getTransactionsList(validList);
        Transaction currentTransaction = transactions.get(0);
        Operation fruitOperationType = currentTransaction.getFruitOperationType();
        String fruitName = currentTransaction.getFruitName();
        int fruitValue = currentTransaction.getFruitValue();
        assertEquals(Operation.BALANCE, fruitOperationType);
        assertEquals("banana", fruitName);
        assertEquals(20, fruitValue);
    }

    @Test
    void getTransactions_fromEmptyList_ok() {
        List<String> emptyList = new ArrayList<>();
        List<Transaction> transactions = dataParser.getTransactionsList(emptyList);
        assertTrue(transactions.isEmpty(),
                String.format("Expected empty list, but was list with %n element(s)",
                        transactions.size()));
    }

    @Test
    void getTransactions_fromIncorrectOperation_notOk() {
        List<String> incorrectOperation = List.of("i,banana,100");
        assertThrows(RuntimeException.class,
                () -> dataParser.getTransactionsList(incorrectOperation));
    }

    @Test
    void getTransactions_fromIncorrectValue_notOk() {
        List<String> incorrectValue = List.of("b,banana,twenty");
        assertThrows(RuntimeException.class,
                () -> dataParser.getTransactionsList(incorrectValue));
    }

    @Test
    void getTransactions_fromEmptyString_notOk() {
        List<String> emptyString = List.of("");
        assertThrows(RuntimeException.class,
                () -> dataParser.getTransactionsList(emptyString));
    }
}
