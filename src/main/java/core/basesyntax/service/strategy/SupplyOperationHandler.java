package core.basesyntax.service.strategy;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        String fruitType = fruitTransaction.getFruit();
        if (FruitStorage.STORAGE_MAP.get(fruitType) == null) {
            throw new InvalidDataException("That is not possible to add "
                    + "a product that is not on the balance of the store.");
        }
        int newQuantity = FruitStorage.STORAGE_MAP.get(fruitType) + fruitTransaction.getQuantity();
        FruitStorage.STORAGE_MAP.put(fruitType, newQuantity);
    }
}
