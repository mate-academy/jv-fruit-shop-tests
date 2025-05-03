package core.basesyntax.service;

import java.util.Map;

public interface ReportMessageCreator {
    String createMessage(Map<String, Integer> toWrite);
}
