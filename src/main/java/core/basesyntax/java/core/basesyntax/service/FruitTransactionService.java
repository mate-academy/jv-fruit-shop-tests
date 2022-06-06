package core.basesyntax.java.core.basesyntax.service;

import core.basesyntax.java.core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionService {
    void addTransaction(String data);

    List<FruitTransaction> getTransactionList();
}
