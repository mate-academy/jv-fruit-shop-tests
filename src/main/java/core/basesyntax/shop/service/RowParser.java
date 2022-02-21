package core.basesyntax.shop.service;

import core.basesyntax.shop.impl.FruitTransaction;

public interface RowParser {
    FruitTransaction parseLine(String line);
}
