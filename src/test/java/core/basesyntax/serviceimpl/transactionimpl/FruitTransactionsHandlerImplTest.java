package core.basesyntax.serviceimpl.transactionimpl;

import core.basesyntax.actions.Balance;
import core.basesyntax.actions.Purchase;
import core.basesyntax.actions.Return;
import core.basesyntax.actions.Supply;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.serviceintrface.operation.OperationStrategy;
import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;
import core.basesyntax.strategy.serviceintrface.transaction.FruitTransactionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionsHandlerImplTest {
    private Storage fruitStorage;
    private FruitTransactionHandler fruitTransactionHandler;

    @BeforeEach
    public void setUp() {
        OperationStrategy operationStrategy = new OperationStrategy(new Balance(),
                new Purchase(),
                new Return(),
                new Supply());
        fruitStorage = new Storage();
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

