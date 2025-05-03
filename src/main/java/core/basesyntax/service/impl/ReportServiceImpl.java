package core.basesyntax.service.impl;

import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {

    @Override
    public String getReport(Map<String, Integer> storage) {
        if (storage == null) {
            throw new RuntimeException("Check your storage. It's something wrong with him");
        }
        StringBuilder builder = new StringBuilder("fruit,quantity");
        if (storage.isEmpty()) {
            builder.append(System.lineSeparator()).append("Storage is empty");
            return builder.toString();
        }
        String infoForRepo = getInfoForReport(storage);
        builder.append(System.lineSeparator()).append(infoForRepo);
        return builder.toString();
    }

    private String getInfoForReport(Map<String, Integer> storage) {
        return storage.entrySet().stream()
                .map(this::parseToString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String parseToString(Map.Entry<String, Integer> entry) {
        StringBuilder builder = new StringBuilder();
        builder.append(entry.getKey()).append(",").append(entry.getValue());
        return builder.toString();
    }
}
