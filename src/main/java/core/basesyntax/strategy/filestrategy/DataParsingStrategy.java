package core.basesyntax.strategy.filestrategy;

import core.basesyntax.exception.InvalidFileTypeException;
import java.util.Map;
import java.util.Optional;

public class DataParsingStrategy {
    private final Map<String, DataParser> dataParsersMap;

    public DataParsingStrategy(Map<String, DataParser> dataParsersMap) {
        this.dataParsersMap = dataParsersMap;
    }

    public DataParser getDataParser(String type) {
        Optional<DataParser> dataParserOptional = Optional.ofNullable(dataParsersMap.get(type));
        return dataParserOptional.orElseThrow(
                () -> new InvalidFileTypeException("Invalid file type " + type));
    }
}
