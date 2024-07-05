package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Optional;

public class SupplyOperationHandler implements OperationHandler {

    @Override
    public void apply(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int suppliedAmount = fruitTransaction.getQuantity();

        Optional<Integer> currentBalance = Optional.ofNullable(Storage.fruitStorage
                .get(fruit)); //currentBalance=50 suppliedAmount = 20
        int balanceAfterSupply = currentBalance.orElse(0) + suppliedAmount;
        Storage.fruitStorage.put(fruit, balanceAfterSupply);

    }
}
