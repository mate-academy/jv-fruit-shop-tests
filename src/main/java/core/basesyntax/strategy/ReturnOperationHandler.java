package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Optional;

public class ReturnOperationHandler implements OperationHandler {

    @Override
    public void apply(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int amountToReturn = fruitTransaction.getQuantity();
        if (!Storage.fruitStorage.containsKey(fruit)) {
            throw new RuntimeException("Cannot return a non-existing fruit: " + fruit);
        }
        if (amountToReturn <= 0) {
            throw new RuntimeException("Quantity to return must be positive: " + amountToReturn);
        }
        Optional<Integer> currentBalance = Optional.ofNullable(Storage.fruitStorage.get(fruit));
        int balanceAfterReturn = currentBalance.orElse(0) + amountToReturn;
        Storage.fruitStorage.put(fruit, balanceAfterReturn);
    }
}
