package core.basesyntax.service.impl;

import core.basesyntax.model.DailyActivity;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl implements Parser {
    private static final int EMPTY_LIST_SIZE = 1;
    private static final int NORMAL_STRING_LENGTH = 3;

    @Override
    public List<DailyActivity> parse(List<String> list) {
        List<DailyActivity> dailyActivities = new ArrayList<>();
        if (list == null || list.size() == EMPTY_LIST_SIZE) {
            throw new RuntimeException("There is not any data for parsing");
        }
        list.remove(0);
        for (String transaction : list) {
            new ValidatorImpl().validate(transaction.trim());
            String[] splitData = transaction.split(",");
            if (splitData.length != NORMAL_STRING_LENGTH) {
                throw new RuntimeException("Your data should have only "
                        + NORMAL_STRING_LENGTH + " parameters"
                        + ", but current parameters amount = " + splitData.length);
            }
            dailyActivities.add(new DailyActivity(splitData[0], splitData[1],
                    Integer.parseInt(splitData[2])));
        }
        return dailyActivities;
    }

}




