package core.basesyntax.service.implementation;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportService;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private final FruitService fruitService;

    public ReportServiceImpl(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @Override
    public String getReport() {
        List<Fruit> fruits = fruitService.getAll();
        StringBuilder builder = new StringBuilder("fruit,quantity");
        for (Fruit fruit : fruits) {
            builder.append(System.lineSeparator())
                    .append(fruit.getName())
                    .append(",")
                    .append(fruit.getAmount());
        }
        return builder.toString();
    }
}
