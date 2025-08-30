import java.util.*;

// This class creates an Inverted Index given some media sources.
public class InvertedIndex {
    public static void main(String[] args) {
        List<Media> docs = List.of(
            new Book("Mistborn", "Brandon Sanderson",
                     new Scanner("Epic fantasy worldbuildling content")),
            new Book("Farenheit 451", "Ray Bradbury",
                     new Scanner("Realistic \"sci-fi\" content")),
            new Book("The Hobbit", "J.R.R. Tolkein",
                     new Scanner("Epic fantasy quest content quest"))
        );
        
        Map<String, Set<Media>> result = createIndex(docs);
        System.out.println(docs);
        System.out.println();
        System.out.println(result);
    }

    
    /* Behavior:
     *  - Creates a map with the keys being the words in every book and the values being the 
     *    books those words are found in. Words are not case sensitive. 
     * Parameters:
     *  - documents: A List of type Media containing all the Books that are going into the map
     * Return
     *  - Map<String, Set<Media>>: the map representing the InvertedIndex
     */ 
    public static Map<String, Set<Media>> createIndex(List<Media> docs) {
        Map<String, Set<Media>> index = new TreeMap<String, Set<Media>>();
        for (Media book : docs){

            for (int i = 0; i < book.getWords().size(); i++) {

                String currWord = book.getWords().get(i).toLowerCase();
                if (index.containsKey(currWord)) {
                    Set<Media> bigBooks = index.get(currWord);
                    bigBooks.add(book);

                } else {
                    Set<Media> books = new HashSet<Media>();
                    books.add(book);
                    index.put(currWord, books);
                }
            }
        }
        return (index);
    }
}
