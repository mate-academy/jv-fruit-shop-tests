package core.basesyntax.service.impl;

import core.basesyntax.dto.ActivityDto;
import core.basesyntax.enums.ActivityType;
import core.basesyntax.exception.CsvParseException;
import core.basesyntax.service.ActivityParser;
import java.util.Collections;
import java.util.List;

public class ActivityParserImpl implements ActivityParser {
    private static final int HEADER_INDEX = 1;
    private static final int COLUMNS_SIZE = 3;
    private static final int CODE_INDEX = 0;
    private static final int TITLE_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String CSV_SEPARATOR = ",";

    @Override
    public List<ActivityDto> parse(List<String> activityRows) {
        if (activityRows == null) {
            return Collections.emptyList();
        }
        return activityRows.stream()
                    .skip(HEADER_INDEX)
                    .map(this::mapToDto)
                    .toList();
    }

    private ActivityDto mapToDto(String line) {
        String[] columns = line.split(CSV_SEPARATOR);
        if (columns.length < COLUMNS_SIZE) {
            throw new CsvParseException("Incorrect number of columns in CSV file");
        }
        int count;
        try {
            count = Integer.parseInt(columns[QUANTITY_INDEX]);
        } catch (NumberFormatException e) {
            throw new CsvParseException(
                    "Not valid CSV file: third column must be a number, but was <"
                            + columns[QUANTITY_INDEX] + ">"
            );
        }
        ActivityType activityType = ActivityType.findByCode(columns[CODE_INDEX]);
        if (activityType == null) {
            throw new CsvParseException(
                    "Not valid CSV file: first column must be an activity, but was <"
                            + columns[CODE_INDEX] + ">"
            );
        }
        return new ActivityDto(
                activityType,
                columns[TITLE_INDEX],
                count);
    }
}
