package core.basesyntax.service;

import core.basesyntax.model.FruitRecordDto;
import java.util.List;

public interface FruitRecordParserService {
    List<FruitRecordDto> getRecord(List<String> stringsFromFile);
}
