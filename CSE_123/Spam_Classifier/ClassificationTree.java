// Dhruv Aravind 
// 11/21/2024
// CSE 123 
// P3: Spam Classifier
// TA: Laura Khotemlyansky

import java.util.*;
import java.io.*;

// The ClassificationTree class represents a decision tree used for classification tasks.
// It supports building the tree from various data sources, such as files or lists of 
// data and labels. 
public class ClassificationTree extends Classifier {

    private final int THRESHOLDSTART = 11;
    private final int FEATURESTART = 9;
    private ClassificationNode front;

    // Class that represents every node within the Classification Tree and can either be a split
    // or a label if it is a leaf node.
    private static class ClassificationNode {
        public ClassificationNode right;
        public ClassificationNode left;
        public String label;
        public Split split;
        public Classifiable oldData;

        // Constructor that creates a node with the inputted split. Split must be
        // non-null
        public ClassificationNode(Split split) {
            this.split = split;
        }

        // Constructor that creates a node with the inputted label.
        public ClassificationNode(String label) {
            this.label = label;
        }

        // Constructor that creates a node with the inputted data and label. Data must
        // be non-null
        public ClassificationNode(Classifiable data, String label) {
            this(label);
            this.oldData = data;
        }

        public ClassificationNode(Split split, ClassificationNode left, ClassificationNode right) {
            this(split);
            this.left = left;
            this.right = right;
        }

    }

    /*
     * Behavor:
     * - Creates a Classification Tree using the inputted scanner.
     * Parameters:
     * - sc: Scanner that reads a file. These file will contain a pair of lines to
     * represent intermediary nodes and one line to represent leaf nodes in the
     * ClassificationTree. The first line in each intermediary node pair will start "Feature: "
     * followed by the feature and the second line will start with "Threshold: " followed by
     * the threshold. Lines representing the leaf nodes will simply contain the label.
     * Must be non-null.
     * Returns:
     * - N/A
     * Exceptions:
     * - N/A
     * 
     */
    public ClassificationTree(Scanner sc) {
        this.front = classificationTreeHelper(sc);
    }

    /*
     * Behavior:
     * - Private recursive method that creates the Classification Tree using an
     * inputted scanner.
     * Returns:
     * - ClassificationNode: A Classification Node that represents the root node of
     * the ClassifictionTree.
     * Parameters:
     * - sc: Scanner that reads a file. These file will contain a pair of lines to
     * represent intermediary nodes and one line to represent leaf nodes in the
     * ClassificationTree. The first line in each intermediary node pair will start "Feature: "
     * followed by the feature and the second line will start with "Threshold: " followed by
     * the threshold. Lines representing the leaf nodes will simply contain the label.
     * Must be non-null.
     * Exceptions:
     * - N/A
     */
    private ClassificationNode classificationTreeHelper(Scanner sc) {
        String next = sc.nextLine();
        if (!next.contains("Feature")) {
            return (new ClassificationNode(next));
        }
        String feature = next.substring(FEATURESTART);
        next = sc.nextLine();
        double threshold = Double.parseDouble(next.substring(THRESHOLDSTART));

        ClassificationNode temp = new ClassificationNode(new Split(feature, threshold),
                classificationTreeHelper(sc), classificationTreeHelper(sc));
        return (temp);
    }

    /*
     * Behavior:
     * - Constructor that creates a Classification Tree given 2 lists containing
     * data and label names.
     * Parameters:
     * - data: A list of Classifiable's that represent the data for each label.
     * Must be non-null.
     * - results: A list of Strings that represent the names of each label.
     * Must be non-null.
     * Each value in the data list corresponds to the value in the result list at
     * the same index
     * Returns:
     * - N/A
     * Exceptions:
     * - IllegalArgumentException: If the sizes of data and result are not the same.
     * If the size of the lists are 0
     */
    public ClassificationTree(List<Classifiable> data, List<String> results) {
        if (data.size() != results.size() || data.size() == 0) {
            throw new IllegalArgumentException();
        }

        this.front = new ClassificationNode(data.get(0), results.get(0));
        for (int i = 1; i < data.size(); i++) {
            front = classificationTreeHelper2(data, results, front, i);
        }
    }

