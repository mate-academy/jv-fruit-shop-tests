package core.basesyntax.process;

import core.basesyntax.model.Storage;
import java.util.Map;

public class FruitReportMakerImpl implements FruitReportMaker {
    private static final String INITIAL_LINE = "fruits, quantity";
    private static final String DELIM = ",";

    @Override
    public String makeFruitReport() {
        if (Storage.fruits.isEmpty()) {
            throw new RuntimeException("Storage is empty!");
        }
        StringBuilder resultReport = new StringBuilder();
        resultReport.append(INITIAL_LINE);
        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            resultReport.append(System.lineSeparator())
                    .append(entry.getKey())
                    .append(DELIM)
                    .append(entry.getValue());
        }
        return resultReport.toString();
    }
}
