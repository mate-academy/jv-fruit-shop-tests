package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.errors.ErrorMessages;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import java.util.stream.Collectors;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String REPORT_HEAD = "fruit,quantity";
    private static final String REPORT_DATA_DELIMITER = ",";
    private final FruitDao fruitDao;

    public ReportGeneratorImpl(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public String getReport() {
        return REPORT_HEAD + System.lineSeparator() + getReportData();
    }

    private String getReportData() {
        return fruitDao.getAll().stream()
                .peek(this::validateFruit)
                .map(fruit -> fruit.getName() + REPORT_DATA_DELIMITER + fruit.getQuantity())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private void validateFruit(Fruit fruit) {
        if (fruit == null) {
            throw new IllegalArgumentException(ErrorMessages.FRUIT_CANNOT_BE_NULL_OR_EMPTY);
        }
    }
}
