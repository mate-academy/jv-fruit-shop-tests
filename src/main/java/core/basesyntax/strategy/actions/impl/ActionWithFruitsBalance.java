package core.basesyntax.strategy.actions.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.actions.ActionWithFruits;

public class ActionWithFruitsBalance implements ActionWithFruits {
    @Override
    public void getAmountAfterAction(String name, int amount) {
        if (name == null || name.equals("") || amount < 0) {
            throw new RuntimeException("Name couldn't be the null "
                    + "or empty and amount couldn't be less than 0");
        }
        if (Storage.get(name) == null) {
            Storage.put(name, amount);
            return;
        }
        Storage.put(name, Storage.get(name) + amount);
    }
}
