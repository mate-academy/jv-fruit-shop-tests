package core.basesyntax.service;

import java.util.List;
import core.basesyntax.model.FruitTransaction;

public interface Parser {
    List<FruitTransaction> parseInput(List<String> fileContent);
}
