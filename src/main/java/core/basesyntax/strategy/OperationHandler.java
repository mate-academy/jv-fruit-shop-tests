package core.basesyntax.strategy;

import core.basesyntax.model.dto.FruitDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationHandler {
    public static final Map<String, Operation> operationType = new HashMap<>();

    {
        operationType.put("b", new BalanceHandler());
        operationType.put("s", new SupplyHandler());
        operationType.put("p", new PurchaseHandler());
        operationType.put("r", new ReturnHandler());

    }

    public void processFruitOperation(List<FruitDto> type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("sorry list can't be null or empty");
        }
        for (FruitDto fruit : type) {
            Operation operation = operationType.get(fruit.getType());
            operation.process(fruit);
        }
    }
}




