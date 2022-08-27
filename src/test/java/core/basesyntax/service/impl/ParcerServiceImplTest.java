package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.WrongDataException;
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
    public void setUp() throws Exception {
        parcerService = new ParcerServiceImpl();
        transactions = List.of("type,fruit,quantity","b,orange,200",
                "b,kiwi,1000", "s,orange,1000", "p,orange,130", "r,kiwi,100",
                "p,kiwi,200", "p,orange,50", "s,orange,500");
    }

    @Test (expected = WrongDataException.class)
    public void parseTransactions_transactionsNull_notOk() {
        parcerService.parseTransactions(null);
    }

    @Test (expected = WrongDataException.class)
    public void parseTransactions_transactionsEmpty_notOk() {
        List<String> emptyList = new ArrayList<>();
        parcerService.parseTransactions(emptyList);
    }

    @Test
    public void parseTransactions_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(Operation.BALANCE, "orange", 200));
        expectedList.add(new FruitTransaction(Operation.BALANCE, "kiwi", 1000));
        expectedList.add(new FruitTransaction(Operation.SUPPLY, "orange", 1000));
        expectedList.add(new FruitTransaction(Operation.PURCHASE, "orange", 130));
        expectedList.add(new FruitTransaction(Operation.RETURN, "kiwi", 100));
        expectedList.add(new FruitTransaction(Operation.PURCHASE, "kiwi", 200));
        expectedList.add(new FruitTransaction(Operation.PURCHASE, "orange", 50));
        expectedList.add(new FruitTransaction(Operation.SUPPLY, "orange", 500));
        List<FruitTransaction> actualList = parcerService.parseTransactions(transactions);
        int actual = 0;
        for (int i = 0; i < actualList.size(); i++) {
            if (actualList.get(i).equals(expectedList.get(i))) {
                actual++;
            }
        }
        int expected = 8;
        assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseOperation_wrong_operationMarker_notOk() {
        List<String> wrongTransactions = List.of("type,fruit,quantity","m,orange,200");
        parcerService.parseTransactions(wrongTransactions);
    }
}
