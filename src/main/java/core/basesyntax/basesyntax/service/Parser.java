package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.FruitTransaction;
import java.util.List;

public interface Parser {
    List<FruitTransaction> parse(List<String> lines);
}
