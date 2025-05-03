package core.basesyntax.date;

import core.basesyntax.FruitTransaction;
import java.util.List;

public interface DateConverter {
    List<FruitTransaction> convertToTransaction(List<String> rawData);
}
