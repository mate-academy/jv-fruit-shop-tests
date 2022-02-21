package core.basesyntax.service.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParseDataImpl implements ParseData {
    private static final String SEPARATOR = ",";
    private static final int DATA_START_INDEX = 20;

    @Override
    public List<String[]> parse(String data) {
        data = data.substring(DATA_START_INDEX);
        List<String> dataToList = Arrays.asList(data.split("\n"));
        return dataToList.stream()
                .map(s -> s.split(SEPARATOR))
                .collect(Collectors.toList());
    }
}
