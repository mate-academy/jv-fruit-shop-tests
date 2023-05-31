package core.basesyntax.servises;

import java.util.HashMap;

public interface ReportCreator {
    String createReport(HashMap<String, Integer> storage);
}
