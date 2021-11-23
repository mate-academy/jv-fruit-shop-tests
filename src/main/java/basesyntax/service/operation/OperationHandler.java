package basesyntax.service.operation;

import basesyntax.model.FruitRecordDto;
import java.util.Map;

public interface OperationHandler {
    int getAmount(FruitRecordDto fruitRecordDto, Map<String, Integer> fruitsStorage);
}
