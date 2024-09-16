package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.services.actions.ActionHandler;
import java.util.List;
import java.util.Map;

public class ReportServiceImpl implements ReportService {
    private final Map<String, ActionHandler> actionType;
    private final RecordValidation recordValidation = new RecordValidation();

    public ReportServiceImpl(Map<String, ActionHandler> actionType) {
        this.actionType = actionType;
    }

    @Override
    public String createReport(List<String> records) {
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity\n");

        for (String record: records) {
            String[] recordParts = record.split(",");

            if (recordValidation.test(recordParts)) {
                String fruitName = recordParts[1];
                String type = recordParts[0];
                int quantity = Integer.parseInt(recordParts[2]);
                ActionHandler actionHandler = actionType.get(type);
                actionHandler.getResultOfAction(fruitName, quantity);
            }
        }

        for (Map.Entry<String,Integer> pair: Storage.fruits.entrySet()) {
            report.append(pair.getKey())
                    .append(",")
                    .append(pair.getValue())
                    .append(System.lineSeparator());
        }

        return report.toString();
    }
}
