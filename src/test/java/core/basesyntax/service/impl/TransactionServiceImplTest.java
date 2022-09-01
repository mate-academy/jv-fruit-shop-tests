package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static final List<String> TEST_DATA
            = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static final List<Transaction> EXPECTED_DATA = List.of(
            new Transaction("b", new Fruit("banana"), 20),
            new Transaction("b", new Fruit("apple"), 100),
            new Transaction("s", new Fruit("banana"), 100),
            new Transaction("p", new Fruit("banana"), 13),
            new Transaction("r", new Fruit("apple"), 10),
            new Transaction("p", new Fruit("apple"), 20),
            new Transaction("p", new Fruit("banana"), 5),
            new Transaction("s", new Fruit("banana"), 50));
    private static TransactionService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new TransactionServiceImpl();
    }

    @Test
    public void parseService_equalsParseData_Ok() {
        List<Transaction> actual = parserService.parse(TEST_DATA);
        assertEquals(EXPECTED_DATA, actual);
    }
}
