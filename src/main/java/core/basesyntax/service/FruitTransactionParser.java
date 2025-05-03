package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionParser {
    FruitTransaction parse(String line);

    List<FruitTransaction> parseLines(List<String> lines);
}
