package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataParsedImplTest {
    private static List<String> data;
    private static FruitTransaction first;
    private static FruitTransaction last;
    private final ParserService parserService = new DataParsedImpl();

    @BeforeAll
    static void beforeAll() {
        data = List.of("    type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100");
        first = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        last = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    }

    @Test
    void parseData_emptyList_ok() {
        List<FruitTransaction> emptyList = parserService.parseData(Collections.emptyList());
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void parseData_correctData_ok() {
        List<FruitTransaction> fruitTransactionList = parserService.parseData(data);
        assertFalse(fruitTransactionList.isEmpty());
    }

    @Test
    void parseData_getFirstElementFromList_ok() {
        List<FruitTransaction> fruitTransactionList = parserService.parseData(data);
        FruitTransaction expected = first;
        FruitTransaction actual = fruitTransactionList.get(0);
        assertEquals(expected, actual);
    }

    @Test
    void parseData_getLastElementFromList_ok() {
        List<FruitTransaction> fruitTransactionList = parserService.parseData(data);
        FruitTransaction expected = last;
        FruitTransaction actual = fruitTransactionList.get(fruitTransactionList.size() - 1);
        assertEquals(expected, actual);
    }
}
