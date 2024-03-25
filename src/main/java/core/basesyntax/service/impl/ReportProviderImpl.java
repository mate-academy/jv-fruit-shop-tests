package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.service.ReportProvider;

public class ReportProviderImpl implements ReportProvider {
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String ELEMENT_SEPARATOR = ",";

    @Override
    public String provide() {
        StringBuilder report = new StringBuilder(REPORT_HEADER);

        for (var element : Storage.fruitStorage.entrySet()) {
            String key = element.getKey();
            if (key == null) {
                throw new NullDataException("Report data can't be null.");
            }
            report.append(System.lineSeparator()).append(key)
                    .append(ELEMENT_SEPARATOR).append(element.getValue());
        }
        return report.toString();
    }
}
