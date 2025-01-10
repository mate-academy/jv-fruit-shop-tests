package core.basesyntax.service.impl;

import core.basesyntax.model.ResultData;
import core.basesyntax.service.ReportGenerator;
import java.util.List;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();

    @Override
    public String getReport(List<ResultData> resultDataList) {
        StringBuilder stringBuilder = new StringBuilder(HEADER);
        for (ResultData resultData : resultDataList) {
            stringBuilder.append("%s,%d%s".formatted(
                    resultData.getFruitName(),
                    resultData.getQuantity(),
                    System.lineSeparator()));
        }
        return stringBuilder.toString();
    }
}
