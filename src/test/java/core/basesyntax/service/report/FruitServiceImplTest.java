package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static List<TransactionDto> testTransactionDto;
    private static List<TransactionDto> testTransactionDtoBigPurchase;
    private static Map<Fruit, Integer> expectedStorage;
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        testTransactionDto = new ArrayList<>();
        TransactionDto transactionDto1 = new TransactionDto(OperationType.BALANCE,
                new Fruit("banana"), 100);
        TransactionDto transactionDto2 = new TransactionDto(OperationType.BALANCE,
                new Fruit("apple"), 100);
        TransactionDto transactionDto3 = new TransactionDto(OperationType.PURCHASE,
                new Fruit("banana"), 50);
        TransactionDto transactionDto4 = new TransactionDto(OperationType.RETURN,
                new Fruit("apple"), 50);
        TransactionDto transactionDto5 = new TransactionDto(OperationType.SUPPLY,
                new Fruit("apple"), 50);
        testTransactionDto.add(transactionDto1);
        testTransactionDto.add(transactionDto2);
        testTransactionDto.add(transactionDto3);
        testTransactionDto.add(transactionDto4);
        testTransactionDto.add(transactionDto5);
        testTransactionDtoBigPurchase = new ArrayList<>(testTransactionDto);
        TransactionDto transactionDto6 = new TransactionDto(OperationType.PURCHASE,
                new Fruit("apple"), 500);
        testTransactionDtoBigPurchase.add(transactionDto6);
        expectedStorage = new HashMap<>();
        expectedStorage.put(new Fruit("banana"), 50);
        expectedStorage.put(new Fruit("apple"), 200);
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategy(operationHandlerMap);
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @After
    public void tearDown() {
        FruitStorage.fruitsDataBase.clear();
    }

    @Test
    public void saveFruitByOperation_CorrectData_ok() {
        fruitService.saveFruitByOperation(testTransactionDto);
        assertEquals(FruitStorage.fruitsDataBase, expectedStorage);
    }

    @Test(expected = RuntimeException.class)
    public void saveFruitByOperation_PurchaseBiggerThanAmount_notOk() {
        fruitService.saveFruitByOperation(testTransactionDtoBigPurchase);
    }
}
