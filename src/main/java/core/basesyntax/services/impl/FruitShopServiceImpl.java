package core.basesyntax.services.impl;

import core.basesyntax.FruitShopService;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FileReadException;
import core.basesyntax.services.ActivityValidator;
import core.basesyntax.services.FileReaderService;
import core.basesyntax.services.FruitReporter;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import java.nio.file.Paths;

public class FruitShopServiceImpl implements FruitShopService {
    private FileReaderService fileReader;
    private ActivityStrategy activityStrategy;
    private FruitReporter fruitReporter;

    public FruitShopServiceImpl() {
        activityStrategy = new ActivityStrategyImpl();
        fileReader = new ReaderServiceImpl();
        fruitReporter = new FruitReporterImpl();
    }

    @Override
    public String getReport(String filePath) {
        if (filePath == null) {
            throw new FileReadException("FilePath is null");
        }
        ActivityValidator activityValidator = new ActivityValidatorImpl();
        fileReader
                .readFile(Paths.get(filePath))
                .stream()
                .filter(s -> activityValidator.validate(s))
                .forEach(a -> activityStrategy
                        .getActivity(a.getActivityType())
                        .apply(a));
        return fruitReporter.report(Storage.fruitsStorage);
    }
}
