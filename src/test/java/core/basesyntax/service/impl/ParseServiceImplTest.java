package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;
    private static final String HEADER = "type,fruit,quantity";

    @BeforeClass
    public static void setUp() {
        parseService = new ParseServiceImpl();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parse_wrongData_NotOk() {
        parseService.parse(List.of(HEADER, "b,apple"));
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

    @Test(expected = RuntimeException.class)
    public void parse_nullData_NotOk() {
        parseService.parse(null);
    }
}
