package core.basesyntax.service.performer;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.parser.Parser;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.basesyntax.model.Operation.BALANCE;
import static org.junit.jupiter.api.Assertions.*;

class TransactionPerformerImplTest {
    private static OperationStrategy operationStrategy;
    private static FruitStorageDao fruitStorageDao;
    private static TransactionPerformer transactionPerformer;
    private static List<FruitTransaction> transactions;
    private static Parser parser;

    @BeforeAll
    static void beforeAll() {
        parser = new Parser();
        fruitStorageDao = new FruitStorageDaoImpl();
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE, new BalanceOperationHandler(fruitStorageDao));
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler(fruitStorageDao));
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler(fruitStorageDao));
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler(fruitStorageDao));

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionPerformer = new TransactionPerformerImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }

    @Test
    void performTransactions_withValidData_ok() {
        transactions = parser.parseListToTransactionList(List.of(
                "b,apple,120",
                "s,banana,23",
                "r,banana,37", // 60
                "p,apple,100"
        ));

        // Perform the transactions

        /*assertDoesNotThrow(IllegalArgumentException.class,
                () -> transactionPerformer.performTransactions(transactions));*/

        transactionPerformer.performTransactions(transactions);
        int bananaActualQuantity = fruitStorageDao.getQuantity("banana");
        int appleActualQuantity = fruitStorageDao.getQuantity("apple");

        int appleExpectedQuantity = 20;
        int bananaExpectedQuantity = 60;
        assertEquals(appleExpectedQuantity, appleActualQuantity);
        assertEquals(bananaExpectedQuantity, bananaActualQuantity);

        //mockito


    }

    @Test
    void performTransactions_withEmptyData_notOk() {
        List<FruitTransaction> transactions = List.of();

        // Perform the transactions (no actual operations should occur)
        transactionPerformer.performTransactions(transactions);


    }
}