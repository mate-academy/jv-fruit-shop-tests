package basesyntax.service.operation;

import basesyntax.model.FruitTransaction;
import java.util.List;

public interface OperationService {
    void process(List<FruitTransaction> fruitTransactionList);
}
