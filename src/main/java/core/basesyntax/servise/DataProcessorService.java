package core.basesyntax.servise;

import core.basesyntax.servise.impl.FruitTransaction;
import java.util.List;

public interface DataProcessorService {
    void processingData(List<FruitTransaction> listOfTransactions);
}
