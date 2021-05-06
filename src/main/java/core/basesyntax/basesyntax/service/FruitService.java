package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import java.util.List;

public interface FruitService {
    void saveDto(List<FruitRecordDto> dtos);
}
