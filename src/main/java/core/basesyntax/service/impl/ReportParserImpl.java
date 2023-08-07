package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.service.interfaces.TransactionParser;
import java.util.Map;

public class ReportParserImpl implements TransactionParser<String, Map<String, Integer>> {
    private static final String REPORT_HEADING = "fruit,quantity" + System.lineSeparator();
    private static final String COMMA = ",";

    @Override
    public String parse(Map<String, Integer> data) {
        if (data == null) {
            throw new InvalidDataException("Data for parsing report must not be null!");
        }
        StringBuilder report = new StringBuilder().append(REPORT_HEADING);
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            report.append(entry.getKey())
                    .append(COMMA)
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        return report.toString().trim();
    }
}
