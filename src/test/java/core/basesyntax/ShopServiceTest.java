package core.basesyntax;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceTest {

    private FruitDao fruitDao;
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() {

        Storage.clear();
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        );
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(fruitDao, operationStrategy);
    }

    @Test
    void process_newFruitSupply_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50)
        );
        shopService.process(fruitTransactions);
    }

    @Test
    void process_nonExistentFruitPurchase_notOk() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10)
        );
        Assertions.assertThrows(RuntimeException.class, () -> {
            shopService.process(fruitTransactions);
        });
    }

    @Test
    void process_existentFruitPurchase_ok() {
        fruitDao.add(new Fruit("banana", 10));

        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5)
        );

        shopService.process(fruitTransactions);
    }

    @Test
    void process_newFruitBalance_ok() {
        List<FruitTransaction> fruitTransaction = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100)
        );
        shopService.process(fruitTransaction);
    }

    @Test
    void process_existentFruitBalance_ok() {
        fruitDao.add(new Fruit("banana", 100));

        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100)
        );

        shopService.process(fruitTransactions);
    }

    @Test
    void process_nonExistentFruitReturn_notOk() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 50)
        );
        Assertions.assertThrows(RuntimeException.class, () -> {
            shopService.process(fruitTransactions);
        });
    }
}

/*

process_existentFruitReturn_ok()*/
