package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Filler;
import core.basesyntax.service.FruitOperationStrategy;
import java.util.List;

public class FillerImpl implements Filler {
    private FruitOperationStrategy fruitOperationStrategy;

    public FillerImpl(FruitOperationStrategy fruitOperationStrategy) {
        this.fruitOperationStrategy = fruitOperationStrategy;
    }

    @Override
    public void fillStorage(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruit: fruitTransactions) {
            fruitOperationStrategy.get(fruit.getOperation())
                    .updateAmount(fruit.getFruit(), fruit.getQuantity());
        }
    }
}
