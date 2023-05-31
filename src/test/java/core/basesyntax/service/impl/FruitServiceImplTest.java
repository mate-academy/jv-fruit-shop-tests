package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.FruitStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private FruitService fruitService;
    private FruitStrategy fruitStrategy;

    @BeforeEach
    void setUp() {
        fruitService = new FruitServiceImpl();
        fruitStrategy = new FruitStrategyImpl(Map.of(
                BALANCE, new BalanceOperation(),
                RETURN, new ReturnOperation(),
                PURCHASE, new PurchaseOperation(),
                SUPPLY, new SupplyOperation()
        ));
    }

    @Test
    void getAllOperations_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(BALANCE,"banana",100),
                new FruitTransaction(SUPPLY,"banana",100),
                new FruitTransaction(PURCHASE,"banana",150),
                new FruitTransaction(RETURN,"banana",10)
        );
        fruitService.getAllOperationsStrategy(fruitTransactionList, fruitStrategy);
        Integer actual = Storage.storage.get("banana");
        Integer expect = 60;
        Assertions.assertEquals(expect, actual);
    }
}
