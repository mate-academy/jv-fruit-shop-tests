package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ServiceException;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ProcessServiceImplTest {
    private ProcessServiceImpl processService = new ProcessServiceImpl();

    @Before
    public void beforeEachTest() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void process_return_Ok() {
        List<String> actual = List.of("type,fruit,quantity",
                "b,orange,44",
                "b,banana,30",
                "r,apple,60",
                "p,apple,20",
                "s,orange,75");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 44),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 60),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 75));
        assertEquals(expected, processService.getTransactions(actual));
    }

    @Test(expected = ServiceException.class)
    public void process_addNegativeBalance_notOk() {
        List<String> actualBalance = List.of("type,fruit,quantity",
                "b,orange,20",
                "b,orange,-44");
        processService.getTransactions(actualBalance);
    }

    @Test(expected = ServiceException.class)
    public void process_addNegativeSupply_notOk() {
        List<String> actualSupply = List.of("type,fruit,quantity",
                "b,orange,20",
                "s,orange,-44");
        processService.getTransactions(actualSupply);
    }

    @Test(expected = ServiceException.class)
    public void process_addNegativePurchase_notOk() {
        List<String> actualPurchase = List.of("type,fruit,quantity",
                "b,orange,20",
                "p,orange,-44");
        processService.getTransactions(actualPurchase);
    }

    @Test(expected = ServiceException.class)
    public void process_addNegativeReturn_notOk() {
        List<String> actualReturn = List.of("type,fruit,quantity",
                "b,orange,20",
                "r,orange,-44");
        processService.getTransactions(actualReturn);
    }

    @Test(expected = ServiceException.class)
    public void process_addEmpty_notOk() {
        List<String> actual = List.of();
        processService.getTransactions(actual);
    }

    @Test (expected = ServiceException.class)
    public void process_incorrectInput_notOk() {
        List<String> wrongType = List.of("type,fruit,quantity",
                "y,orange,44");
        processService.getTransactions(wrongType);

        List<String> wrongFruit = List.of("type,fruit,quantity",
                "b,John,44");
        processService.getTransactions(wrongFruit);

        List<String> missFruit = List.of("type,fruit,quantity",
                "b,,44");
        processService.getTransactions(missFruit);
    }
}
