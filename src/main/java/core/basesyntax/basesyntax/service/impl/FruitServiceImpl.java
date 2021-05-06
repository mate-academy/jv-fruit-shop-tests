package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.basesyntax.service.FruitService;
import core.basesyntax.basesyntax.service.FruitStrategy;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private FruitStrategy fruitStrategy;

    public FruitServiceImpl(FruitStrategy fruitStrategy) {
        this.fruitStrategy = fruitStrategy;
    }

    @Override
    public void saveDto(List<FruitRecordDto> dtos) {
        for (FruitRecordDto dto : dtos) {
            fruitStrategy.get(dto.getOperationType()).apply(dto);
        }
    }
}
