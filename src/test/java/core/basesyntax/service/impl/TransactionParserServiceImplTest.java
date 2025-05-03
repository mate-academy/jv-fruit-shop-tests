package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {

    private static TransactionParserService parser;

    private String source;

    @BeforeClass
    public static void setUp() {
        parser = new TransactionParserServiceImpl();
    }

    @Before
    public void before() {
        source = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50";
    }

    @Test
    public void parse_defaultCase_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction("b", "banana", 20),
                new FruitTransaction("b", "apple", 100),
                new FruitTransaction("s", "banana", 100),
                new FruitTransaction("p", "banana", 13),
                new FruitTransaction("r", "apple", 10),
                new FruitTransaction("p", "apple", 20),
                new FruitTransaction("p", "banana", 5),
                new FruitTransaction("s", "banana", 50)
        );

        List<FruitTransaction> result = parser.parse(source);

        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i).getOperation(), expected.get(i).getOperation());
            assertEquals(result.get(i).getFruit(), expected.get(i).getFruit());
            assertEquals(result.get(i).getQuantity(), expected.get(i).getQuantity());
        }
    }

    @Test(expected = RuntimeException.class)
    public void parse_argumentNull_notOk() {
        parser.parse(null);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongRowFormat_notOk() {
        parser.parse(source + "kjksjdflkajlkdfja");
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongColumnsFormat_notOk() {
        parser.parse("asdasda" + source);
    }
}
