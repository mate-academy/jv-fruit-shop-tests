package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.List;

public interface ReportGeneratingService {

    List<String> createReport(List<Fruit> db);
}
