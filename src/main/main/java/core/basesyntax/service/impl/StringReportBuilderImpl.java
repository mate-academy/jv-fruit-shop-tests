package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.StringReportBuilder;
import java.util.Map;
import java.util.Set;

public class StringReportBuilderImpl implements StringReportBuilder {
    @Override
    public String buildReport() {
        Set<Map.Entry<Fruit, Integer>> fruitSet = new FruitDaoImpl().getDataFromStorage();
        StringBuilder reportBuilder = new StringBuilder()
                .append("fruit,quantity");
        if (fruitSet.size() == 0) {
            throw new RuntimeException("There still weren't any operations in shop");
        }
        for (Map.Entry<Fruit, Integer> fruitEntry : fruitSet) {
            reportBuilder
                    .append(System.lineSeparator())
                    .append(fruitEntry.getKey().getName())
                    .append(",")
                    .append(fruitEntry.getValue());
        }
        return reportBuilder.toString();
    }
}
