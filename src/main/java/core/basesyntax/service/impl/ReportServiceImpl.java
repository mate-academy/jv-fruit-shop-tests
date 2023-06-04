package core.basesyntax.service.impl;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.OutFileStructure;
import core.basesyntax.service.ReportService;

public class ReportServiceImpl implements ReportService {
    private static final String COMMA_SEPARATOR = ",";

    @Override
    public String getDataReport(OutFileStructure structure, FruitsStorage storage) {
        StringBuilder reportData = new StringBuilder();
        reportData.append(structure.getFruit()).append(COMMA_SEPARATOR)
                .append(structure.getQuantity());
        for (var node : FruitsStorage.fruitsStorage.entrySet()) {
            reportData.append(System.lineSeparator())
                    .append(node.getKey())
                    .append(COMMA_SEPARATOR).append(node.getValue());
        }
        return reportData.toString();
    }
}
