package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionsHandlerImplTest {
    private FruitStorage fruitStorage;
    private FruitTransactionHandler fruitTransactionHandler;

    @BeforeEach
    public void setUp() {
        OperationStrategy operationStrategy = new OperationStrategy(new BalanceHandler(),
                new PurchaseHandler(),
                new ReturnHandler(),
                new SupplyHandler());
        fruitStorage = new FruitStorage();
        fruitTransactionHandler = new FruitTransactionsHandlerImpl(fruitStorage, operationStrategy);
    }

    @Test
    void check_Storage_ProcessTransaction_OK() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 50);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        fruitTransactionHandler.processTransactionsList(fruitTransactionList);

        Set<Map.Entry<String, Integer>> storageData = fruitStorage.getData();

        for (Map.Entry<String, Integer> testData : storageData) {
            Assertions.assertEquals(fruitTransaction.getQuantity(), testData.getValue());
            Assertions.assertEquals(fruitTransaction.getFruit(), testData.getKey());
        }

    }

}
