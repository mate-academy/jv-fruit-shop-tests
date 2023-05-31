package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionExecutorImpl;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionExecutorImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static TransactionExecutor transactionExecutor;
    private static List<FruitTransaction> transactions;
    private static FruitTransaction bananaBalanceOperation;
    private static FruitTransaction bananaSecondBalanceOperation;
    private static FruitTransaction bananaSupplyOperation;
    private static FruitTransaction bananaPurchaseOperation;
    private static FruitTransaction bananaReturnOperation;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionExecutor = new TransactionExecutorImpl(operationStrategy);
        transactions = new ArrayList<>();
        bananaBalanceOperation = new FruitTransaction();
        bananaSecondBalanceOperation = new FruitTransaction();
        bananaSupplyOperation = new FruitTransaction();
        bananaPurchaseOperation = new FruitTransaction();
        bananaReturnOperation = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());

        bananaBalanceOperation.setOperation(FruitTransaction.Operation.getCode("b"));
        bananaBalanceOperation.setFruit("banana");
        bananaBalanceOperation.setAmount(100);

        bananaSecondBalanceOperation.setOperation(FruitTransaction.Operation.getCode("b"));
        bananaSecondBalanceOperation.setFruit("banana");
        bananaSecondBalanceOperation.setAmount(150);

        bananaSupplyOperation.setOperation(FruitTransaction.Operation.getCode("s"));
        bananaSupplyOperation.setFruit("banana");
        bananaSupplyOperation.setAmount(50);

        bananaPurchaseOperation.setOperation(FruitTransaction.Operation.getCode("p"));
        bananaPurchaseOperation.setFruit("banana");
        bananaPurchaseOperation.setAmount(70);

        bananaReturnOperation.setOperation(FruitTransaction.Operation.getCode("r"));
        bananaReturnOperation.setFruit("banana");
        bananaReturnOperation.setAmount(10);
    }

    @AfterEach
    void tearDown() {
        operationHandlerMap = new HashMap<>();
        transactions.clear();
        Storage.fruitData.clear();
    }

    @Test
    void execute_validListTransaction_Ok() {
        transactions.add(bananaBalanceOperation);
        transactions.add(bananaSupplyOperation);
        transactions.add(bananaPurchaseOperation);
        transactions.add(bananaReturnOperation);
        transactionExecutor.execute(transactions);
        assertEquals(90, Storage.fruitData
                .get(bananaBalanceOperation.getFruit()));
    }
}
