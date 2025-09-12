import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable <K,V> implements DeletelessDictionary<K,V>{
    private List<Item<K,V>>[] table; // the table itself is an array of linked lists of items.
    private int size;
    private static int[] primes = {11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853};

    public ChainingHashTable(){
        table = (LinkedList<Item<K,V>>[]) Array.newInstance(LinkedList.class, primes[0]);
        for(int i = 0; i < table.length; i++){
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    // Gives the hash key for the inputted key.
    // The hash key is the index of the table where the item will be stored.
    private int hash(K key) {
        return (Math.abs(key.hashCode()) % table.length);
    }

    // Resizes the table to a larger size that is ideally a prime number.
    private void resize() {
        int newLength;
        int currIndex = 0;
        while (currIndex < primes.length && primes[currIndex] <= table.length) {
            currIndex++;
        }

        if (currIndex < primes.length) {
            newLength = primes[currIndex];
        } else {
            newLength = table.length * 2 + 1; 
        }

        List<Item<K, V>>[] newTable = (LinkedList<Item<K, V>>[]) Array.newInstance(LinkedList.class, newLength);
        for (int i = 0; i < newLength; i++) {
            newTable[i] = new LinkedList<>();
        }

        for (List<Item<K, V>> bucket : table) {
            for (Item<K, V> item : bucket) {
                int newIndex = Math.abs(item.key.hashCode()) % newLength;
                newTable[newIndex].add(item);
            }
        }

        table = newTable;
    }

    public boolean isEmpty(){
        return size == 0;
    }    

    public int size(){
        return size;
    }

    // TODO
    public V insert(K key, V value){
        int index = hash(key);
        for (Item<K, V> item : table[index]) {
            if (item.key.equals(key)) {
                V oldValue = item.value;
                item.value = value;
                return (oldValue);
            }
        }
        table[index].add(new Item<>(key, value));
        size++;
        if ((double) size / table.length > 1.0) {
            resize();
        }
        return null;
    }

    // TODO
    public V find(K key){
        int index = hash(key);
        for (Item<K, V> item : table[index]) {
            if (item.key.equals(key)) {
                return (item.value);
            }
        }
        return null;
    }

    // TODO
    public boolean contains(K key){
        return (find(key) != null);
    }

    // TODO
    public List<K> getKeys(){
        List<K> keys = new ArrayList<>();
        for (List<Item<K, V>> col : table) {
            for (Item<K, V> row : col) {
                keys.add(row.key);
            }
        }
        return keys;
    }

    // TODO
    public List<V> getValues(){
        List<V> values = new ArrayList<>();
        for (List<Item<K, V>> col : table) {
            for (Item<K, V> row : col) {
                values.add(row.value);
            }
        }
        return values;
    }

    public String toString(){
        String s = "{";
        s += table[0];
        for(int i = 1; i < table.length; i++){
            s += "," + table[i];
        }
        return s+"}";
    }

}
