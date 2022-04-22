package core.basesyntax.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyReport {

    public List<String> listOperation(Map<String, Integer> fruitMap) {
        List<String> result;
        try {
            result = fruitMap.entrySet()
                     .stream()
                     .map(entry -> entry.getKey() + "," + entry.getValue())
                     .collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new RuntimeException(" Storage is empty  ", e);
        }
        return result;
    }
}
