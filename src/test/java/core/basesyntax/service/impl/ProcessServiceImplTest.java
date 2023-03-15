package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Test;

public class ProcessServiceImplTest {
    private ProcessServiceImpl processService = new ProcessServiceImpl();

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

}
