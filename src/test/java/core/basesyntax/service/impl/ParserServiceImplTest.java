package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;
    private static List<String> list;
    private static List<Transaction> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        parserService = new ParserServiceImpl();
        list = new ArrayList<>();
        expected = List.of(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("banana"), 20));
    }

    @Test
    public void correctOutput_Ok() {
        list.add("type,fruit,quantity");
        list.add("b,banana,20");
        List<Transaction> actual = parserService.parse(list);
        assertEquals(expected, actual);
    }
}
