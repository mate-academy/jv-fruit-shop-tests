package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.ParcerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParcerServiceImplTest {
    private ParcerService parcerService;
    private List<String> transactions;

    @Before
    public void setUp() {
        parcerService = new ParcerServiceImpl();
        transactions = List.of("type,fruit,quantity","b,orange,200",
                "b,kiwi,1000", "s,orange,1000", "p,orange,130", "r,kiwi,100",
                "p,kiwi,200", "p,orange,50", "s,orange,500");
    }

    @Test (expected = RuntimeException.class)
    public void parseTransactions_transactionsNull_notOk() {
        parcerService.parseTransactions(null);
    }

    @Test (expected = RuntimeException.class)
    public void parseTransactions_transactionsEmpty_notOk() {
        List<String> emptyList = new ArrayList<>();
        parcerService.parseTransactions(emptyList);
    }

    @Test
    public void parseTransactions_transactionsRight_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "orange", 200));
        expected.add(new FruitTransaction(Operation.BALANCE, "kiwi", 1000));
        expected.add(new FruitTransaction(Operation.SUPPLY, "orange", 1000));
        expected.add(new FruitTransaction(Operation.PURCHASE, "orange", 130));
        expected.add(new FruitTransaction(Operation.RETURN, "kiwi", 100));
        expected.add(new FruitTransaction(Operation.PURCHASE, "kiwi", 200));
        expected.add(new FruitTransaction(Operation.PURCHASE, "orange", 50));
        expected.add(new FruitTransaction(Operation.SUPPLY, "orange", 500));
        List<FruitTransaction> actual = parcerService.parseTransactions(transactions);
        assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseOperation_wrong_operationMarker_notOk() {
        List<String> wrongTransactions = List.of("type,fruit,quantity","m,orange,200");
        parcerService.parseTransactions(wrongTransactions);
    }
}
