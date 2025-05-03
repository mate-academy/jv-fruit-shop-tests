package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.storage.Storage;
import java.util.Map;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private static final String FIRST_RESULT_LINE = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String createReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FIRST_RESULT_LINE).append(System.lineSeparator());
        for (Map.Entry<String, Integer> fruits : Storage.getStorage().entrySet()) {
            stringBuilder.append(fruits.getKey()).append(COMMA).append(fruits.getValue())
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
