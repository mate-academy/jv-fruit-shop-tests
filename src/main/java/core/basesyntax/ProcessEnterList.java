package core.basesyntax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessEnterList {
    private final OperationsStrategyImpl operationsStrategyImpl;
    private Map<String,Integer> map;

    public ProcessEnterList(OperationsStrategyImpl operationsStrategyImpl) {
        this.operationsStrategyImpl = operationsStrategyImpl;
        this.map = new HashMap<>();
    }

    public List<String> workWithEnterList(List<String> enterList) {
        for (String enter : enterList) {
            String[] split = enter.split(",");
            String operation = split[0];
            String fruitType = split[1];
            int amount = Integer.parseInt(split[2]);

            int updatedAmount = operationsStrategyImpl.operation(operation, fruitType, amount);
            map.put(fruitType, updatedAmount);
        }

        List<String> processedData = new ArrayList<>();
        for (Map.Entry<String, Integer> mapDetail : map.entrySet()) {
            processedData.add(mapDetail.getKey() + "," + mapDetail.getValue());
        }
        return processedData;
    }
}
