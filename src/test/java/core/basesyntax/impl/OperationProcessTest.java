package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataProcesser;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationProcessTest {
    private static DataProcesser dataProcesser;
    private static List<FruitTransaction> fruitTransactionList;

    @BeforeAll
    static void setUp() {
        dataProcesser = new OperationProcess();
    }

    @Test
    void process_ValidTransactions_Ok() {
        fruitTransactionList = List.of(new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 5),
                new FruitTransaction(Operation.SUPPLY, "apple", 3),
                new FruitTransaction(Operation.RETURN, "apple", 2));
        dataProcesser.processData(fruitTransactionList);
        assertEquals(10, Storage.getStorage().get("apple"));
    }
}
