package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceTest {
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeEach
    void setOperationStrategy() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        operationStrategy = new OperationStrategy(handlers);
        shopService = new ShopService(operationStrategy);
    }

    @Test
    void processTransaction_IsOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "apple", 100)
        );
        shopService.processTransactions(transactions);
        Assert.assertEquals(100, Storage.getQuantity("apple"));
    }

}
