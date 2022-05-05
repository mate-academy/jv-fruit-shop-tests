package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_ValidData_Ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("s,lemon,8");
        input.add("p,apple,7");

        FruitTransaction lemonTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("lemon"), 8);

        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        appleTransaction.setFruit(new Fruit("apple"));
        appleTransaction.setQuantity(7);

        List<FruitTransaction> expected = List.of(lemonTransaction, appleTransaction);
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_emptyTransaction_Ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        List<FruitTransaction> expected = Collections.EMPTY_LIST;
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(expected, actual);
    }
}
