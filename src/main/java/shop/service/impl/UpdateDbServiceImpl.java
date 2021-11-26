package shop.service.impl;

import java.util.HashMap;
import java.util.List;
import shop.service.UpdateDbService;
import shop.service.action.ActionHandler;
import shop.service.action.ActionStrategyHandler;

public class UpdateDbServiceImpl implements UpdateDbService {
    private final int actionIndex;
    private final int fruitName;
    private final int countIndex;
    private final ActionStrategyHandler actionStrategy;

    public UpdateDbServiceImpl(HashMap<String, ActionHandler> actionMap,
                               int actionIndex, int fruitName, int countIndex) {
        actionStrategy = new ActionStrategyHandlerImpl(actionMap);
        this.actionIndex = actionIndex;
        this.fruitName = fruitName;
        this.countIndex = countIndex;
    }

    @Override
    public boolean updateStorage(List<String> listInput) {
        listInput.stream().map(list -> list.split(","))
                .forEach(line -> actionStrategy.get(line[actionIndex])
                        .update(line[fruitName],
                                Integer.parseInt(line[countIndex])));
        return true;
    }
}
