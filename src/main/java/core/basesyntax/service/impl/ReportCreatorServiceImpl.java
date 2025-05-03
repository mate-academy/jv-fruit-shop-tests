package core.basesyntax.service.impl;

import core.basesyntax.service.ReportCreatorService;
import java.util.Map;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private static final String FILE_HEADER = "fruit,quantity";
    private static final String COMMA = ",";

    @Override
    public String createReport(Map<String, Integer> products) {
        if (products == null || products.isEmpty()) {
            throw new RuntimeException("Can't create report on null or empty data");
        }
        StringBuilder productsInfo = new StringBuilder();
        productsInfo.append(FILE_HEADER).append(System.lineSeparator());
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productInfo = entry.getKey() + COMMA + entry.getValue();
            productsInfo.append(productInfo).append(System.lineSeparator());
        }
        return productsInfo.toString();
    }
}
