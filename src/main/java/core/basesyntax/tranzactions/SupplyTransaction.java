package core.basesyntax.tranzactions;

import core.basesyntax.stoage.Storage;

public class SupplyTransaction implements Transactions {
    @Override
    public void applyOperations(String fruit, Integer value) {
        if (Storage.storage().get(fruit) == null) {
            throw new RuntimeException("Can`t supply non-existence fruit");
        }
        int count = Storage.storage().get(fruit);
        count += value;
        Storage.storage().remove(fruit);
        Storage.storage().put(fruit, count);
    }
}
