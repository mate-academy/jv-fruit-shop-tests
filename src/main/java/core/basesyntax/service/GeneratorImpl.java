package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;

public class GeneratorImpl implements Generator {
    private static final String SEPARATOR = ",";
    private static final String TITLE = "fruit,quantity";
    private static final String MULTIPLATFORM_LINE_SEPARATOR = "\n";
    private FruitDao fruitDao;

    public GeneratorImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append(TITLE).append(MULTIPLATFORM_LINE_SEPARATOR);
        fruitDao.getAll().forEach((key, value) -> report.append(key).append(SEPARATOR)
                .append(value).append(MULTIPLATFORM_LINE_SEPARATOR));
        report.setLength(report.length() - 1);
        return report.toString();
    }
}
