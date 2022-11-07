package core.basesyntax.service.impl;

import core.basesyntax.datavalidator.DataValidationException;
import core.basesyntax.datavalidator.OutputDataValidator;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import java.util.ArrayList;
import java.util.List;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private static final String COMMA_SEPARATOR = ",";
    private static final String REPORT_HEAD = "fruit,quantity";

    @Override
    public List<String> createReport(List<Fruit> fruitInShopList) {
        List<String> reportList = new ArrayList<>();
        try {
            OutputDataValidator outputDataValidator = new OutputDataValidator();
            outputDataValidator.validateOutput(fruitInShopList);
            reportList.add(REPORT_HEAD);
            for (Fruit fruit : fruitInShopList) {
                StringBuilder stringBuilder = new StringBuilder();
                reportList.add(stringBuilder.append(fruit.getName())
                        .append(COMMA_SEPARATOR)
                        .append(fruit.getAmountInShop())
                        .toString());
            }
        } catch (DataValidationException e) {
            throw new RuntimeException("Invalid data. Can't create a report.", e);
        }

        return reportList;
    }
}
