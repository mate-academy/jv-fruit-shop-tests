package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface DataParser {
    FruitTransaction parse(String stringToParse);
}
