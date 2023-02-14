package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private TransactionParser transactionParser;
    private List<String> lines;

    @Before
    public void setUp() {
        transactionParser = new TransactionParserImpl();
        lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        lines.add("p,banana,13");
        lines.add("r,apple,10");
        lines.add("p,apple,20");
    }

    @Test
    public void parseFruitTransactions_validData_Ok() {
        List<FruitTransaction> actual = transactionParser.parseFruitTransactions(lines);
        assertEquals("banana", actual.get(1).getFruit());
        assertEquals(13, actual.get(2).getQuantity());
        assertEquals(FruitTransaction.Operation.RETURN, actual.get(3).getOperation());
        assertEquals(FruitTransaction.Operation.PURCHASE, actual.get(4).getOperation());
    }

    @Test
    public void parseFruitTransactions_validDatasizeOfList_Ok() {
        List<FruitTransaction> actual = transactionParser.parseFruitTransactions(lines);
        assertEquals(5, actual.size());
    }

    @Test
    public void parseFruitTransactions_NullForInput_NotOk() {
        assertThrows(RuntimeException.class, () -> transactionParser.parseFruitTransactions(null));
    }

    @Test
    public void parseFruitTransactions_EmptyIncomingList_NotOk() {
        assertThrows(RuntimeException.class, () ->
                transactionParser.parseFruitTransactions(new ArrayList<>()));
    }
}
