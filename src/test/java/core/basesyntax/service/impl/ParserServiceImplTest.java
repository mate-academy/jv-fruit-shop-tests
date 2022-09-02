package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
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

    @Test(expected = NullPointerException.class)
    public void parserService_Null_NotOk() {
        assertEquals(parserService.parse(null), Collections.EMPTY_LIST);
    }

    @Test
    public void parserService_EmptyList_Ok() {
        assertEquals(parserService.parse(Collections.EMPTY_LIST), Collections.EMPTY_LIST);
    }

    @Test
    public void parserService_getTransaction_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,20");
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        expected.add(new Transaction("b", new Fruit("apple"), 20));
        List<Transaction> actual = parserService.parse(lines);
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getOperation(), actual.get(i).getOperation());
            assertEquals(expected.get(i).getFruit(), actual.get(i).getFruit());
            assertEquals(expected.get(i).getQuantity(), actual.get(i).getQuantity());
        }

    }
}
