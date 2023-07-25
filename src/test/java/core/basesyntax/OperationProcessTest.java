package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationProcess;
import core.basesyntax.service.impl.OperationProcessImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OperationProcessTest {
    private OperationProcess operationProcess;

    @Before
    public void setUp() {
        operationProcess = new OperationProcessImpl();
    }

    @Test
    public void processData_ValidTransactions_Ok() {
        Storage.storageMap.put("apple", 10);
        Storage.storageMap.put("banana", 20);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(Operation.PURCHASE, "apple", 5));
        transactions.add(new FruitTransaction(Operation.SUPPLY, "banana", 10));
        transactions.add(new FruitTransaction(Operation.RETURN, "apple", 2));
        operationProcess.processData(transactions);
        assertEquals(7, (int) Storage.storageMap.get("apple"));
        assertEquals(30, (int) Storage.storageMap.get("banana"));
    }
}
