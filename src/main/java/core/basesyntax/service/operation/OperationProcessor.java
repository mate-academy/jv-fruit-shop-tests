package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface OperationProcessor {
    void processConvertedData(List<FruitTransaction> fruitTransactionList);
}
