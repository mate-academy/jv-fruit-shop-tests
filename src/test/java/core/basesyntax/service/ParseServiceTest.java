package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParseServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParseServiceTest {
    private static final List<String> EMPTY_DATA = Collections.emptyList();
    private static final int ELEMENTS_IN_LIST = 3;
    private static List<String> rawData;
    private static final FruitTransaction LAST =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    private static final FruitTransaction FIRST =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private List<FruitTransaction> fruitTransactions;
    private List<FruitTransaction> emptyFruitTransactions;
    private ParseService parseService = new ParseServiceImpl();

    @BeforeAll
    static void beforeAll() {
        rawData = List.of("    type,fruit,quantity",
                "    b,banana,20", "    b,apple,100",
                "    s,banana,100");
    }

    @BeforeEach
    void setUp() {
        emptyFruitTransactions = parseService.parseRawData(EMPTY_DATA);
        fruitTransactions = parseService.parseRawData(rawData);
    }

    @Test
    void parseEmptyData_ok() {
        assertTrue(emptyFruitTransactions.isEmpty());
    }

    @Test
    void elementsCreatedAfterParse_ok() {
        assertTrue(fruitTransactions.size() > 0);
    }

    @Test
    void firstElementFruitTransaction_oK() {
        FruitTransaction expected = FIRST;
        FruitTransaction actual = fruitTransactions.get(0);
        assertEquals(expected, actual);
    }

    @Test
    void lastElementFruitTransaction_Ok() {
        FruitTransaction expected = LAST;
        FruitTransaction actual = fruitTransactions.get(fruitTransactions.size() - 1);
        assertEquals(expected, actual);
    }

    @Test
    void existedElementsCountAfterParse_ok() {
        int actual = fruitTransactions.size();
        assertEquals(actual, ELEMENTS_IN_LIST);
    }
}
