package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final String HEADER = "type,fruit,quantity";
    private static final String FIRST_LINE = "b,apple,10";
    private static final String SECOND_LINE = "p,apple,5";
    private static TransactionParser transactionParser;

    @BeforeAll
    static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    void parseTransactions_CreateList_Ok() {
        List<String> lines = List.of(HEADER, FIRST_LINE, SECOND_LINE);

        assertEquals(lines.size() - 1, transactionParser.parse(lines).size());

        List<FruitTransaction> fruitTransactions = transactionParser.parse(lines);
        assertEquals("BALANCE", String.valueOf(fruitTransactions.get(0).getOperation()));
        assertEquals("apple", fruitTransactions.get(0).getFruit());
        assertEquals(10, fruitTransactions.get(0).getQuantity());
    }
}
