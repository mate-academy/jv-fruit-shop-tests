package core.basesyntax.service;

import core.basesyntax.strategy.DoActivities;
import java.util.List;
import java.util.Map;

public interface WriteToDB {
    boolean writeToDB(List<String> data, Map<String, DoActivities> strategy);
}
