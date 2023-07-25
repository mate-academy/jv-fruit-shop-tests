package core.basesyntax.service;

import core.basesyntax.dto.FruitTransaction;

public interface ParserServiceImpl {
    FruitTransaction parseLine(String line);
}
