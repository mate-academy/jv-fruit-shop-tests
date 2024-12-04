package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.ReturnOperation;
import core.basesyntax.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.transaction.FruitTransaction;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        EnumMap<FruitTransaction.Operation, OperationHandler>
                operationEnumMap = new EnumMap<>(FruitTransaction.Operation.class);
        operationEnumMap.put(FruitTransaction.Operation.B, new BalanceOperation(fruitDao));
        operationEnumMap.put(FruitTransaction.Operation.R, new ReturnOperation(fruitDao));
        operationEnumMap.put(FruitTransaction.Operation.P, new PurchaseOperation(fruitDao));
        operationEnumMap.put(FruitTransaction.Operation.S,new SupplyOperation(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationEnumMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.getFruitsStorage().clear();
    }

    @Test
    void processTransaction_Ok() {
        fruitDao.saveOrUpdate("banana", 20);
        FruitTransaction banana = new FruitTransaction();
        banana.setQuantity(10);
        banana.setFruitName("banana");
        banana.setOperation(FruitTransaction.Operation.P);
        fruitDao.saveOrUpdate("apple", 20);
        FruitTransaction apple = new FruitTransaction();
        apple.setQuantity(30);
        apple.setOperation(FruitTransaction.Operation.S);
        apple.setFruitName("apple");

        List<FruitTransaction> transactionList = List.of(banana, apple);
        shopService.processTransactions(transactionList);

        Integer bananaQuantity = fruitDao.getFruitQuantity("banana");
        int expectedBananaQuantity = 10;
        Integer appleQuantity = fruitDao.getFruitQuantity("apple");
        int expectedAppleQuantity = 50;

        assertEquals(expectedBananaQuantity, bananaQuantity);
        assertEquals(expectedAppleQuantity, appleQuantity);
    }

    @Test
    void processTransaction_emptyList_Ok() {
        List<FruitTransaction> transactions = List.of();
        shopService.processTransactions(transactions);
    }
}
