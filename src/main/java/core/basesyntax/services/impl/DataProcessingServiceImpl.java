package core.basesyntax.services.impl;

import core.basesyntax.exceptions.InvalidParametersException;
import core.basesyntax.services.DataProcessingService;
import core.basesyntax.services.ParametrsValidatorService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProcessingServiceImpl implements DataProcessingService {
    public static final int FRUIT_INDEX = 1;
    public static final int OPERATION_INDEX = 0;
    public static final int QUANTITY_INDEX = 2;
    private final OperationStrategy operationStrategy;
    private final ParametrsValidatorService parametrValidator;

    public DataProcessingServiceImpl(OperationStrategy operationStrategy,
                                     ParametrsValidatorService parametrValidator) {
        this.operationStrategy = operationStrategy;
        this.parametrValidator = parametrValidator;
    }

    @Override
    public Map<String, Integer> processData(List<List<String>> parcedData) {
        parametrValidator.isParametrsNotNull(parcedData);
        Map<String, Integer> resultMap = new HashMap<>();
        parcedData.forEach(transaction -> {
            parametrValidator.isValidParameters(transaction);
            resultMap.compute(transaction.get(FRUIT_INDEX), (fruit, balance)
                    -> calculate(balance, transaction));
                }
        );
        return resultMap;
    }

    private int calculate(Integer balance, List<String> transaction) {
        String operation = transaction.get(OPERATION_INDEX);
        int quantity = Integer.parseInt(transaction.get(QUANTITY_INDEX));
        int saldo = balance == null ? 0 : balance;
        saldo = operationStrategy.getOperation(operation)
                .changeSaldo(saldo)
                .applyAsInt(quantity);
        if (saldo < 0) {
            throw new InvalidParametersException("You can't do purchase: "
                    + transaction.get(FRUIT_INDEX) + " " + operation + " = "
                    + quantity
                    + ". Because your balance: " + balance);
        }
        return saldo;
    }
}
