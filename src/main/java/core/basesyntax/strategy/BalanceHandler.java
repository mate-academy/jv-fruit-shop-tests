package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import java.util.Objects;

public class BalanceHandler implements OperationHandler {
    @Override
    public void execute(String fruitName, int quantity) {
        Storage.storage.put(fruitName, quantity);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
