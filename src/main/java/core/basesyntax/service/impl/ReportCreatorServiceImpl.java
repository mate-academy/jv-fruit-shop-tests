package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreatorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private static final String FILE_HEADER = "fruit,quantity";

    @Override
    public List<String> createReport(Map<String, Integer> fruitsMap) {
        if (fruitsMap == null) {
            throw new RuntimeException("Report creation failed: input data is null!");
        }
        List<String> lines = new ArrayList<>();
        lines.add(FILE_HEADER);
        for (Map.Entry<String, Integer> entry: fruitsMap.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new RuntimeException("Wrong data in Storage: fruit is " + entry.getKey()
                + " quantity is " + entry.getValue() + "!");
            }
            lines.add(System.lineSeparator());
            lines.add(entry.getKey() + "," + entry.getValue());
        }
        return lines;
    }
}
