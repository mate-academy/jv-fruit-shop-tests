package core.basesyntax.service;

import core.basesyntax.dto.ShopOperation;

public interface Parser {
    ShopOperation parseLine(String line);
}
