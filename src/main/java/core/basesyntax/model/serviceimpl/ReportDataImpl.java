package core.basesyntax.model.serviceimpl;

import core.basesyntax.model.service.ReportData;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportDataImpl implements ReportData {
    private static final String TITLE = "fruit,quantity";

    @Override
    public String createDataReport(Set<Map.Entry<String, Integer>> entries) {
        String collect = entries.stream().map(e -> e.getKey() + "," + e.getValue())
                        .sorted().collect(Collectors.joining(System.lineSeparator()));
        StringBuilder builder = new StringBuilder(TITLE).append("\n").append(collect);
        return builder.toString();
    }
}
