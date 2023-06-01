package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private ParserService parserService;
    private List<String> data;

    @BeforeEach
    void setUp() {
        parserService = new ParserServiceImpl();
        data = new ArrayList<>();
    }

    @Test
    void parseData_validData_ok() {
        data = List.of("type,fruit,quantity", "b,apple,200");
        FruitTransaction expected = new FruitTransaction(BALANCE, "apple", 200);
        List<FruitTransaction> fruitTransactions = parserService.parseData(data);
        FruitTransaction actual = fruitTransactions.get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parseData_ivalidOperation_notOk() {
        data = List.of("type,fruit,quantity", "q,apple,200");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> parserService.parseData(data));
    }

    @Test
    void parseData_nullList_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> parserService.parseData(null));
    }
}
