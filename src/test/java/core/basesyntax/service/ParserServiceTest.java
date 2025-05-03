package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parseService;

    @BeforeClass
    public static void setUp() {
        parseService = new ParserServiceImpl();
    }

    @Test
    public void parse_headerList_Ok() {
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = parseService.parse(List.of("type,fruit,quantity"));
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validData_Ok() {
        FruitTransaction appleTransaction = new FruitTransaction("b", new Fruit("apple"), 5);
        List<FruitTransaction> expected = new ArrayList<>(List.of(appleTransaction));
        List<FruitTransaction> actual = parseService
                .parse(List.of("type,fruit,quantity", "b,apple,5"));
        assertEquals(expected, actual);
    }

    @Test
    public void parse_emptyList_Ok() {
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual = parseService.parse(List.of());
        assertEquals(expected, actual);
    }

}
