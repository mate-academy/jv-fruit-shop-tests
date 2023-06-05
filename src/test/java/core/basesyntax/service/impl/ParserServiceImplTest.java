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
    private static List<String> dataList;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @Test
    void formatData_correctList_ok() {
        dataList = List.of("b,banana,80");
        List<FruitTransaction> fruitTransactionList = parserService.formatData(dataList);
        FruitTransaction expected = new FruitTransaction(
                BALANCE, "banana", 80);
        FruitTransaction actual = fruitTransactionList.get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void formatData_nullList_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                parserService.formatData(null));
    }
}
