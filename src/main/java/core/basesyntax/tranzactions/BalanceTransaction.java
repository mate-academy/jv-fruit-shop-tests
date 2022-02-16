package core.basesyntax.tranzactions;

import core.basesyntax.service.StorageServiceImpl;
import core.basesyntax.stoage.Storage;

public class BalanceTransaction implements Transactions {
    @Override
    public void applyOperations(String fruit,Integer value) {
        if (!Storage.storage().isEmpty() && Storage.storage().get(fruit) != null
                && Storage.storage().get(fruit) != 0) {
            throw new RuntimeException("Balance of " + fruit + "has already existes");
        }
        StorageServiceImpl service = new StorageServiceImpl();
        service.addToStorage(fruit, value);
    }
}
