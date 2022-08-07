package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static List<FruitTransaction> expected;
    private static List<FruitTransaction> actual;
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
        expected = new ArrayList<>(List.of(
                new FruitTransaction(BALANCE, "banana",20),
                new FruitTransaction(BALANCE, "apple",100),
                new FruitTransaction(SUPPLY, "banana",100)));
    }

    @Test
    public void parseStringToFruit_Ok() {
        List<String> list = new LinkedList<>(List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100"));
        actual = parserService.parse(list);
        IntStream.range(0, expected.size()).forEach(i -> {
            assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i).getFruit(), actual.get(i).getFruit());
            assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
        });
    }

    @Test(expected = RuntimeException.class)
    public void parseStringToFruit_NotOk() {
        List<String> list = new LinkedList<>(List.of("typefruitquantity",
                "bbanana20", "bapple100", "sbanana00"));
        parserService.parse(list);
    }

    @Test
    public void parseSizeFruitList() {
        assertEquals(expected.size(),actual.size());
    }
}
