package core.basesyntax.service.interfaces;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitService {
    void processTransactions(List<FruitTransaction> transaction);
}
