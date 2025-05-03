package core.basesyntax.servicetest;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.DataValidatorImpl;
import core.basesyntax.service.impl.ParserImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new DataValidatorImpl());
    }

    @Test
    public void parseToTransactionsList_RegularData_Ok() {
        List<String> initialData = List.of("type,fruit,quantity", "s,banana,100",
                "r,apple,8", "b,grape,20", "p,peach,38");
        List<Transaction> expected = List.of(new Transaction(Operation.SUPPLY, "banana", 100),
                new Transaction(Operation.RETURN, "apple", 8), new Transaction(Operation.BALANCE,
                        "grape", 20),
                new Transaction(Operation.PURCHASE, "peach", 38));
        Assert.assertEquals(String.format("\nExpected:\n%s\nbut was:\n%s",
                parser.parseToTransactionList(initialData),
                expected), parser.parseToTransactionList(initialData), expected);
    }

    @Test(expected = RuntimeException.class)
    public void parseToTransactionsList_IrregularData_NotOk() {
        List<String> actual = List.of("type,fruit,quantity", ",banana,100",
                "r,,8", "b,grape,-20", "p,peach,38");
        parser.parseToTransactionList(actual);
    }
}
