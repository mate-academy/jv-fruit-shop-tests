package core.basesyntax.service;

import core.basesyntax.dto.FruitTransaction;

public interface ParserService {
    FruitTransaction parseLine(String line);
}
