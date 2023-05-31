package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_emptyInput_Ok() {
        List<Transaction> expected = List.of();
        List<Transaction> actual = parserService.parse(List.of());
        assertEquals("Invalid parse operation with empty input ",
                expected, actual);
    }

    @Test
    public void parse_validInput_Ok() {
        Transaction banana = new Transaction("p",new Fruit("banana"), 10);
        Transaction apple = new Transaction("s", new Fruit("apple"), 15);
        List<Transaction> expected = List.of(banana, apple);
        List<Transaction> actual = parserService.parse(List.of(
                "type,fruit,quantity", "p,banana,10", "s,apple,15"));
        assertEquals("Invalid parse operation ", expected, actual);
    }
}
