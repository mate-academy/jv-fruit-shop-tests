package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;
import java.util.Stack;

public class ReportGeneratorImpl implements ReportGenerator {
    private static final String HEAD_LINE = "fruit,quantity" + System.lineSeparator();

    @Override
    public String getReport() {
        if (Storage.getStorage().isEmpty()) {
            throw new RuntimeException("Can't get report from Storage");
        }
        Stack<String> stack = new Stack<>();
        for (Map.Entry<String, Integer> entry : Storage.getStorage().entrySet()) {
            if (entry.getValue() < 0) {
                throw new RuntimeException("Quantity can't be less than 0");
            }
            stack.push(entry.getKey() + "," + entry.getValue() + System.lineSeparator());
        }
        stack.push(HEAD_LINE);
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }
        String report = builder.toString();
        if (report.endsWith(System.lineSeparator())) {
            report = report.substring(0, report.length() - System.lineSeparator().length());
        }
        return report;
    }
}
