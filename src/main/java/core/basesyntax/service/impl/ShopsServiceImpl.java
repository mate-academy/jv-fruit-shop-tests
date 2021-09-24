package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopsService;
import java.util.List;

public class ShopsServiceImpl implements ShopsService {
    private static final int TEMPORARY_ELEMENT = 0;
    private OperationStrategy operationStrategy;

    public ShopsServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public boolean updateStorage(List<FruitOperationDto> dtos) {
        for (FruitOperationDto dto : dtos) {
            if (!Storage.fruitStorage.containsKey(dto.getFruitName())) {
                Storage.fruitStorage.put(dto.getFruitName(), TEMPORARY_ELEMENT);
            }
        }
        for (FruitOperationDto dto : dtos) {
            operationStrategy.get(dto.getType()).handler(dto);
        }
        return true;
    }
}
