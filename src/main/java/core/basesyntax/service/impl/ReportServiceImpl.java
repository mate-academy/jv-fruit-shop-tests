package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;

public class ReportServiceImpl implements ReportService {
    @Override
    public String getReport() {
        StringBuilder builder = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator());

        for (Fruit thisFruit : Storage.storage.keySet()) {
            builder.append(thisFruit.getName())
                    .append(",")
                    .append(Storage.storage.get(thisFruit))
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }
}
