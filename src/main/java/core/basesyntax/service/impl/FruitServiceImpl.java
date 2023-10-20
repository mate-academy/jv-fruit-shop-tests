package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.WriterService;
import core.basesyntax.strategy.TypeActivityStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FruitServiceImpl implements FruitService {
    private static final String COMA = ",";
    private static final String TO_FILE = "newFile.CSV";

    private ReaderService readerService;

    private WriterService writerService;

    private TypeActivityStrategy typeActivityStrategy;

    public FruitServiceImpl(ReaderService readerService, WriterService writerService,
                            TypeActivityStrategy typeActivityStrategy) {
        this.readerService = readerService;
        this.writerService = writerService;
        this.typeActivityStrategy = typeActivityStrategy;
    }

    @Override
    public void writeReport(String file) {
        List<FruitTransaction> fruitTransactions = readerService.readFromFile(file);
        fruitTransactions.stream()
                .forEach(fruit -> typeActivityStrategy
                        .get(fruit.getOperation()).setAmountOfFruit(fruit));
        writerService.writeToFile(TO_FILE);
    }

}
