package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.OperationsStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.FruitStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static FruitService fruitService;
    private static FruitStrategy fruitStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationsStrategy> operationsStrategyMap = new HashMap<>();
        operationsStrategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationsStrategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationsStrategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationsStrategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        fruitService = new FruitServiceImpl();
        fruitStrategy = new FruitStrategyImpl(operationsStrategyMap);
    }

    @Test
    void getAllOperations_ok() {
        FruitTransaction fruitBalance = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,"banana",100);
        FruitTransaction fruitSupply = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,"banana",100);
        FruitTransaction fruitPurchase = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,"banana",150);
        FruitTransaction fruitReturn = new FruitTransaction(
                FruitTransaction.Operation.RETURN,"banana",10);
        List<FruitTransaction> fruitTransactionList = List.of(
                fruitBalance, fruitSupply, fruitPurchase, fruitReturn);
        fruitService.getAllOperationsStrategy(fruitTransactionList, fruitStrategy);
        Integer actual = Storage.storage.get("banana");
        Integer expect = 60;
        Assertions.assertEquals(expect, actual);
    }
}
