package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyProvider;
import java.util.List;
import java.util.Map;

public class FruitShopService {
    private final InventoryService inventoryService;
    private final OperationStrategyProvider strategyProvider;

    public FruitShopService(InventoryService inventoryService,
                            OperationStrategyProvider strategyProvider) {
        this.inventoryService = inventoryService;
        this.strategyProvider = strategyProvider;
    }

    public void processTransactions(List<FruitTransaction> transactions) {
        Map<String, Integer> inventory = Storage.inventory;

        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = strategyProvider
                    .getHandler(transaction.getOperation());

            if (!inventory.containsKey(transaction.getFruit())) {
                throw new IllegalArgumentException("Fruit not found: " + transaction.getFruit());
            }

            handler.apply(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
