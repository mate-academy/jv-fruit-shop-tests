package core.basesyntax.dao;

import static core.basesyntax.db.Storage.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkWithFile implements IStorageDao {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();

    @Override
    public List<String> getData(String path_input) {
        List<String> operations = new ArrayList<>();
        String tmp;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path_input))) {
            while ((tmp = bufferedReader.readLine()) != null) {
                operations.add(tmp);
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found " + e);
        }
        return operations;
    }

    @Override
    public void putData(String path_output) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path_output))) {
            bufferedWriter.write(HEADER);
            for (Map.Entry<String, Integer> entry: storage.entrySet()) {
                bufferedWriter.write(entry.getKey() + ","
                                       + entry.getValue() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("File not found " + e);
        }
    }
}
