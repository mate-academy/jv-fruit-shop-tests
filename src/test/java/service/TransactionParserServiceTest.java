package service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.TransactionParserServiceImpl;

public class TransactionParserServiceTest {
    private static final String FIRST_LINE = "type,fruit,quantity";
    private static TransactionParserService parser;

    private String source;

    @BeforeClass
    public static void beforeAll() {
        parser = new TransactionParserServiceImpl();
    }

    @Before
    public void before() {
        source = new StringBuilder()
                .append(FIRST_LINE)
                .append(System.lineSeparator())
                .append("b,banana,85")
                .append(System.lineSeparator())
                .append("b,apple,70")
                .append(System.lineSeparator())
                .append("s,banana,25")
                .append(System.lineSeparator())
                .append("s,apple,30")
                .append(System.lineSeparator())
                .append("p,banana,30")
                .append(System.lineSeparator())
                .append("r,banana,10")
                .append(System.lineSeparator())
                .append("p,apple,40")
                .toString();
    }

    @Test
    public void parse_defaultCase_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 85),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 70),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 25),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 40)
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
        parser.parse(source + "frogman");
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongColumnsFormat_notOk() {
        parser.parse("frogman" + source);
    }

}
