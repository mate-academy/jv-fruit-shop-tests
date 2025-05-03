package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitDto;
import core.basesyntax.service.IteratorService;
import core.basesyntax.strategy.TransDistrStrategy;
import java.util.List;
import java.util.Map;

public class IteratorServiceImpl implements IteratorService {
    public IteratorServiceImpl() {
    }

    @Override
    public void iterate(List<FruitDto> fruitDtoList,
                        TransDistrStrategy transDistrStrategy,
                        Map<Fruit, Integer> storageMap) {
        for (FruitDto operation : fruitDtoList) {
            transDistrStrategy.choseStorageService(operation)
                    .actionToStorage(operation, storageMap);
        }
    }
}
