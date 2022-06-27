package core.basesyntax.service.impl;

import core.basesyntax.service.CreateReport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateReportImpl implements CreateReport {
    @Override
    public List<String> createReport(Map<String, Integer> fruitsAtStorageMap) {
        List<String> report = new ArrayList<>();
        report.add("fruits,quantity");
        fruitsAtStorageMap.forEach((key, value)
                -> report.add(System.lineSeparator() + key + "," + value));
        return report;
    }
}
