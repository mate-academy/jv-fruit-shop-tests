package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static TransactionParser transactionParser;
    private static final List<String> validFileData = new ArrayList<>();
    private static final List<String> noSeparatorLine = new ArrayList<>();
    private static final List<String> nullStringInList = new ArrayList<>();
    private static final List<Transaction> expected = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
        validFileData.add("type,fruit,quantity");
        validFileData.add("b,banana,20");
        validFileData.add("p,apple,100");
        validFileData.add("r,berry,1");
        Transaction transaction1 = new Transaction();
        transaction1.setOperation(Transaction.Operation.BALANCE);
        transaction1.setProduct("banana");
        transaction1.setQuantity(20);
        expected.add(transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setOperation(Transaction.Operation.PURCHASE);
        transaction2.setProduct("apple");
        transaction2.setQuantity(100);
        expected.add(transaction2);
        Transaction transaction3 = new Transaction();
        transaction3.setOperation(Transaction.Operation.RETURN);
        transaction3.setProduct("berry");
        transaction3.setQuantity(1);
        expected.add(transaction3);
        noSeparatorLine.add("type,fruit,quantity");
        noSeparatorLine.add("b,banana,20");
        noSeparatorLine.add("p,apple,100");
        noSeparatorLine.add("r,berry,1");
        noSeparatorLine.add("rbanana10");
        nullStringInList.add("type,fruit,quantity");
        nullStringInList.add("b,banana,20");
        nullStringInList.add("p,apple,100");
        nullStringInList.add("r,berry,1");
        nullStringInList.add(null);
    }

    @Test
    void parse_validList_Ok() {
        List<Transaction> actual = transactionParser.parse(validFileData);
        assertEquals(expected, actual,
                "Method should create corresponding to fileData Transaction objects");
    }

    @Test
    void parse_nullList_notOk() {
        RuntimeException actual = assertThrows(IllegalArgumentException.class,
                () -> transactionParser.parse(null),
                "Exception should be thrown if List is null");
        assertEquals("List can't be null", actual.getMessage());
    }

    @Test
    void parse_emptyList_notOk() {
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class, () -> {
            transactionParser.parse(new ArrayList<>());
        });
        assertTrue(actual.getMessage().contains("List can't be empty: "));
    }

    @Test
    void parse_ListStringIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionParser.parse(nullStringInList),
                "If String in List is null, exception should be thrown");
    }

    @Test
    void parse_lineNotSeparated_notOk() {
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> transactionParser.parse(noSeparatorLine),
                "If string in List is null, exception should be thrown");
        assertEquals("Line was not separated correctly", actual.getMessage());
    }
}
