package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransferDto;

public interface OperationStrategy {
    void handle(FruitTransferDto dto);
}
