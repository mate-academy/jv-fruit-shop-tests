package core.basesyntax.service.data;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ProcessDataService {
    void process(List<FruitTransaction> list);
}
