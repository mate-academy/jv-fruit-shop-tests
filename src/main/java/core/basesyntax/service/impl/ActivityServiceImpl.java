package core.basesyntax.service.impl;

import core.basesyntax.model.Activity;
import core.basesyntax.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private static final String SEPARATOR = ",";
    private static final int CODE_INDEX = 0;
    private static final int ITEM_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private final Activity.Builder activityBuilder = new Activity.Builder();

    @Override
    public Activity parse(String activityInfo) {
        String[] activityUnits = activityInfo.split(SEPARATOR);
        activityBuilder.setOperation(activityUnits[CODE_INDEX]);
        activityBuilder.setItem(activityUnits[ITEM_INDEX]);
        activityBuilder.setQuantity(Integer.parseInt(activityUnits[QUANTITY_INDEX]));
        return activityBuilder.build();
    }
}
