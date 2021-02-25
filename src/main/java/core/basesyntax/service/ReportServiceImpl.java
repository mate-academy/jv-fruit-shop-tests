package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReportServiceImpl implements ReportService {
    private FruitDao fruitDao;
    private ActivityStrategy activityStrategy;

    public ReportServiceImpl(FruitDao fruitDao, ActivityStrategy activityStrategy) {
        this.fruitDao = fruitDao;
        this.activityStrategy = activityStrategy;
    }

    @Override
    public void report(String fileInputName, String fileOutputName) {
        getDataFromCsvAndPutItsNamesToDB(fileInputName);

        fillDB_withValidData(fileInputName);

        writeReport(fileOutputName);
    }

    private void getDataFromCsvAndPutItsNamesToDB(String fileInputName) {
        FruitService fruitService = new FruitServiceImpl(fruitDao);
        List<Fruit> listOfFruit = fruitService.getAllFruitFromFile(fileInputName);

        // Fill DB(Storage) with fruit that contains names only
        Set<String> fruitsNamesOnly = new HashSet<>();
        for (Fruit fruit : listOfFruit) {
            fruitsNamesOnly.add(fruit.getName());
        }

        for (String nameOfFruit : fruitsNamesOnly) {
            Fruit fruit = new Fruit();
            fruit.setName(nameOfFruit);
            fruitDao.add(fruit);
        }
    }

    private void fillDB_withValidData(String fileInputName) {
        FruitService fruitService = new FruitServiceImpl(fruitDao);
        List<Fruit> listOfFruit = fruitService.getAllFruitFromFile(fileInputName);

        // Fill fruit in DB(Storage) with their amounts that depends on type of operations
        for (Fruit fruit : listOfFruit) {
            // Check validation for positive amount
            if (fruit.getAmount() < 0) {
                throw new RuntimeException("Amount of " + fruit.getName()
                        + " can`t be " + fruit.getAmount() + " There is wrong input amount.");
            }
            int amount = activityStrategy.get(fruit.getType()).getActivity(fruit.getAmount());
            int currentAmountInStorage = fruitDao.get(fruit.getName()).getAmount();
            int newAmount = currentAmountInStorage + amount;
            // Check validation for impossible operation
            if (newAmount < 0) {
                throw new RuntimeException("Buyers will not be able to buy "
                        + amount + " bananas, because they are only "
                        + currentAmountInStorage + " units in stock.");
            }
            Fruit newFruit = fruitService.createNewFruit(fruit.getName(), newAmount);
            fruitDao.update(newFruit);
        }
    }

    private void writeReport(String fileOutputName) {
        // Get data from DB(Storage) and write it into the created new csv file
        try (FileWriter fileWriter = new FileWriter(new File(fileOutputName), true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("fruit,quantity" + System.lineSeparator());
            for (Fruit fruit : fruitDao.getAll()) {
                bufferedWriter.write(fruit.getName() + ","
                        + fruit.getAmount() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data into file", e);
        }
    }
}
