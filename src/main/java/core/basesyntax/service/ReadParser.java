package core.basesyntax.service;

import core.basesyntax.model.fruit.Record;
import java.util.List;

public interface ReadParser {
    List<Record> parseFileData(List<String> data);
}
