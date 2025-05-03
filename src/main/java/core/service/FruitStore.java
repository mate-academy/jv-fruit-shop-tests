package core.service;

import core.exception.OperationHandlerException;
import core.operationstrategy.OperationStrategyImpl;
import core.transactions.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FruitStore {
    private OperationStrategyImpl operationHandlers;
    private Map<String, Integer> fruitTransactions;

    public FruitStore(OperationStrategyImpl operationHandlers) {
        this.operationHandlers = operationHandlers;
        this.fruitTransactions = new HashMap<>();
    }

    public List<OperationData> processOperations(List<OperationData> dataList) {
        if (dataList == null) {
            throw new RuntimeException("I can't work with null data");
        }

        for (OperationData data : dataList) {
            String product = data.getProduct();
            int number = data.getQuantity();
            int currentQuantity = fruitTransactions.getOrDefault(product, 0);

            OperationType operationType = data.getOperationType();
            OperationHandler handler = operationHandlers.get(operationType);

            if (handler != null) {
                int newQuantity = handler.getTransaction(currentQuantity, number);
                fruitTransactions.put(product, newQuantity);
            } else {
                throw new OperationHandlerException(
                        "Handler not found for operation: " + operationType);
            }
        }

        List<OperationData> result = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : fruitTransactions.entrySet()) {
            String product = entry.getKey();
            int number = entry.getValue();
            result.add(new OperationData(OperationType.B, product, number));
        }
        return result;
    }

    public String convertListToString(List<OperationData> dataList) {
        StringBuilder sb = new StringBuilder();

        for (OperationData data : dataList) {
            sb.append(data.getProduct())
                    .append(",")
                    .append(data.getQuantity())
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }

    public void resetTransactions() {
        fruitTransactions.clear();
    }
}
