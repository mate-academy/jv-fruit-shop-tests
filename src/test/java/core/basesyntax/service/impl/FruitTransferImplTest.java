package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.ReadScvService;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransferImplTest {
    private static final String FILE_PATH = "inputTestFile.csv";
    private FruitTransactionDao fruitTransactionDao;
    private ReadScvService readScvService;
    private FruitTransactionService fruitTransactionService;
    private OperationStrategy operationStrategy;
    private FruitTransferImpl fruitTransfer;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoIml();
        readScvService = new ReadScvServiceImpl();
        fruitTransactionService = new FruitTransactionServiceImpl(fruitTransactionDao);
        Map<FruitTransaction.Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(fruitTransactionDao, fruitTransactionService));
        operationMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(fruitTransactionDao));
        operationMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(fruitTransactionDao));
        operationMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(fruitTransactionDao));
        operationStrategy = new OperationStrategyImp(operationMap);
        fruitTransfer = new FruitTransferImpl(operationStrategy, readScvService, FILE_PATH);
    }

    @Test
    void transfer_ok() {
        List<FruitTransaction> actualFruitList = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(237);
        actualFruitList.add(fruitTransaction);
        fruitTransfer.transfer();
        assertEquals(actualFruitList, fruitTransactionDao.getAllListDb());
        assertTrue(fruitTransactionDao.getAllListDb().contains(fruitTransaction));
    }
}
