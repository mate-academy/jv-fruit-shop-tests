package core.basesyntax.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.KeyAlreadyExistsException;

public class DaoServiceHashMap implements DaoService<String,Integer> {
    private static final String CANNOT_BE_NEGATIVE = "Balance cannot be negative";
    private static final String CANNOT_BE_NULL = "Balance value cannot be null";
    private static final String KEY_ALREADY_EXISTS = "Storage already contains value for ";
    private Map<String, Integer> memory;

    public DaoServiceHashMap() {
        this.memory = new HashMap<>();
    }

    @Override
    public void create(String key, Integer value) {
        if (memory.containsKey(key)) {
            throw new KeyAlreadyExistsException(KEY_ALREADY_EXISTS + key);
        }
        vlidateValue(value);
        memory.put(key, value);
    }

    @Override
    public void update(String key, Integer value) {
        vlidateValue(value);
        memory.put(key, value);
    }

    @Override
    public Integer getByKey(String key) {
        return memory.get(key);
    }

    @Override
    public Collection<Map.Entry<String,Integer>> getAll() {
        return memory.entrySet();
    }

    @Override
    public void clear() {
        memory.clear();
    }

    private void vlidateValue(Integer value) {
        if (value == null) {
            throw new NullPointerException(CANNOT_BE_NULL);
        }
        if (value < 0) {
            throw new NumberFormatException(CANNOT_BE_NEGATIVE);
        }
    }

    public Map<String, Integer> getMemory() {
        return memory;
    }

    public void setMemory(Map<String, Integer> memory) {
        this.memory = memory;
    }
}
