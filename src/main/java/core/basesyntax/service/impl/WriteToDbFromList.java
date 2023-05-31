package core.basesyntax.service.impl;

import core.basesyntax.service.WriteToDB;
import core.basesyntax.strategy.DoActivities;
import java.util.List;
import java.util.Map;

public class WriteToDbFromList implements WriteToDB {
    private static final int INDEX_OF_HEADER = 0;
    private static final int INDEX_OF_ACTIVITY = 0;
    private static final int INDEX_OF_FRUIT = 1;
    private static final int INDEX_OF_NUMBER = 2;
    private String spliterator;

    public WriteToDbFromList(String spliterator) {
        if (spliterator == null) {
            throw new RuntimeException("Spliterator must be matched");
        }
        this.spliterator = spliterator;
    }

    @Override
    public boolean writeToDB(List<String> data, Map<String, DoActivities> strategy) {
        if (data == null) {
            throw new RuntimeException("Data must be matched");
        }
        if (strategy == null) {
            throw new RuntimeException("Strategy must be matched");
        }
        if (data.isEmpty() || strategy.isEmpty()) {
            return false;
        }
        data.remove(INDEX_OF_HEADER);
        data.stream()
                .map(s -> s.split(spliterator))
                .forEach(s -> strategy.get(s[INDEX_OF_ACTIVITY]).doActivity(s[INDEX_OF_FRUIT],
                        Integer.parseInt(s[INDEX_OF_NUMBER])));
        return true;
    }
}
