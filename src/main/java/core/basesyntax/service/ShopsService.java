package core.basesyntax.service;

import core.basesyntax.model.FruitOperationDto;
import java.util.List;

public interface ShopsService {
    boolean updateStorage(List<FruitOperationDto> dtos);
}
