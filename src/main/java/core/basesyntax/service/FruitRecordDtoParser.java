package core.basesyntax.service;

import core.basesyntax.model.FruitRecordDto;

public interface FruitRecordDtoParser {
    FruitRecordDto parse(String line);
}
