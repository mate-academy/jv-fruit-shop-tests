package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.service.GeneratorDataForWriting;
import java.util.Map;

public class GeneratorDataForWritingImpl implements GeneratorDataForWriting {
    private static final String TEMPLATE = System.lineSeparator() + "%s,%d";
    private static final String FIRST_LINE = "fruit,quantity";
    private final FruitsDao fruitsDao;

    public GeneratorDataForWritingImpl(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public String generateData() {
        StringBuilder stringBuilder = new StringBuilder(FIRST_LINE);
        fruitsDao.getAll().stream()
                .forEach(e -> stringBuilder.append(formatString(e)));
        return stringBuilder.toString();
    }

    private String formatString(Map.Entry<String, Integer> record) {
        return String.format(TEMPLATE, record.getKey(), record.getValue());
    }
}
