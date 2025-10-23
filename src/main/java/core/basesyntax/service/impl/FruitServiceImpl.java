package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitDto;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.FruitService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FruitServiceImpl implements FruitService {
    private final Map<FruitTransaction.Operation, FruitOperation> operationHandlers;

    public FruitServiceImpl(Map<FruitTransaction.Operation, FruitOperation> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public void makeOperationsOnFruitsDto(List<FruitDto> fruitDtos) {
        for (FruitDto fruitDto : fruitDtos) {
            FruitOperation operation = operationHandlers.get(fruitDto.getOperation());
            if (operation == null) {
                throw new RuntimeException("Operation handler not found for operation: "
                        + fruitDto.getOperation());
            }
            operation.apply(fruitDto);
        }
    }

    @Override
    public Map<String, Long> gerFruitReport() {
        return Storage.fruits.stream()
                .collect(Collectors.groupingBy(Fruit::getName, Collectors.counting()));
    }
}
