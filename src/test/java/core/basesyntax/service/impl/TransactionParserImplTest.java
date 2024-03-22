package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.DataNotFoundException;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final List<String> VALID_DATA = List.of(
            "type,fruit,quantity",
            "b,banana,107",
            "b,apple,100",
            "s,banana,100"
    );
    private static final List<String> INVALID_DATA = List.of(
            "fruit,quantity",
            "banana,107",
            "apple,100",
            "banana,100"
    );
    private static final List<Transaction> EXPECTED_LIST_OF_TRANSACTIONS = List.of(
            new Transaction("b", "banana", 107),
            new Transaction("b", "apple", 100),
            new Transaction("s", "banana", 100)
    );
    private static final String TRANSACTION_ERROR_MESSAGE = "Invalid transaction data format";
    private static final String DATA_NOT_FOUND_ERROR_MESSAGE = "Data not found";
    private final TransactionParser transactionParser = new TransactionParserImpl();

    @Test
    void parse_ValidData_Ok() {
        List<Transaction> actualTransactions = transactionParser.parse(VALID_DATA);
        assertEquals(EXPECTED_LIST_OF_TRANSACTIONS, actualTransactions);
    }

    @Test
    void parse_InvalidData_NotOk() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                transactionParser.parse(INVALID_DATA));
        assertEquals(TRANSACTION_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void parse_DataIsNull_NotOk() {
        var exception = assertThrows(DataNotFoundException.class, () ->
                transactionParser.parse(null));
        assertEquals(DATA_NOT_FOUND_ERROR_MESSAGE, exception.getMessage());
    }
}