    /*
     * Behavior:
     * - Private recursive helper method that creates a Classification Tree.
     * Parameters:
     * - data: A ist of Classifiable's that represent the data for each label.
     * Must be non-null.
     * - results: A list of Strings that represent the names of each label.
     * Must be non-null.
     * - curr: The current node the of the Classification Tree the method is on.
     * Must be non-null
     * - listPos: The position of the current label in the lists of data and results.
     * Returns:
     * - ClassificiationNode: The updated or newly created ClassificationNode after processing 
 *   the data at the specified position.
     * Exceptions:
     * - N/A
     */
    private ClassificationNode classificationTreeHelper2(List<Classifiable> data, 
            List<String> results, ClassificationNode curr, int listPos) {
        if (curr.split == null) {
            if (!curr.label.equals(results.get(listPos))) {
                ClassificationNode temp = new ClassificationNode(curr.oldData, curr.label);
                ClassificationNode newNode = new ClassificationNode(
                    curr.oldData.partition(data.get(listPos)));
                if (newNode.split.evaluate(temp.oldData)) {
                    newNode.left = temp;
                    newNode.right = new ClassificationNode(data.get(listPos), 
                        results.get(listPos));
                } else {
                    newNode.left = new ClassificationNode(data.get(listPos), results.get(listPos));
                    newNode.right = temp;
                }
                return newNode; 
            }
        } else {
            if (curr.split.evaluate(data.get(listPos))) {
                curr.left = classificationTreeHelper2(data, results, curr.left, listPos);
            } else {
                curr.right = classificationTreeHelper2(data, results, curr.right, listPos);
            }
        }
        return curr; 
    }

    /*
     * Behavior:
     * - Saves the current Classification Tree to the inputted PrintStream.
     * Parameters:
     * - ps: The PrintStream which the Classificaion Tree is going to be saved to.
     * The printstream will contain a pair of lines to represent intermediary nodes
     * and one line to represent leaf nodes in the ClassificationTree. A split will start with
     * "Feature: " followed by the feature and the second line will start with "Threshold: "
     * followed by the threshold. A leaf node will simply show the label.
     * Must be non-null.
     * Returns:
     * - N/A
     * Exceptions:
     * - N/A
     */
    public void save(PrintStream ps) {
        save(ps, front);
    }

    /*
     * Behavior:
     * - Private recursive helper method that saves the Classification Tree to the
     * inputeed
     * PrintStream.
     * Parameters:
     * - ps: The PrintStream which the Classificaion Tree is going to be saved to.
     * The printstream will contain a pair of lines to represent intermediary nodes
     * and one line to represent leaf nodes in the ClassificationTree. A split will start with
     * "Feature: " followed by the feature and the second line will start with "Threshold: "
     * followed by the threshold. A leaf node will simply show the label.
     * Must be non-null.
     * - curr: The current node the of the Classification Tree the method is on.
     * Must be non-null
     * Returns:
     * - N/A
     * Exceptions:
     * - N/A
     */
    private void save(PrintStream ps, ClassificationNode curr) {
        if (curr.split == null) {
            ps.println(curr.label);
        } else {
            ps.println(curr.split.toString());
            save(ps, curr.left);
            save(ps, curr.right);
        }

    }

    /*
     * Behavior:
     * - Checks whether the input can be classified or not
     * Parameters:
     * - input: A Classifiable object that must be non-null
     * Returns:
     * - boolean: True if the input can be classified and false if it can't.
     * Exceptions:
     * - N/A
     */
    public boolean canClassify(Classifiable input) {
        return canClassify(input, front);
    }

    /*
     * Behavior:
     * - Private recursive method that checks whether the input can be classified or
     * not
     * Parameters:
     * - input: A Classifiable object that must be non-null
     * - curr: The current node the of the Classification Tree the method is on.
     * Must be non-null
     * Returns:
     * - boolean: True if the input can be classified and false if it can't.
     * Exceptions:
     * - N/A
     */
    private boolean canClassify(Classifiable input, ClassificationNode curr) {
        if (curr.split == null) {
            return true;
        }
        if (!(input.getFeatures().contains(curr.split.getFeature()))) {
            return false;
        }

        return (canClassify(input, curr.left) && canClassify(input, curr.right));
    }

    /*
     * Behavior:
     * - Classifies the given Classifiable input
     * Parameters:
     * - input: A Classifiable object that must be non-null
     * Returns:
     * - String: The label that the Classification Tree points to given the inputted Classifiable 
     *           object
     * Exceptions:
     * - IllegalArgumentException: The input must be classifiable.
     */
    public String classify(Classifiable input) {
        if (!canClassify(input)) {
            throw new IllegalArgumentException("The provided input cannot be classified");
        }
        return (classify(input, front));
    }

    /*
     * Behavior:
     * - Private recursive method that classifies the given Classifiable input
     * Parameters:
     * - input: A Classifiable object that must be non-null
     * - curr: The current node the of the Classification Tree the method is on.
     * Must be non-null
     * Returns:
     * - String: The label that the Classification Tree points to given the inputted Classifiable 
     *           object
     * Exceptions:
     * - IllegalArgumentException: The input must be classifiable.
     */
    private String classify(Classifiable input, ClassificationNode curr) {
        if (curr.split == null) {
            return (curr.label);
        }
        boolean eval = curr.split.evaluate(input);
        String lab;
        if (eval) {
            lab = classify(input, curr.left);
        } else {
            lab = classify(input, curr.right);
        }
        return (lab);
    }
}