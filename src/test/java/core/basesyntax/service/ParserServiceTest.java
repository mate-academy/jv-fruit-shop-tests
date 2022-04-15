package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ValidationServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parserService;

    @BeforeClass
    public static void setUp() {
        parserService = new ParserServiceImpl(new ValidationServiceImpl());
    }

    @Test (expected = NullPointerException.class)
    public void parse_NullInput_NotOk() {
        parserService.parse(null);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void parse_EmptyInput_NotOk() {
        parserService.parse(new ArrayList<>());
    }

    @Test
    public void parse_ValidInput_Ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("s,banana,100");
        input.add("p,apple,13");

        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        bananaTransaction.setFruit(new Fruit("banana"));
        bananaTransaction.setQuantity(100);

        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        appleTransaction.setFruit(new Fruit("apple"));
        appleTransaction.setQuantity(13);

        List<FruitTransaction> expected = List.of(bananaTransaction, appleTransaction);
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_OnlyHeader_Ok() {
        List<String> input = new ArrayList<>();
        input.add("type,fruit,quantity");
        List<FruitTransaction> expected = Collections.EMPTY_LIST;
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(expected, actual);
    }
}
