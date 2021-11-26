package core.basesyntax.service.strategy;

import core.basesyntax.model.Transaction;

public interface OptionHandler {
    void apply(Transaction transaction);
}
