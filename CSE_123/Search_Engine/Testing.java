import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("Book 2 string constructor")
    public void testBook2String() {
        Book book = new Book("Title", "Author");

        // Change the following to fill in the expected output.
        assertEquals("Title", book.getTitle());

        // Change the null below to fill in the expected output.
        // Hint: you can use List.of("some string") to make a list with one element.
        assertEquals(List.of("Author"), book.getArtists());

        // Change the following to fill in the expected output.
        assertEquals("Title by [Author]", book.toString());
    }

    @Test
    @DisplayName("Book string, list constructor")
    public void testBookStringList() {
        Book book = new Book("Title", List.of("Author 1", "Author 2"));

        // TODO: Test that getTitle returns "Title"
        assertEquals("Title", book.getTitle());

        // TODO: Test that getArtists returns a list containing
        //       "Author 1" and "Author 2"
        assertEquals(List.of("Author 1", "Author 2"), book.getArtists());

        // TODO: Test that toString returns the correctly
        //       String representation
        assertEquals("Title by [Author 1, Author 2]", book.toString());
        
    }

    @Test
    @DisplayName("createIndex tests")
    public void testInvertedIndex() {
        Book mistborn = new Book("Mistborn", "Brandon Sanderson",
                                 new Scanner("Epic fantasy \n worldbuildling content"));
        Book farenheit = new Book("Farenheit 451", "Ray Bradbury",
                                  new Scanner("Realistic \"sci-fi\" content"));
        Book hobbit = new Book("The Hobbit", "J.R.R. Tolkein",
                               new Scanner("Epic fantasy quest content"));
        
        List<Media> books = List.of(mistborn, farenheit, hobbit);
        Map<String, Set<Media>> index = InvertedIndex.createIndex(books);
        
        // Finish the following statements by using assertTrue/assertFalse to test that the 
        // quotes around sci-fi aren't ignored. Then uncomment it!
         assertFalse(index.containsKey("sci-fi"));
         assertTrue(index.containsKey("\"sci-fi\""));

        // Change the following line such that following test passes
        // (what original books include 'fantasy'?)
        Set<Book> expected = Set.of(mistborn, hobbit);
        assertEquals(expected, index.get("fantasy"));
    }
}
