package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreatorService;
import java.util.ArrayList;
import java.util.List;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private static final String COMMA_SEPARATOR = ",";
    private static final String REPORT_HEAD = "fruit,quantity";

    @Override
    public List<String> createReport(List<Fruit> fruitInShopList) {
        if (fruitInShopList == null || fruitInShopList.size() == 0) {
            return List.of("Given list of fruits in the shop is empty. Check input data");
        }
        List<String> reportList = new ArrayList<>();
        reportList.add(REPORT_HEAD);
        for (Fruit fruit : fruitInShopList) {
            StringBuilder stringBuilder = new StringBuilder();
            reportList.add(stringBuilder.append(fruit.getName())
                    .append(COMMA_SEPARATOR)
                    .append(fruit.getAmountInShop())
                    .toString());
        }
        return reportList;
    }
}
