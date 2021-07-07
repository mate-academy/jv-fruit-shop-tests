package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.Map;

public class FruitReportServiceImpl implements FruitReportService {
    private static final String COMMA = ",";
    private static final String TITLE_RESULT = "fruit,quantity";

    @Override
    public String returnReport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TITLE_RESULT);
        for (Map.Entry<Fruit,Integer> entry : Storage.fruitStorage.entrySet()) {
            stringBuilder.append(System.lineSeparator()).append(entry.getKey().getFruitName())
                    .append(COMMA).append(entry.getValue());
        }
        return stringBuilder.toString();
    }
}
