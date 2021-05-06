package core.basesyntax.strategy;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.operations.AddOperation;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.Operation;
import core.basesyntax.operations.PurchaseOperation;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategy {
    private static final Map<String, Operation> operationMap = new HashMap<>();

    public OperationStrategy() {
        operationMap.put("b", new BalanceOperation());
        operationMap.put("r", new AddOperation());
        operationMap.put("s", new AddOperation());
        operationMap.put("p", new PurchaseOperation());
    }

    public void doAction(ProductDto productDto) {
        Operation operation = operationMap.get(productDto.getOperationType());

        if (operation == null) {
            throw new RuntimeException("Invalid operation - '"
                    + productDto.getOperationType()
                    + "'");
        }

        operation.apply(productDto);
    }
}
