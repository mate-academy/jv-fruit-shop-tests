package core.basesyntax.service.impl;

import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.service.ReaderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<String> readFileToList(String fromFileName) {
        List<String> inputList = new ArrayList<>();
        try (BufferedReader fromFile = new BufferedReader(new FileReader(fromFileName))) {
            while (fromFile.ready()) {
                inputList.add(fromFile.readLine());
            }
        } catch (IOException e) {
            throw new FruitStoreException("Can't read from file " + fromFileName);
        }
        if (inputList.isEmpty()) {
            throw new FruitStoreException("File " + fromFileName + " is empty. Parser worked only "
                    + "with non empty CSV files");
        }
        return inputList;
    }
}
