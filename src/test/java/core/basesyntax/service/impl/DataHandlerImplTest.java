package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitShopException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataHandler;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.impl.TransactionStrategyImpl;
import core.basesyntax.transactionhandler.TransactionHandler;
import core.basesyntax.transactionhandler.impl.BalanceHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class DataHandlerImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap
            = new HashMap<>();
    private TransactionStrategy transactionStrategy
            = new TransactionStrategyImpl(transactionHandlerMap);
    private DataHandler dataHandler = new DataHandlerImpl(transactionStrategy);

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void processTransaction_validFruitTransaction_ok() {
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(storageDao));
        List<FruitTransaction> validFruitTransaction = new ArrayList<>();
        validFruitTransaction.add(new FruitTransaction("b", "apple", 10));
        dataHandler.processTransaction(validFruitTransaction);
        int actual = Storage.fruitStorage.get("apple");
        int expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    void processTransaction_withNotEmptyStorage_ok() {
        Storage.fruitStorage.put("apple", 1000);
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(storageDao));
        List<FruitTransaction> validFruitTransaction = new ArrayList<>();
        validFruitTransaction.add(new FruitTransaction("b", "apple", 10));
        dataHandler.processTransaction(validFruitTransaction);
        assertEquals(Storage.fruitStorage.get("apple"), 10);
    }

    @Test
    void processTransaction_nullFruitTransaction_notOk() {
        assertThrows(FruitShopException.class, () -> dataHandler.processTransaction(null));
    }
}
