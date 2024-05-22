package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.service.ReportCreatorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BalanceReportCreatorService implements ReportCreatorService {
    private static final String REPORT_ANNOTATION = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private FruitShopDao dao = new FruitShopDaoImpl();
    private Map<String, Integer> statistic;

    @Override
    public List<String> createReport() {
        List<String> finalFile = new ArrayList<>();
        finalFile.add(REPORT_ANNOTATION);
        statistic = dao.getBalance();
        List<String> statisticToString = statistic.entrySet().stream()
                .peek(kv -> {
                    if (kv.getValue() < 0) {
                        throw new IllegalStateException("Balance " + kv.getKey()
                                + " is negative: " + kv.getValue());
                    }
                })
                .map(kv -> kv.getKey() + SEPARATOR + kv.getValue())
                .collect(Collectors.toList());
        finalFile.addAll(statisticToString);
        return finalFile;
    }
}
