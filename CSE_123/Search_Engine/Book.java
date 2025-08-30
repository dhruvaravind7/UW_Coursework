import java.util.*;

// This class represents a book which contains all the information about a book such as
// the Title, Authors, Ratings, Query, and the words that are in the book. 

public class Book implements Media {

    // The fields of the Book class
    private List<String> authors;
    private String title;
    private int totalRatings;
    private int numRatings;
    private List<String> words;

    // Constructor that sets the title and author of the book from the given parameters.
    public Book(String title, String author) {
        this(title, List.of(author)); 
    }   
    
    // Constructor that sets the title and author of the book from the given parameters.
    public Book(String title, List<String> authors) {
        this.authors = new ArrayList<>();
        this.title = title;
        this.authors = authors;
        this.words = new ArrayList<>();
    }

    // Constructor that sets the title and author of the book from the given parameters.
    // Adds each word in the book to the words list.
    public Book(String title, String author, Scanner sc) {
        this(title, List.of(author));
        List<String> words = new ArrayList<>();
        while (sc.hasNextLine()){
            String sentence = sc.nextLine();
            List<String> lineWords = Arrays.asList(sentence.split("\\s+"));
            for (int i = 0; i < lineWords.size(); i++){
                words.add(lineWords.get(i));
            }
        }
        this.words = words;
    }

    // returns the title of this book
    public String getTitle() {
        return (this.title);
    }

    // returns the list of the authors of this book
    public List<String> getArtists() {
        return (this.authors);
    }

    // adds the score given to the total ratings and increases the number of ratings by 1
    public void addRating(int score) {
        this.totalRatings += score;
        this.numRatings += 1;
    }

    // returns the number of ratings on this book
    public int getNumRatings() {
        return (this.numRatings);
    }

    // returns the average rating of this book
    public double getAverageRating() {
        if (this.numRatings == 0){
            return (0);
        }
        double average = (double) this.totalRatings / this.numRatings;
        return (average);
    }

    // returns a list of all the words in the book
    public List<String> getWords() {
        return (this.words);
    }

    // Returns the title and author if no ratings are present
    // Returns the title, author, average ratings, and total number of ratings if ratings are present.
    public String toString() {
        if (this.getNumRatings() == 0) {
            return (this.title + " by " + this.authors);
        } else {
            return (this.title + " by " + this.authors + ": " + 
                   (Math.round(getAverageRating() * 100.0) / 100.0) + " (" + getNumRatings()
                   + " ratings)");
        }
    }
}