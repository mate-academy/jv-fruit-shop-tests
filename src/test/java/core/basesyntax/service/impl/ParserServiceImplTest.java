package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private static ParserServiceImpl parserService;

    @BeforeAll
    public static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void lineInParserTest() {
        List<String> parseTest = new ArrayList<>();
        parseTest.add("type,fruit,quantity");
        parseTest.add("b,apple,10");
        List<FruitTransaction> transactions = parserService.parse(parseTest);
        assertEquals(10, transactions.get(0).getQuantity());
    }

    @Test
    public void nameInParserTest() {
        List<String> parseTest = new ArrayList<>();
        parseTest.add("type,fruit,quantity");
        parseTest.add("b,apple,10");
        List<FruitTransaction> transactions = parserService.parse(parseTest);
        assertEquals("apple", transactions.get(0).getFruit());
    }

    @Test
    public void operationInParserTest() {
        List<String> parseTest = new ArrayList<>();
        parseTest.add("type,fruit,quantity");
        parseTest.add("b,apple,10");
        List<FruitTransaction> transactions = parserService.parse(parseTest);
        assertEquals(FruitTransaction.Operation.BALANCE,
                transactions.get(0).getOperation());
    }

    @Test
    public void unknownOperationTest() {
        List<String> parseTest = new ArrayList<>();
        parseTest.add("type,fruit,quantity");
        parseTest.add("x,apple,10");
        assertThrows(RuntimeException.class, () -> parserService.parse(parseTest));
    }

    @Test
    public void invalidCsvFormatTest() {
        List<String> parseTest = new ArrayList<>();
        parseTest.add("type,fruit,quantity");
        parseTest.add("b,apple");
        assertThrows(RuntimeException.class, () -> parserService.parse(parseTest));
    }
}
