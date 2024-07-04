package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public interface FruitService {
    void applyTransaction(FruitTransaction transaction);

    Map<String, Integer> getReportData();

    void applyTransactions(List<FruitTransaction> transactions);
}
