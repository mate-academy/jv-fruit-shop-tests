package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ValidatorServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parserService;
    private static ValidatorService validatorService;

    @BeforeClass
    public static void init() {
        validatorService = new ValidatorServiceImpl();
        parserService = new ParserServiceImpl(validatorService);
    }

    @Test
    public void parseValidData_Ok() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,pineapple,100");
        data.add("s,lemon,100");

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        new Fruit("pineapple"), 100));
        expected.add(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        new Fruit("lemon"), 100));
        List<FruitTransaction> actual = parserService.parse(data);

        if (actual.size() == expected.size()) {
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i).getFruit(), actual.get(i).getFruit());
                assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
                assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidData_NotOk() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("balance,pineapple,100");
        data.add("supply,lemon,100");
        parserService.parse(data);
    }

    @Test(expected = RuntimeException.class)
    public void parseEmptyData_NotOk() {
        parserService.parse(Collections.EMPTY_LIST);
    }

    @Test(expected = RuntimeException.class)
    public void parseNullData_NotOk() {
        parserService.parse(null);
    }
}
