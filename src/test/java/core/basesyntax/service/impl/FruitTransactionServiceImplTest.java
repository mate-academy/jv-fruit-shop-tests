package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static final String APPLE = "apple";
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static FruitDao fruitDao;
    private static FruitTransactionServiceImpl fruitTransactionService;
    private static OperationStrategy operationStrategy;
    private static FruitTransaction balance_transaction_apple;
    private static FruitTransaction purchase_transaction_apple;
    private static FruitTransaction supply_transaction_apple;
    private static FruitTransaction return_transaction_apple;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
                = Map.of(FruitTransaction.Operation.BALANCE,
                        new BalanceOperationHandler(fruitDao),
                FruitTransaction.Operation.SUPPLY,
                        new SupplyOperationHandler(fruitDao),
                FruitTransaction.Operation.PURCHASE,
                        new PurchaseOperationHandler(fruitDao),
                FruitTransaction.Operation.RETURN,
                        new ReturnOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
        balance_transaction_apple = new FruitTransaction();
        balance_transaction_apple.setQuantity(10);
        balance_transaction_apple.setFruit(APPLE);
        balance_transaction_apple.setOperation(FruitTransaction.Operation.BALANCE);
        purchase_transaction_apple = new FruitTransaction();
        purchase_transaction_apple.setQuantity(11);
        purchase_transaction_apple.setFruit(APPLE);
        purchase_transaction_apple.setOperation(FruitTransaction.Operation.PURCHASE);
        supply_transaction_apple = new FruitTransaction();
        supply_transaction_apple.setQuantity(3);
        supply_transaction_apple.setFruit(APPLE);
        supply_transaction_apple.setOperation(FruitTransaction.Operation.SUPPLY);
        return_transaction_apple = new FruitTransaction();
        return_transaction_apple.setQuantity(4);
        return_transaction_apple.setFruit(APPLE);
        return_transaction_apple.setOperation(FruitTransaction.Operation.RETURN);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void process_AllTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(balance_transaction_apple,
                supply_transaction_apple,
                purchase_transaction_apple,
                return_transaction_apple);
        fruitTransactionService.process(transactions);
        int expected = 6;
        int actual = Storage.fruitStorage.get(APPLE);
        assertEquals(expected, actual);
    }

    @Test
    void process_NullTransactions_NotOk() {
        assertThrows(NullPointerException.class, () ->
                fruitTransactionService.process(null));
    }

    @Test
    void process_InvalidTransactions_NotOk() {
        List<FruitTransaction> transactions = List.of(balance_transaction_apple,
                purchase_transaction_apple);
        assertThrows(RuntimeException.class, () -> fruitTransactionService.process(transactions));
    }
}
