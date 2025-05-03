package core.basesyntax.service.impl;

import core.basesyntax.service.ProcessingService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ProcessingServiceImpl implements ProcessingService {
    private static final String EMPTY_STATISTICS_MESSAGE = "The day statistics is empty!";
    private static final String HEADING = "type,fruit,quantity";
    private static final int HEADING_INDEX = 0;
    private static final String SEPARATOR = ",";
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String HEADING_IS_ALREADY_REMOVED_MESSAGE = "The heading "
            + "has been already removed";

    @Override
    public void removeHeading(List<String> list) {
        if (list.get(HEADING_INDEX).equals(HEADING)) {
            list.remove(HEADING_INDEX);
        } else {
            throw new RuntimeException(HEADING_IS_ALREADY_REMOVED_MESSAGE);
        }
    }

    @Override
    public void processData(List<String> dayStatistics, OperationStrategy strategy) {
        if (dayStatistics.isEmpty()) {
            throw new RuntimeException(EMPTY_STATISTICS_MESSAGE);
        }
        for (String datum : dayStatistics) {
            String fruitType = datum.split(SEPARATOR)[FRUIT_TYPE_INDEX];
            Integer quantity = Integer.parseInt(datum.split(SEPARATOR)[QUANTITY_INDEX]);
            strategy.getHandler(datum).operateStorage(fruitType,quantity);
        }
    }
}
