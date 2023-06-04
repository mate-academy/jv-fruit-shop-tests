package core.basesyntax.service;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.OutFileStructure;
//
public interface ReportService {
    String getDataReport(OutFileStructure structure, FruitsStorage storage);
}
