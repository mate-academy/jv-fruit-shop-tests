package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ParseService;

class ParseServiceImplTest {
    private ParseService parseService;
    private List<String> lines;

    @BeforeEach
    void setUp() {
        parseService = new ParseServiceImpl();
        lines = new ArrayList<>();
    }

    @Test
    void parseTransactions_EmptyArray_Ok() {
        List<FruitTransaction> fruitTransactionList = parseService.parseTransactions(lines);
        assertTrue(fruitTransactionList.isEmpty());
    }

    @Test
    void parseTransactions_Ok() {
        lines.add("type,fruit,quantity");
        lines.add("b,apple,71");
        lines.add("s,banana,14");
        List<FruitTransaction> fruitTransactionList = parseService.parseTransactions(lines);
        assertEquals(2, fruitTransactionList.size());
    }

    @Test
    void parseTransactions_Value_Ok() {
        lines.add("type,fruit,quantity");
        lines.add("b,apple,71");
        lines.add("s,banana,14");
        List<FruitTransaction> fruitTransactionList = parseService.parseTransactions(lines);
        FruitTransaction fruitTransaction = fruitTransactionList.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
        assertEquals("apple", fruitTransaction.getFruit());
        assertEquals(71, fruitTransaction.getQuantity());
    }
}
