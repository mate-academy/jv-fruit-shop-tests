package core.basesyntax.service.impl;

import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReportCreatorService;
import java.util.stream.Collectors;

public class ReportCreatorServiceImpl implements ReportCreatorService {
    private FruitService fruitService;

    public ReportCreatorServiceImpl(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    public String createReport() {
        return "fruit,quantity\n" + fruitService.getAll().entrySet()
                .stream()
                .map(entry -> entry.getKey() + "," + entry.getValue())
                .collect(Collectors.joining("\n"));
    }
}
