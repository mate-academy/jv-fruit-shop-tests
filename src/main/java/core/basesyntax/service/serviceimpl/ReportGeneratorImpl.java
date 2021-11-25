package core.basesyntax.service.serviceimpl;

import core.basesyntax.dao.Dao;
import core.basesyntax.service.ReportGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGeneratorImpl implements ReportGenerator {
    private final Dao dao;

    public ReportGeneratorImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public List<String> generate() {
        List<String> report = new ArrayList<>();
        report.add("fruit,quantity");
        List<String> fruitNames = dao.getProductNames()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        for (String fruitName : fruitNames) {
            report.add(fruitName + "," + dao.get(fruitName));
        }
        return report;
    }
}
