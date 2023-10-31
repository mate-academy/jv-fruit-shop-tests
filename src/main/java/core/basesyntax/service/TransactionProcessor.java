package core.basesyntax.service;

import core.basesyntax.model.Transaction;
import java.util.List;

public interface TransactionProcessor {
    void processTransaction(List<Transaction> transactionsList);
}
