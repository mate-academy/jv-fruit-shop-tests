package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParserServiceCsvImplTest {
    private static final List<String> DATA = new ArrayList<>();
    private final ParserService parserService = new ParserServiceCsvImpl();

    @Test
    void parseData_validData_Ok() {
        DATA.add("fruit,quantity");
        DATA.add("b,lemon,30");
        DATA.add("s,apple,441");
        DATA.add("r,banana,2");
        DATA.add("b,pineapple,231");
        DATA.add("p,apple,37");
        DATA.add("s,lemon,73");
        DATA.add("r,pineapple,74");
        List<FruitTransaction> fruitTransactions = parserService.parseData(DATA);
        assertFalse(fruitTransactions.isEmpty());
    }

    @Test
    void parseData_notValidData_notOk() {
        DATA.add("r,pineapple,74");
        DATA.add(null);
        DATA.add(null);
        DATA.add(null);
        assertThrows(NullPointerException.class, () -> parserService.parseData(DATA));
    }
}
