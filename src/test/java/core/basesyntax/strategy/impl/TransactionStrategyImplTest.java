package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitShopException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.transactionhandler.TransactionHandler;
import core.basesyntax.transactionhandler.impl.BalanceHandler;
import core.basesyntax.transactionhandler.impl.PurchaseHandler;
import core.basesyntax.transactionhandler.impl.ReturnHandler;
import core.basesyntax.transactionhandler.impl.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TransactionStrategyImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(storageDao),
            FruitTransaction.Operation.PURCHASE, new PurchaseHandler(storageDao),
            FruitTransaction.Operation.RETURN, new ReturnHandler(storageDao),
            FruitTransaction.Operation.SUPPLY, new SupplyHandler(storageDao));
    private final TransactionStrategy strategy = new TransactionStrategyImpl(transactionHandlerMap);

    @Test
    void get_validOperation_ok() {
        assertEquals(strategy.get(FruitTransaction.Operation.BALANCE),
                new BalanceHandler(storageDao));
        assertEquals(strategy.get(FruitTransaction.Operation.PURCHASE),
                new PurchaseHandler(storageDao));
        assertEquals(strategy.get(FruitTransaction.Operation.RETURN),
                new ReturnHandler(storageDao));
        assertEquals(strategy.get(FruitTransaction.Operation.SUPPLY),
                new SupplyHandler(storageDao));
    }

    @Test
    void get_nullOperation_notOk() {
        assertThrows(FruitShopException.class, () -> strategy.get(null));
    }
}
