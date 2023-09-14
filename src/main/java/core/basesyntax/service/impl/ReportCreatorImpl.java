package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitStorage;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportCreator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportCreatorImpl implements ReportCreator {
    @Override
    public List<String> create() {
        List<String> report = new ArrayList<>();
        Field[] fields = FruitTransaction.class.getDeclaredFields();
        String column0Name = fields[1].getName();
        String column1Name = fields[2].getName();
        report.add(column0Name + "," + column1Name);
        for (Map.Entry<String, Integer> entry : fruitStorage.entrySet()) {
            report.add(entry.getKey() + "," + entry.getValue());
        }
        return report;
    }
}
