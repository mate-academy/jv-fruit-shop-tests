package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private FruitDao fruitDao;

    public ReportServiceImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public List<String> generatedReport() {
        List<Fruit> fruitDaoAll = fruitDao.getAll();
        if (fruitDaoAll.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> lines = new ArrayList<>();
        lines.add("fruit,quantity");
        for (Fruit fruit : fruitDaoAll) {
            lines.add(fruit.getName().concat(",").concat(String.valueOf(fruit.getQuantity())));
        }
        return lines;
    }
}
