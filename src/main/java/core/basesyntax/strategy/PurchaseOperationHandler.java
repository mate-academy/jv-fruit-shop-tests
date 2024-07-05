package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Optional;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public void apply(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int amountToPurchase = fruitTransaction.getQuantity();

        Optional<Integer> currentBalance = Optional.ofNullable(Storage.fruitStorage.get(fruit));
        int balanceAfterPurchase = currentBalance.orElse(0) - amountToPurchase;

        if (balanceAfterPurchase < 0) {
            throw new RuntimeException("Insufficient stock for item " + fruit);
        }
        Storage.fruitStorage.put(fruit, balanceAfterPurchase);
    }
}
