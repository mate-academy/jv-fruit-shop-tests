package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private Storage storage;
    private SupplyHandler supplyHandler;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        supplyHandler = new SupplyHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,null, 0);
    }

    @Test
    public void supplyOperation_OperateTransaction_ok() {
        transaction.setQuantity(5);
        storage.put(transaction.getFruit(), 5);
        supplyHandler.operateTransaction(transaction, storage);
        int expectedQuantity = 10;
        int actualQuantity = storage.get(transaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
