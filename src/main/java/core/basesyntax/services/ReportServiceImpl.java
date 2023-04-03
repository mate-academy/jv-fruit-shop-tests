package core.basesyntax.services;

import core.basesyntax.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private static final String HEADER = "name,quantity";

    @Override
    public String createReport(List<Product> products) {
        if (products.size() == 0) {
            return null;
        }
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        products.stream()
                .map(Product::toString)
                .forEach(lines::add);
        return lines.stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
