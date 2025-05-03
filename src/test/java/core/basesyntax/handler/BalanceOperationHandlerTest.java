package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseHandler;
import core.basesyntax.service.handler.ReturnHandler;
import core.basesyntax.service.handler.SupplyHandler;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static FruitShopService fruitShopService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> fruitMap = new HashMap<>();
        fruitMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        fruitMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        fruitMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        fruitMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(fruitMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    void balanceBanana_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setQuantity(300);
        List<FruitTransaction> transactions = List.of(transaction);
        fruitShopService.processOfOperations(transactions);
        boolean correct = Storage.fruits.get("banana").equals(300);
        Assertions.assertTrue(correct);
    }
}
