package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ReportService {
    List<String> create(List<FruitTransaction> fruitTransactions,
                        OperationStrategy operationStrategy);
}
