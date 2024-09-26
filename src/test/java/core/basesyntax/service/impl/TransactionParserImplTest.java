package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TransactionParserImplTest {
    private static final int NUMBER_OF_ARGUMENTS = 3;
    private static final String SEPARATOR = ",";
    private static final int OPERATION_POS = 0;
    private static final int FRUIT_NAME_POS = 1;
    private static final int QUANTITY_POS = 2;
    TransactionParser parser;

    @BeforeEach
    void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    void nullList_NotOk() {
        List<String> strings = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> parser.parse(strings));
    }

    @Test
    void lessThanThreeParts_NotOk() {
        List<String> strings = new ArrayList<>();
        strings.add("banana,100");
        assertThrows(RuntimeException.class, () -> parser.parse(strings));
    }

    @Test
    void moreThanThreeParts_NotOk() {
        List<String> strings = new ArrayList<>();
        strings.add("p,sample,100,text");
        assertThrows(RuntimeException.class, () -> parser.parse(strings));
    }

    @Test
    void nonExistentOperationCode_NotOk() {
        List<String> strings = new ArrayList<>();
        strings.add("i,banana,100");
        assertThrows(RuntimeException.class, () -> parser.parse(strings));
    }

    @Test
    void nullFruitName_NotOk() {
        List<String> strings = new ArrayList<>();
        strings.add("i,,100");
        assertThrows(RuntimeException.class, () -> parser.parse(strings));
    }

    @Test
    void quantityIsNotNumber_NotOk() {
        List<String> strings = new ArrayList<>();
        strings.add("i,banana,hundred");
        assertThrows(RuntimeException.class, () -> parser.parse(strings));
    }

    @Test
    void validTransaction_Ok() {
        List<String> strings = new ArrayList<>();
        strings.add("s,apple,10");
        List<FruitTransaction> transactions = parser.parse(strings);
        FruitTransaction transaction = transactions.get(0);
        assertEquals(Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void multipleValidTransactions_Ok() {
        List<String> strings = new ArrayList<>();
        strings.add("b,apple,10");
        strings.add("p,banana,20");
        strings.add("r,orange,30");
        List<FruitTransaction> expectedTransactions = new ArrayList<>();
        expectedTransactions.add(new FruitTransaction(Operation.BALANCE, "apple", 10));
        expectedTransactions.add(new FruitTransaction(Operation.PURCHASE, "banana", 20));
        expectedTransactions.add(new FruitTransaction(Operation.RETURN, "orange", 30));
        List<FruitTransaction> actualTransactions = parser.parse(strings);
        for (int i = 0; i < actualTransactions.size(); ++i) {
            assertEquals(expectedTransactions.get(i), actualTransactions.get(i));
        }
    }
}
