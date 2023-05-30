package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserServiceImplTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 80;
    private static ParserService parserService;
    private List<String> dataList;

    @BeforeAll
    static void beforeAll() {
        parserService = new ParserServiceImpl();
    }

    @BeforeEach
    void setUp() {
        dataList = new ArrayList<>();
    }

    @Test
    void parse_validData_ok() {
        dataList.add("b,banana,80");
        List<FruitTransaction> fruitTransactionList = parserService.formatData(dataList);
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, FRUIT_QUANTITY);
        FruitTransaction actual = fruitTransactionList.get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void parse_nullList_notOk() {
        Assertions.assertThrows(NullPointerException.class, () ->
                parserService.formatData(null));
    }
}
