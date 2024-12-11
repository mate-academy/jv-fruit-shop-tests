package core.basesyntax.data.servise;

import core.basesyntax.data.model.FruitTransaction;
import java.util.List;

public interface ShopService {
    void process(List<FruitTransaction> operations);
}
