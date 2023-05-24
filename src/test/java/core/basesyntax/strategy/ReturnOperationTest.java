package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private Storage storage;
    private ReturnOperation returnOperation;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        returnOperation = new ReturnOperation();
        transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,null, 0);
    }

    @Test
    public void returnOperation_OperateTransaction_ok() {
        transaction.setQuantity(5);
        storage.put(transaction.getFruit(), 5);
        returnOperation.operateTransaction(transaction, storage);
        int expectedQuantity = 10;
        int actualQuantity = storage.get(transaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
