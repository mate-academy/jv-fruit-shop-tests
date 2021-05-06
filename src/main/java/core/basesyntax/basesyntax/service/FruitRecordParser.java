package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import java.util.List;

public interface FruitRecordParser {
    List<FruitRecordDto> parse(List<String> data);
}
