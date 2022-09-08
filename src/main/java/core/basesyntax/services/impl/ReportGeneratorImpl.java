package core.basesyntax.services.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.services.ReportGenerator;
import java.util.stream.Collectors;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String FIRST_LINE = "fruit,quantity";
    private FruitDao fruitDao;

    public ReportGeneratorImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public String generate() {
        return FIRST_LINE + fruitDao.getAll().entrySet().stream()
                .map(s -> System.lineSeparator() + s.getKey() + "," + s.getValue())
                .collect(Collectors.joining());
    }
}
