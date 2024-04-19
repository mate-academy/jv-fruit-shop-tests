package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.FruitTransaction;
import java.util.List;

public interface TransactionParser {
    List<FruitTransaction> parseData(List<String> dataFromFile);
}
