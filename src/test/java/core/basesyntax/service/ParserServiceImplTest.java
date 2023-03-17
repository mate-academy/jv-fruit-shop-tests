package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ParserServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String LINE_OK = "b,banana,100";
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parse_toList_isOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                KEY,VALUE);
        List<FruitTransaction> excepted = parserService.parse(List.of(HEADER, LINE_OK));
        assertEquals(List.of(fruitTransaction).size(), excepted.size());
    }

    @Test
    void parse_toList_isNotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                KEY,VALUE);
        List<FruitTransaction> excepted = parserService.parse(List.of(LINE_OK));
        assertNotEquals(List.of(fruitTransaction).size(), excepted.size());
    }
}
