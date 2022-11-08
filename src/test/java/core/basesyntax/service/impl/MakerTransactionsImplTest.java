package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.MakerTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.operation.service.BalanceOperationHandler;
import core.basesyntax.service.impl.operation.service.PurchaserOperationHandler;
import core.basesyntax.service.impl.operation.service.ReturnerOperationHandler;
import core.basesyntax.service.impl.operation.service.SupplierOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class MakerTransactionsImplTest {
    private static final List<FruitTransaction> FRUIT_TRANSACTION_LIST =
            List.of(new FruitTransaction("b", "banana", 20),
                    new FruitTransaction("b", "apple", 100),
                    new FruitTransaction("r", "apple", 10),
                    new FruitTransaction("s", "banana", 10),
                    new FruitTransaction("p", "apple", 40));
    private static final String NAME_APPLE = "apple";
    private static final String NAME_BANANA = "banana";
    private static final int EXPECTED_APPLE_QUANTITY = 70;
    private static final int EXPECTED_BANANA_QUANTITY = 30;
    private static final int EXPECTED_SIZE_OF_STORAGE = 2;
    private final FruitsDao fruitsDao = new FruitDaoImpl();
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplierOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaserOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnerOperationHandler());
    private final OperationStrategy operationStrategy = new OperationStrategy(operationHandlerMap);
    private final MakerTransaction makerTransaction
            = new MakerTransactionsImpl(operationStrategy, fruitsDao);

    @Test
    public void do_transactionFromEmptyList_ok() {
        makerTransaction.doTransactions(List.of());
        assertEquals(0, Storage.fruitsStorage.size());
    }

    @Test
    public void do_transactionFromNotEmptyList_ok() {
        makerTransaction.doTransactions(FRUIT_TRANSACTION_LIST);
        assertEquals(EXPECTED_SIZE_OF_STORAGE, Storage.fruitsStorage.size());
        assertEquals(EXPECTED_APPLE_QUANTITY, fruitsDao.get(NAME_APPLE));
        assertEquals(EXPECTED_BANANA_QUANTITY, fruitsDao.get(NAME_BANANA));
    }

    @After
    public void afterEachTest() {
        Storage.fruitsStorage.clear();
    }
}
