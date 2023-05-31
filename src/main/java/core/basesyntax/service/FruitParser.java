package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitParser {
    List<FruitTransaction> parse(List<String> lines);

    FruitTransaction parseTransaction(String line);
}

