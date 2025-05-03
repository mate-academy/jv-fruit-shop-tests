package core.basesyntax.services;

import core.basesyntax.model.Product;
import java.util.List;

public interface ReportService {
    String createReport(List<Product> products);
}
