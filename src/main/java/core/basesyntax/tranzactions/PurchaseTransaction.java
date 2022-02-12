package core.basesyntax.tranzactions;

import core.basesyntax.stoage.Storage;

public class PurchaseTransaction implements Transactions {
    @Override
    public void applyOperations(String fruit, Integer value) {
        if (Storage.storage().get(fruit) == null
                || Storage.storage().get(fruit) - value < 0) {
            throw new RuntimeException("You can`t solve more fruits that you have");
        }
        int count = Storage.storage().get(fruit);
        count -= value;
        Storage.storage().remove(fruit);
        Storage.storage().put(fruit, count);
    }
}
