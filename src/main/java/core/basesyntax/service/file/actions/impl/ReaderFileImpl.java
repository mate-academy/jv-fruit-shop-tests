package core.basesyntax.service.file.actions.impl;

import core.basesyntax.service.file.actions.ReaderFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderFileImpl implements ReaderFile {
    @Override
    public List<String> readFileFruitShop(String pathFileStore) {
        List<String> fruitStore = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pathFileStore))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                fruitStore.add(line);
            }
        } catch (IOException ioException) {
            throw new RuntimeException("Unable to read information from this file "
                    + pathFileStore);
        }

        return fruitStore;
    }
}
