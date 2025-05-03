package core.basesyntax.strategy;

import core.basesyntax.dto.TransactionDto;

public interface FruitsStrategy {
    void apply(TransactionDto fruitDto);
}
