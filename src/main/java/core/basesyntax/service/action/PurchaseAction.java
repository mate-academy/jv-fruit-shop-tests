package core.basesyntax.service.action;

import static core.basesyntax.storage.Storage.storageOfFruits;

public class PurchaseAction implements ActionHandler {
    @Override
    public void count(String fruit, int amount) {
        checkAmount(amount);

        if (storageOfFruits.get(fruit) < amount) {
            throw new RuntimeException("Shop does not have a such amount of fruits");
        }

        int newBalance = storageOfFruits.get(fruit) - amount;
        checkAmount(newBalance);
        storageOfFruits.put(fruit, newBalance);
    }
}
