package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitTransaction;

public interface DataOperationStrategy {
    void execute(FruitTransaction transaction);
}
