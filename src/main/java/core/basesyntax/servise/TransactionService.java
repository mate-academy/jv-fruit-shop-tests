package core.basesyntax.servise;

import core.basesyntax.model.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> processData(List<String> records);
}
