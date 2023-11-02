package core.basesyntax.service.parser;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private static List<String> fileData;
    private static List<FruitTransaction> transactions;
    private static Parser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new Parser();
    }

    @Test
    void parseListToTransactionList_withValidData_ok() {
        fileData = List.of("fruit,quantity", "b,Apple,10", "s,Banana,5", "p,Orange,15");
        transactions = parser.parseListToTransactionList(fileData);

        assertEquals(3, transactions.size());
        assertEquals(Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("Apple", transactions.get(0).getFruit());
        assertEquals(10, transactions.get(0).getQuantity());

        assertEquals(Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("Banana", transactions.get(1).getFruit());
        assertEquals(5, transactions.get(1).getQuantity());
    }

    @Test
    void parseListToTransactionList_withEmptyData_ok() {
        fileData = List.of("fruit,quantity");
        transactions = parser.parseListToTransactionList(fileData);
        assertTrue(transactions.isEmpty());
    }
}