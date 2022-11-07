package myfirstproject.service;

import java.util.Map;
import myfirstproject.model.Fruit;

public interface PreparingData {
    String prepare(Map<Fruit, Integer> mapToWrite);
}
