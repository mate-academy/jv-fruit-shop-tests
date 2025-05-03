package core.basesyntax.data.servise;

import core.basesyntax.data.model.FruitTransaction;
import java.util.List;

public interface DataConverter {
    List<FruitTransaction> convertToOperation(List<String> reportList);
}
