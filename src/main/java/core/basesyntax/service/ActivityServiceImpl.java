package core.basesyntax.service;

import core.basesyntax.model.ActivityType;
import core.basesyntax.service.activityhandler.ActivityHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private static final int HEAD_STRING = 0;
    private static final int ACTIVITY_TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String SPLITTER = ",";

    @Override
    public Map<String, Integer> processActivities(ActivityTypeStrategy activityTypeStrategy,
                                                     List<String> activities) {
        Map<String, Integer> storageReport = new HashMap<>();
        activities.remove(HEAD_STRING);
        for (String activity : activities) {
            String[] activitySplit = activity.split(SPLITTER);
            ActivityHandler activityHandler = activityTypeStrategy
                    .get(ActivityType
                            .valueOf(activitySplit[ACTIVITY_TYPE_INDEX]));
            try {
                activityHandler.processActivity(storageReport, activitySplit[FRUIT_INDEX],
                        Integer.parseInt(activitySplit[AMOUNT_INDEX]));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return storageReport;
    }
}
