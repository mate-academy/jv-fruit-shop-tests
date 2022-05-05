package core.basesyntax.service.implementations;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.inerfaces.ReportMaker;
import java.util.Map;

public class ReportMakerImpl implements ReportMaker {
    @Override
    public String formReport() {
        StringBuilder builder = new StringBuilder();
        FruitsDao fruitsDao = new FruitsDaoImpl();
        for (Map.Entry<Fruit, Integer> entry : fruitsDao.getAll().entrySet()) {
            builder.append(entry.getKey().getName()).append(",")
                    .append(String.valueOf(entry.getValue()))
                    .append(System.lineSeparator()).toString();
        }
        return builder.toString();
    }
}
