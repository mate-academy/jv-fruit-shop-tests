package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
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
                "b,banana,20",
                "s,orange,75");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 44),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 75));
        assertEquals(expected, processService.getTransactions(actual));
    }

    @Test(expected = RuntimeException.class)
    public void process_addEmpty_notOk() {
        List<String> actual = List.of();
        processService.getTransactions(actual);
    }

    @Test (expected = RuntimeException.class)
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
