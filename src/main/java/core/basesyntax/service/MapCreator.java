package core.basesyntax.service;

import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public interface MapCreator {
    Map<String, OperationHandler> createMap();
}
