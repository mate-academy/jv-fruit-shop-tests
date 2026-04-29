package core.basesyntax.service.impl;

import core.basesyntax.db.StorageDao;
import core.basesyntax.service.ReportGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGeneratorImpl implements ReportGenerator {
    private StorageDao dao;

    public ReportGeneratorImpl(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public String generateReport() {
        Map<String, Integer> fruits = dao.getData()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity")
                .append(System.lineSeparator());

        for (String key : fruits.keySet()) {
            builder.append(key)
                    .append(",")
                    .append(fruits.get(key))
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
