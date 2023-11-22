package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ConvertToFruitTransactionService {
    List<FruitTransaction> convert(List<String> rawData);
}
