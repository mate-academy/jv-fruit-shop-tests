package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataParsedImplTest {
    private static List<String> data;
    private static final FruitTransaction FIRST =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final FruitTransaction LAST =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    private final ParserService parserService = new DataParsedImpl();
    private List<FruitTransaction> fruitTransactionList;
    private List<FruitTransaction> emptyList;

    @BeforeAll
    static void beforeAll() {
        data = List.of("    type,fruit,quantity",
                "    b,banana,20",
                "    b,apple,100",
                "    s,banana,100");
    }

    @BeforeEach
    void setUp() {
        emptyList = parserService.parseData(Collections.emptyList());
        fruitTransactionList = parserService.parseData(data);
    }

    @Test
    void parseEmptyList_Ok() {
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void parseData_Ok() {
        assertFalse(fruitTransactionList.isEmpty());
    }

    @Test
    void getFirstElementFromList_Ok() {
        FruitTransaction expected = FIRST;
        FruitTransaction actual = fruitTransactionList.get(0);
        assertEquals(expected, actual);
    }

    @Test
    void getLastElementFromList_Ok() {
        FruitTransaction expected = LAST;
        FruitTransaction actual = fruitTransactionList.get(fruitTransactionList.size() - 1);
        assertEquals(expected, actual);
    }
}
