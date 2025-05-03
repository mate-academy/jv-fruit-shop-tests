package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionParserTest {
    private TransactionParser transactionParser;

    @BeforeEach
    void setUp() {
        transactionParser = new TransactionParser();
    }

    @Test
    void parseTransaction_SuccessfulParse_Ok() {
        String line = "b,apple,10";
        FruitTransaction expectedTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction parsedTransaction = transactionParser.parseTransaction(line);
        assertEquals(expectedTransaction, parsedTransaction);
    }

    @Test
    void parseTransaction_InvalidNumType_NotOk() {
        String line = "b,apple,ten";
        assertThrows(IllegalArgumentException.class, () -> {
            transactionParser.parseTransaction(line);
        });
    }

    @Test
    void parseTransaction_InvalidOperationType_NotOk() {
        String line = "u,apple,ten";
        assertThrows(IllegalArgumentException.class, () -> {
            transactionParser.parseTransaction(line);
        });
    }

    @Test
    void parseTransaction_EmptyLine_NotOk() {
        String line = "";
        assertThrows(IllegalArgumentException.class, () -> {
            transactionParser.parseTransaction(line);
        });
    }

    @Test
    void parseTransaction_NullLine_NotOk() {
        String line = null;
        assertThrows(IllegalArgumentException.class, () -> {
            transactionParser.parseTransaction(line);
        });
    }

    @Test
    void parseTransaction_partsCount_NotOk() {
        String line = "r,apple,";
        assertThrows(IllegalArgumentException.class, () -> {
            transactionParser.parseTransaction(line);
        });
    }

}
