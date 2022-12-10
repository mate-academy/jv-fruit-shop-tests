package core.basesyntax.services.imp;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParserServiceImplTest {
    private final TransactionParser transactionParser = new ParserServiceImpl();

    private final List<String> testList = new ArrayList<>();

    @Test(expected = RuntimeException.class)
    public void parse_listIsNull_notOK() {
        List<String> test = null;
        transactionParser.parse(test);
    }

    @Test
    public void parse_correctList_Ok() {
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("b,apple,100");

        List<FruitTransaction> expect = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",100));
        List<FruitTransaction> actual = transactionParser.parse(testList);
        Assert.assertEquals(expect,actual);
    }
}
