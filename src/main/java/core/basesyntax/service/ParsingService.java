package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface ParsingService {
    FruitTransaction parse(String line);
}
