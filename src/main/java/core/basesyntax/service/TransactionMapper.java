package core.basesyntax.service;

import core.basesyntax.model.Transaction;
import java.util.List;

public interface TransactionMapper {
    List<Transaction> map(List<String> records);
}
