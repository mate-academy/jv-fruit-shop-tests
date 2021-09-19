package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportMakerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportMakerServiceImpl implements ReportMakerService {
    private static final String COMMA = ",";
    private static final String LINE_SEPARATOR = "\n";
    private static final String FIRST_ROW = "fruit,quantity";

    @Override
    public String getReport(Map<Fruit, Integer> map) {
        StringBuilder builder = new StringBuilder(FIRST_ROW);
        for (Map.Entry<Fruit, Integer> entry : getListFromMap(map)) {
            builder.append(LINE_SEPARATOR)
                    .append(getSingleFruitReport(entry));
        }
        return builder.toString();
    }

    private Iterable<? extends Map.Entry<Fruit, Integer>> getListFromMap(Map<Fruit, Integer> map) {
        List<Map.Entry<Fruit, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort((o1, o2) -> o2.getValue() - o1.getValue());
        return entries;
    }

    private String getSingleFruitReport(Map.Entry<Fruit, Integer> entry) {
        return entry.getKey().getName()
                + COMMA
                + entry.getValue();
    }
}
