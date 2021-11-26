package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParserImplTest {
    private final Parser parser = new ParserImpl(new ValidatorImpl());

    @Test
    public void parse_ValidDate_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("s,banana,100");
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("b", "banana", 20));
        transactions.add(new Transaction("s", "banana", 100));
        Assert.assertEquals(parser.parse(lines), transactions);
    }
}
