package core.basesyntax.service;

import static org.hamcrest.CoreMatchers.is;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParseserviceImplTest {
    private ParseService parseService = new ParserServiceImpl();

    @Test
    public void parsing_transactions_ok() {
        List<String> stringTransactions = new ArrayList<>();
        stringTransactions.add("type,fruit,quantity");
        stringTransactions.add("b,banana,20");
        stringTransactions.add("b,apple,100");
        stringTransactions.add("s,banana,100");
        List<Transaction> tranactions = new ArrayList<>();
        tranactions.add(new Transaction("b", new Fruit("banana"), 20));
        tranactions.add(new Transaction("b", new Fruit("apple"), 100));
        tranactions.add(new Transaction("s", new Fruit("banana"), 100));
        Assert.assertThat(parseService.parse(stringTransactions), is(tranactions));
    }
}
