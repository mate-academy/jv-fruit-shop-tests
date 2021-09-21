package core.basesyntax.services;

import core.basesyntax.storage.Stock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportingImpl implements Reporting {
    private static final String START_MESSAGE = "fruit,quantity";

    @Override
    public List<String> createReport() {
        List<String> listReport = new ArrayList<>();
        listReport.add(START_MESSAGE);
        for (Map.Entry<String, Integer> entry : Stock.stockStorage.entrySet()) {
            listReport.add(entry.getKey() + "," + entry.getValue());
        }
        return listReport;
    }
}
