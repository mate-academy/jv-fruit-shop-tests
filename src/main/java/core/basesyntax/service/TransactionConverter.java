package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface TransactionConverter {
    List<FruitTransaction> convertToTransactionList(List<String[]> dataFromFile);
}

