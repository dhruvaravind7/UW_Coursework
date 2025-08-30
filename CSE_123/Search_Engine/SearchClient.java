/* Dhruv Aravind
 * 10/02/2024
 * CSE 123
 * C0: SearchEngine
 * TA: Laura Khotemlyansky
 */


import java.io.*;
import java.util.*;
// Name: TODO
// Date: TODO

// This class allows users to find and rate books within BOOK_DIRECTORY
// containing certain terms
public class SearchClient {
    public static final String BOOK_DIRECTORY = "./books";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        List<Book> books = loadBooks();
        List<Media> media = new ArrayList<>(books);

        Map<String, Set<Media>> index = createIndex(media);

        System.out.println("Welcome to the CSE 123 Search Engine!");
        String command = "";
        while (!command.equalsIgnoreCase("quit")) {
            System.out.println("What would you like to do? [Search, Rate, Quit]");
            System.out.print("> ");
            command = console.nextLine();

            if (command.equalsIgnoreCase("search")) {
                searchQuery(console, media, index);
            } else if (command.equalsIgnoreCase("rate")) {
                addRating(console, media);
            } else if (!command.equalsIgnoreCase("quit")) {
                System.out.println("Invalid command, please try again.");
            }
        }
        System.out.println("See you next time!");
    }
    
    /* Behavior:
     *  - Creates an map with the keys being the words in every book and the values being the 
     *    books those words are found in. Words are not case sensitive.
     * Parameters:
     *  - documents: A List of type Media containing all the Books that are going into the map
     * Return
     *  - Map<String, Set<Media>>: the map representing the InvertedIndex
     */
    public static Map<String, Set<Media>> createIndex(List<Media> documents) {
        Map<String, Set<Media>> index = new TreeMap<String, Set<Media>>();
        for (Media book : documents) {

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

    // Allows the user to search a specific query using the provided 'index' to find appropraite
    //  Media entries.
    // Parameters:
    //   console - the Scanner to get user input from
    //   index - invertedIndex mapping terms to the Set of media containing those terms
    public static void searchQuery(Scanner console, List<Media> documents, Map<String, Set<Media>> index) {
        System.out.println("Enter query:");
        System.out.print("> ");
        String query = console.nextLine();

        for (Media doc : documents) { 
            doc.setQuery(query); 
        }
        Set<Media> result = search(index, query);
        
        if (result.isEmpty()) {
            System.out.println("\tNo results!");
        } else {
            for (Media m : result) {
                System.out.println("\t" + m.toString());
            }
        }
    }

    // Allows the user to add a rating to one of the options wthin 'media'
    //
    // Parameters:
    //   console - the Scanner to get user input from
    //   media - list of all media options loaded into the search engine
    public static void addRating(Scanner console, List<Media> media) {
        for (int i = 0; i < media   .size(); i++) {
            System.out.println("\t" + i + ": " + media.get(i).toString());
        }
        System.out.println("What would you like to rate (enter index)?");
        System.out.print("> ");
        int choice = Integer.parseInt(console.nextLine());
        if (choice < 0 || choice >= media.size()) {
            System.out.println("Invalid choice");
        } else {
            System.out.println("Rating [" + media.get(choice).getTitle() + "]");
            System.out.println("What rating would you give?");
            System.out.print("> ");
            int rating = Integer.parseInt(console.nextLine());
            media.get(choice).addRating(rating);
        }
    }

    // Searches a specific query using the provided 'index' to find appropraite Media entries.
    //  terms are determined by whitespace separation. If a term is proceeded by '-' any entry
    //  containing that term will be removed from the result.
    //
    // Parameters:
    //   index - invertedIndex mapping terms to the Set of media containing those terms
    //   query - user's entered query string to use in searching
    //
    // Returns:
    //   An optional set of all Media containing the requirested terms. If none, Optional.Empty()
    public static Set<Media> search(Map<String, Set<Media>> index, String query) {
        Set<Media> ret = new TreeSet<>();

        Scanner tokens = new Scanner(query);
        while (tokens.hasNext()) {
            String token = tokens.next().toLowerCase();

            if (index.containsKey(token)) {
                ret.addAll(index.get(token));
            }
        }
        return ret;
    }

    // Loads all books from BOOK_DIRECTORY. Assumes that each book starts with two lines -
    //      "Title: " which is followed by the book's title
    //      "Author: " which is followed by the book's author
    //
    // Returns:
    //   A list of all book objects corresponding to the ones located in BOOK_DIRECTORY
    public static List<Book> loadBooks() throws FileNotFoundException {
        List<Book> ret = new ArrayList<>();
        
        File dir = new File(BOOK_DIRECTORY);
        for (File f : dir.listFiles()) {
            Scanner sc = new Scanner(f, "utf-8");
            String title = sc.nextLine().substring("Title: ".length());
            String author = sc.nextLine().substring("Author: ".length());

            ret.add(new Book(title, author, sc));
        }

        return ret;
    }
}
