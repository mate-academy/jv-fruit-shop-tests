package core.basesyntax.service;

import core.basesyntax.model.Transaction;
import java.util.List;

public interface TransactionStrategy {
    void process(List<Transaction> transactions);
}
