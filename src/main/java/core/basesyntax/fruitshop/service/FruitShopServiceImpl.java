package core.basesyntax.fruitshop.service;

import core.basesyntax.fruitshop.model.RecordDto;
import core.basesyntax.fruitshop.service.FruitShopService;
import core.basesyntax.fruitshop.service.OperationStrategy;

import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private OperationStrategy operationStrategy;

    public FruitShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void fruitStorageModifier(List<RecordDto> dataInDto) {
        for (RecordDto infoLine : dataInDto) {
            operationStrategy.get(infoLine.getOperationType()).applyOperation(infoLine);
        }
    }
}
