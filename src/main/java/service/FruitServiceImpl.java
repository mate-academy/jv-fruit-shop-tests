package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitRecord;

public class FruitServiceImpl implements FruitService {
    @Override
    public Map<String, Integer> getReport(List<FruitRecord> fruitRecords,
                                          ActivityStrategy activityStrategy) {
        if (fruitRecords == null) {
            throw new RuntimeException("FruitRecordList is null");
        }
        if (activityStrategy == null) {
            throw new RuntimeException("ActivityStrategy is null");
        }
        Map<String, Integer> report = new HashMap<>();
        for (FruitRecord fruitRecord : fruitRecords) {
            activityStrategy.select(fruitRecord.getActivityType()).use(fruitRecord, report);
        }
        return report;
    }
}
