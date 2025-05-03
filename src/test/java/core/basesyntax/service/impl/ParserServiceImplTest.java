package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void parseData_validData_ok() {
        List<String> data = List.of("type,fruit,quantity", "b,apple,200");
        FruitTransaction expected = new FruitTransaction(BALANCE, "apple", 200);
        List<FruitTransaction> fruitTransactions = parserService.parseData(data);
        FruitTransaction actual = fruitTransactions.get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parseData_ivalidOperation_notOk() {
        List<String> data = List.of("type,fruit,quantity", "q,apple,200");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> parserService.parseData(data));
    }

    @Test
    void parseData_nullList_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> parserService.parseData(null));
    }
}
