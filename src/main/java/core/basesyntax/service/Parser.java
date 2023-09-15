package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface Parser {
    List<FruitTransaction> parseInput(List<String> fileContent);
}
