package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ProcessTransaction;
import core.basesyntax.service.impl.ProcessTransactionImpl;
import core.basesyntax.service.impl.transactions.BalanceHandlerImpl;
import core.basesyntax.service.impl.transactions.PurchaseHandlerImpl;
import core.basesyntax.service.impl.transactions.ReturnHandlerImpl;
import core.basesyntax.service.impl.transactions.SupplyHandlerImpl;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessTransactionImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            new HashMap<>();
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
    }

    @Test
    public void addDataIntoStorage_moveDataIntoStorage_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        Fruit fruit = new Fruit("apple");
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(20);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        ProcessTransaction processTransaction = new ProcessTransactionImpl(storageDao,
                new TransactionStrategyImpl(operationHandlerMap));
        processTransaction.addDataIntoStorage(fruitTransactionList);
        Integer actual = storageDao.getQuantity(fruit);
        Assert.assertEquals(20, (int) actual);
        Assert.assertTrue(Storage.getStorage().containsKey(fruit));
    }
}
