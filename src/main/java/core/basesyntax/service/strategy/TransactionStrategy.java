package core.basesyntax.service.strategy;

import core.basesyntax.model.Transaction;
import java.util.List;

public interface TransactionStrategy {
    void execute(List<Transaction> transactions);
}
