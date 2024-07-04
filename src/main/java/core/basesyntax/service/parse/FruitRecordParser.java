package core.basesyntax.service.parse;

import core.basesyntax.model.FruitRecord;
import java.util.List;

public interface FruitRecordParser {
    List<FruitRecord> parseFruitRecords(String[] lines);
}
