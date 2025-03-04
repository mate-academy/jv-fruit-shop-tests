package core.basesyntax.service;

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
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = strategyProvider
                    .getHandler(transaction.getOperation());
            Map<String, Integer> inventory = inventoryService.getInventory();
            int currentQuantity = inventory.getOrDefault(transaction.getFruit(), 0);

            switch (transaction.getOperation()) {
                case PURCHASE:
                    if (transaction.getQuantity() < 0) {
                        throw new IllegalArgumentException("Quantity cannot be negative for "
                                + transaction.getFruit());
                    }
                    if (currentQuantity < transaction.getQuantity()) {
                        throw new IllegalArgumentException("Not enough stock for "
                                + transaction.getFruit());
                    }
                    break;
                case RETURN:
                    if (transaction.getQuantity() < 0) {
                        throw new IllegalArgumentException("Quantity cannot be negative for "
                                + transaction.getFruit());
                    }
                    break;
                case SUPPLY:
                case BALANCE:
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operation: "
                            + transaction.getOperation());
            }

            handler.apply(inventory, transaction.getFruit(), transaction.getQuantity());
        }
    }
}
