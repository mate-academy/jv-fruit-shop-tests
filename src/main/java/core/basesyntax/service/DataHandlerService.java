package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface DataHandlerService {
    boolean handleData(List<FruitTransaction> fruitTransactions);
}
