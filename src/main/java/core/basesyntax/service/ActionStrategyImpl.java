package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.service.action.ActionHandler;
import java.util.Map;

public class ActionStrategyImpl implements ActionStrategy {
    private final Map<Operation, ActionHandler> actionHandlerMap;

    public ActionStrategyImpl(Map<Operation, ActionHandler> actionHandlerMap) {
        this.actionHandlerMap = actionHandlerMap;
    }

    @Override
    public ActionHandler get(Operation operation) {
        if (actionHandlerMap == null) {
            throw new RuntimeException("Action map is null");
        }
        return actionHandlerMap.get(operation);
    }
}
