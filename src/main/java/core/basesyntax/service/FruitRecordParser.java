package core.basesyntax.service;

import core.basesyntax.model.FruitRecord;
import java.util.List;

public interface FruitRecordParser {
    List<FruitRecord> parse(List<String[]> splitedInformationList);
}
