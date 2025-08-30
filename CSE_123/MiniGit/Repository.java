/* Dhruv Aravind
 * 10/30/2024
 * CSE 123
 * TA: Laura Khotemlyansky
 */


 import java.util.*;
 import java.text.SimpleDateFormat;
 
 // This class is a repository. It represents a group of commits.
 public class Repository {
 
     private String name;
     private Commit head;
 
     /* Behavior:
      *  - Sets the name of the repository with the input.
      * Parmeter:
      *  - name: The name that is going to be used as the name.
      *          Name cannot be null or have 0 characters. 
      * Returns:
      *  - N/A
      * Exceptions:
      *  - If the name is null or empty than an Illegal Argument Exception is thrown.
      */
     public Repository(String name){
         if (name == null || name.length() == 0){
             throw new IllegalArgumentException("Name cannot be null or 0 characters long");
         }
         this.name = name;
     }
 
     // Returns the head of the repository or null if there are no commits.
     public String getRepoHead(){
         if (this.head != null){
             return (head.id);
         }
         return (null);
     }
 
     // Returns the number of commits in the repo i.e. the size of the repo.
     public int getRepoSize(){
         int counter = 0;
         Commit curr = this.head;
         while (curr != null){
             counter ++;
             curr = curr.past;
         }
         return (counter);
     }
 
     // Returns the message for the repo. If there is no head, then the name of the repo is shown
     // followed by no commits. If there is a head, then the name of the repo is shown followed by 
     // the string representation of the head commit.
     public String toString(){
         if (this.head == null){
             return (String.format("%s - No commits", this.name));
         }
         return (String.format("%s - Current head: %s",this.name, this.head.toString()));
     }
 
 
     /* Behavior:
      *  - Checks to see if the inputted id is contained in the repo
      * Parameter:
      *  - targetId: The id that is being searched for.
      * Returns:
      *  - boolean: True if the inputted id is in the repo and false if it is not found.
      * Exceptions:
      *  - N/A 
      */
     public boolean contains(String targetId){
         Commit curr = this.head;
         while (curr != null){
             if (curr.id.equals(targetId)){
                 return (true);
             }
             curr = curr.past;
         }
         return (false);
     }
 
     /* Behavior:
      *  - Returns a certain number of commits in the repo from newest to oldest.
      * Parameters:
      *  n: The number of commits that are going to be shown.
      *     Must be a positive number.
      *     If n is greater than the size of the repo all commits are shown.
      * Returns:
      *  - String: A string containing the string representations of the n newest commits
      *            with a new line for each commit. Newer commits are at the top.
      * Exceptions:
      *  - IllegalArgumentException: If the input is less than i.e. not positive.
      */
     public String getHistory(int n){
         if (n < 1){
             throw new IllegalArgumentException("The input must be positive");
         }
         
         String history = "";
         int counter = 0;
         Commit curr = this.head;
         while (curr != null && counter < n){
             history += (curr.toString() + "\n");
             curr = curr.past;
             counter += 1;
         }
         return (history);
     }
     
     // Adds a new commit with the inputted message to the front of the repo and returns its id.
     public String commit(String message){
         Commit commited = new Commit(message, this.head);
         this.head = commited;
         return(this.head.id);
     }
 
     /* Behavior:
      *  - Removes a commit from the repo if its id is inputted
      * Parameter:
      *  - targetId: the id the of the commit that is going to be dropped
      * Returns:
      *  - boolean: False if the commit was not in the repo and true if was dropped from the repo
      * Exceptions:
      *  - N/A
      * 
      */
     public boolean drop(String targetId) {
         if (this.getRepoSize() == 0){
             return (false);
         }
         Commit curr = this.head;
         if (curr.id.equals(targetId)){
             this.head = this.head.past;
             return (true);
         }
         while (curr.past != null) {
             if (curr.past.id.equals(targetId)) {
                 curr.past = curr.past.past;
                 return(true);
             }
             curr = curr.past;
         }
         return (false);
     }
 
     /* Behavior:
      *  - Combines the inputted repo into this repo while preserving the chronological order
      *    of all commits. The inputted repo becomes empty after the combination.
      * Parameters:
      *  - other: The repositiory that is going to be combined.
                  Must be non-null
      * Returns:
      *  - N/A
      * Exceptions:
      *  - N/A
      */
     public void synchronize(Repository other){
         Commit curr = this.head; 
         if (other.getRepoSize() != 0){
             if (this.getRepoSize() == 0) {
                 this.head = other.head;
                 other.drop(other.head.id);
                 this.head.past = null;
             } else if (other.head != null && other.head.timeStamp > this.head.timeStamp) {
                 this.head = other.head;
                 other.drop(other.head.id);
                 this.head.past = curr;
             }
         }
         curr = this.head;
 
         while (other.head != null){
             if (curr.past == null){
                 curr.past = other.head;
                 other.head = null;
             } else if (other.head.timeStamp > curr.past.timeStamp) {
                 Commit next = curr.past;
                 curr.past = other.head;
                 other.drop(other.head.id);
                 curr.past.past = next;
             }
             curr = curr.past;
         }
     }
 
     /**
      * DO NOT MODIFY
      * A class that represents a single commit in the repository.
      * Commits are characterized by an identifier, a commit message,
      * and the time that the commit was made. A commit also stores
      * a reference to the immediately previous commit if it exists.
      *
      * Staff Note: You may notice that the comments in this 
      * class openly mention the fields of the class. This is fine 
      * because the fields of the Commit class are public. In general, 
      * be careful about revealing implementation details!
      */
     public static class Commit {
 
         private static int currentCommitID;
 
         /**
          * The time, in milliseconds, at which this commit was created.
          */
         public final long timeStamp;
 
         /**
          * A unique identifier for this commit.
          */
         public final String id;
 
         /**
          * A message describing the changes made in this commit.
          */
         public final String message;
 
         /**
          * A reference to the previous commit, if it exists. Otherwise, null.
          */
         public Commit past;
 
         /**
          * Constructs a commit object. The unique identifier and timestamp
          * are automatically generated.
          * @param message A message describing the changes made in this commit. Should be non-null.
          * @param past A reference to the commit made immediately before this
          *             commit.
          */
         public Commit(String message, Commit past) {
             this.id = "" + currentCommitID++;
             this.message = message;
             this.timeStamp = System.currentTimeMillis();
             this.past = past;
         }
 
         /**
          * Constructs a commit object with no previous commit. The unique
          * identifier and timestamp are automatically generated.
          * @param message A message describing the changes made in this commit. Should be non-null.
          */
         public Commit(String message) {
             this(message, null);
         }
 
         /**
          * Returns a string representation of this commit. The string
          * representation consists of this commit's unique identifier,
          * timestamp, and message, in the following form:
          *      "[identifier] at [timestamp]: [message]"
          * @return The string representation of this collection.
          */
         @Override
         public String toString() {
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
             Date date = new Date(timeStamp);
 
             return id + " at " + formatter.format(date) + ": " + message;
         }
 
         /**
         * Resets the IDs of the commit nodes such that they reset to 0.
         * Primarily for testing purposes.
         */
         public static void resetIds() {
             Commit.currentCommitID = 0;
         }
     }
 }
 