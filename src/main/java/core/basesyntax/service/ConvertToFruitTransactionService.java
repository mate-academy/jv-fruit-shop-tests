package core.basesyntax.service;

import java.util.List;
import core.basesyntax.model.FruitTransaction;

public interface ConvertToFruitTransactionService {
    List<FruitTransaction> convert(List<String> rawData);
}
