package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser parser;
    private static final List<FruitTransaction> EXPECTED = new ArrayList<>();
    private static final List<String> INPUT = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        parser = new TransactionParserImpl();

        EXPECTED.add(new FruitTransaction("test1", 0, Operation.BALANCE));
        EXPECTED.add(new FruitTransaction("test2", 1, Operation.PURCHASE));
        EXPECTED.add(new FruitTransaction("test3", 2, Operation.SUPPLY));
        EXPECTED.add(new FruitTransaction("test4", 3, Operation.RETURN));

        INPUT.add("type,fruit,quantity");
        INPUT.add("b,test1,0");
        INPUT.add("p,test2,1");
        INPUT.add("s,test3,2");
        INPUT.add("r,test4,3");
    }

    @Test
    public void parse_normal_Ok() {
        List<FruitTransaction> actual = parser.parse(INPUT);
        assertEquals(EXPECTED, actual);
    }
}
