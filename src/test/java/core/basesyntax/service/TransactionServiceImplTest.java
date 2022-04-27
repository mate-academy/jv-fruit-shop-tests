package core.basesyntax.service;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static OperationStrategy operationStrategy;
    private static TransactionService transactionService;
    private static FruitTransactionDao fruitTransactionDao;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static Map<String, Integer> REQUIRED_STORAGE_CONTENT;
    private static List<FruitTransaction> DATA;

    @BeforeAll
    static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionService = new TransactionServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        REQUIRED_STORAGE_CONTENT = new HashMap<>();
        REQUIRED_STORAGE_CONTENT.put("banana",152);
        REQUIRED_STORAGE_CONTENT.put("apple",90);
        DATA = new ArrayList<>();
        FruitTransaction first = new FruitTransaction();
        first.setOperation("b");
        first.setFruitName("banana");
        first.setQuantity(20);
        DATA.add(first);
        FruitTransaction second = new FruitTransaction();
        second.setOperation("b");
        second.setFruitName("apple");
        second.setQuantity(100);
        DATA.add(second);
        FruitTransaction third = new FruitTransaction();
        third.setOperation("s");
        third.setFruitName("banana");
        third.setQuantity(100);
        DATA.add(third);
        FruitTransaction fourth = new FruitTransaction();
        fourth.setOperation("p");
        fourth.setFruitName("banana");
        fourth.setQuantity(13);
        DATA.add(fourth);
        FruitTransaction fifth = new FruitTransaction();
        fifth.setOperation("r");
        fifth.setFruitName("apple");
        fifth.setQuantity(10);
        DATA.add(fifth);
        FruitTransaction sixth = new FruitTransaction();
        sixth.setOperation("p");
        sixth.setFruitName("apple");
        sixth.setQuantity(20);
        DATA.add(sixth);
        FruitTransaction seventh = new FruitTransaction();
        seventh.setOperation("p");
        seventh.setFruitName("banana");
        seventh.setQuantity(5);
        DATA.add(seventh);
        FruitTransaction eighth = new FruitTransaction();
        eighth.setOperation("s");
        eighth.setFruitName("banana");
        eighth.setQuantity(50);
        DATA.add(eighth);
    }

    @Test
    void transactionService_checkValidList_Ok() {
        Map<String, Integer> actual = transactionService.countsFruitsAfterWorkDay(DATA);
        Assertions.assertEquals(REQUIRED_STORAGE_CONTENT, actual);
    }

    @Test
    void transactionService_nullList_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> transactionService.countsFruitsAfterWorkDay(null));
    }
}
