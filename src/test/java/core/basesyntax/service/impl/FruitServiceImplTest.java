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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private static final String BANANA = "banana";
    private static FruitService fruitService;
    private static FruitStrategy fruitStrategy;

    @BeforeAll
    static void beforeAll() {
        fruitService = new FruitServiceImpl();
        fruitStrategy = new FruitStrategyImpl(Map.of(
                BALANCE, new BalanceOperation(),
                RETURN, new ReturnOperation(),
                PURCHASE, new PurchaseOperation(),
                SUPPLY, new SupplyOperation()
        ));
    }

    @Test
    void processTransactions_validOperation_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(BALANCE,BANANA,100),
                new FruitTransaction(SUPPLY,BANANA,100),
                new FruitTransaction(PURCHASE,BANANA,150),
                new FruitTransaction(RETURN,BANANA,10)
        );
        fruitService.processTransactions(fruitTransactionList, fruitStrategy);
        Integer actual = Storage.storage.get(BANANA);
        Integer expect = 60;
        Assertions.assertEquals(expect, actual);
    }
}
