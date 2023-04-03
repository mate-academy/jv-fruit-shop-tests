package core.basesyntax.services.transaction;

import core.basesyntax.services.transaction.model.ProductTransaction;
import java.util.List;

public interface TransactionService {
    void process(List<ProductTransaction> productTransactions);
}
