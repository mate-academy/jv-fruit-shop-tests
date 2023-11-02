package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {

    public FileReaderServiceImpl() {
    }

    @Override
    public List<FruitTransaction> readFromFile(String filePath) throws RuntimeException {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8);
            List<FruitTransaction> fruitTransactionList = new ArrayList<>();
            for (String line : lines) {
                fruitTransactionList.add(new TransactionDataParseImpl().parseTransaction(line));
            }
            return fruitTransactionList;
        } catch (IOException e) {
            throw new UnsupportedOperationException(
                "Can't read data from your file " + filePath + " !", e);
        }
    }
}
