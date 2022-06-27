package basesyntax.service;

import basesyntax.model.FruitTransaction;
import java.util.List;

public interface Parser {
    List<FruitTransaction> parseData(List<String> input);
}
