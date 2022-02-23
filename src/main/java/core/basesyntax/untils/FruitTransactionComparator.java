package core.basesyntax.untils;

import core.basesyntax.model.FruitTransaction;
import java.util.Comparator;

public class FruitTransactionComparator implements Comparator<FruitTransaction> {
    @Override
    public int compare(FruitTransaction fruitTransaction1, FruitTransaction fruitTransaction2) {
        if (fruitTransaction1.getOperation().equals(fruitTransaction2.getOperation())) {
            return 0;
        }
        if (fruitTransaction1.getOperation().equals(FruitTransaction.Operation.BALANCE)) {
            return -1;
        }
        if (fruitTransaction2.getOperation().equals(FruitTransaction.Operation.BALANCE)) {
            return 1;
        }
        return 0;
    }
}
