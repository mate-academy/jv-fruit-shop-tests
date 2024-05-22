package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface TransactionParserService {
    FruitTransaction parseFromString(String inputStrings);

    List<FruitTransaction> parseFromListStrings(List<String> dataFromFile);
}
