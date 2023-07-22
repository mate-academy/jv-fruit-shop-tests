package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.impl.ErrorDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvTransactionParserTest {
    private TransactionParser parser = new CsvTransactionParser();

    @BeforeEach
    void setUp() {
        TransactionParser parser = new CsvTransactionParser();
    }

    @Test
    void parseTransaction_testParsing_Ok() {
        FruitTransaction[] transactions = parser.parseTransaction(new String[]{"first line",
                "b,banana,20"});
        String expectedFruit = "banana";
        Operation expectedOperation = Operation.BALANCE;
        int expectedQuantity = 20;
        assertEquals(transactions[0].getFruit(), expectedFruit);
        assertEquals(transactions[0].getOperation(), expectedOperation);
        assertEquals(transactions[0].getQuantity(), expectedQuantity);
    }

    @Test
    void parseTransaction_twoElementsInSecondString_NotOk() {
        try {
            parser.parseTransaction(new String[]{"first line", "1,2"});
        } catch (ErrorDataException e) {
            return;
        }
        fail("If input data is incorrect than ErrorDataException should be thrown");
    }
}
