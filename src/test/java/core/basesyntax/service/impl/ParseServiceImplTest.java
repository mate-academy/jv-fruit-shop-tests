package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParcer;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static FruitTransactionParcer parseService;
    private static final String HEADER = "type,fruit,quantity";

    @BeforeClass
    public static void setUp() {
        parseService = new ParseServiceImpl();
    }

    @Test
    public void parse_emptyList_Ok() {
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = parseService.parse(List.of());
        assertEquals(expected, actual);
    }

    @Test
    public void parse_onlyHeaderList_Ok() {
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = parseService.parse(List.of(HEADER));
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validData_Ok() {
        FruitTransaction appleTransaction = new FruitTransaction("b", new Fruit("apple"), 5);
        List<FruitTransaction> expected = new ArrayList<>(List.of(appleTransaction));
        List<FruitTransaction> actual = parseService.parse(List.of(HEADER, "b,apple,5"));
        assertEquals(expected, actual);
    }
}
