public class AVLTree  <K extends Comparable<K>, V> extends BinarySearchTree<K,V> {

    public AVLTree(){
        super();
    }

    // Inserts the inputted key-value pair into the tree and returns the previous value. 
    // If the key was not previously in the tree, null is returned.
    public V insert(K key, V value){
        V[] oldHolder = (V[]) new Object[1];
        root = insertAVL(key, value, root, oldHolder);
        root.updateHeight();
        return oldHolder[0];
    }

    // Private helper method for insert that also rotates the tree if necessary.
    private TreeNode<K, V> insertAVL(K key, V value, TreeNode<K,V> curr, V[] oldHolder){
        
        // Base Case 1
        if(curr == null){
            oldHolder[0] = null;
            size++;
            return (new TreeNode<K, V>(key, value));
        }

        // Base Case 2
        if(curr.key.equals(key)){
            oldHolder[0] = curr.value;
            curr.value = value;
            return (curr);
        } 

        // Traverses the tree and adds the new node
        int currMinusNew = curr.key.compareTo(key); 
        if(currMinusNew > 0){
            curr.left = insertAVL(key, value, curr.left, oldHolder);
            
        } else{
            curr.right = insertAVL(key, value, curr.right, oldHolder);
        }

        // Update the height of the node
        curr.updateHeight();

        // Calculate height
        int leftHeight = (curr.left == null) ? -1 : curr.left.height;
        int rightHeight = (curr.right == null) ? -1 : curr.right.height;
        if (Math.abs(leftHeight - rightHeight) <= 1){
            return (curr);
        }

        // Left Rotation
        if (rightHeight > leftHeight){
            return (leftRotate(curr, key));
        } else{
            return (rightRotate(curr, key));
        }
        
    }

    private TreeNode<K, V> leftRotate(TreeNode<K, V> curr, K key){
        // Right-Left Case
        if (key.compareTo(curr.right.key) < 0){
            TreeNode<K, V> temp = curr.right;
            curr.right = curr.right.left;
            TreeNode<K, V> oldRR = curr.right.right;
            curr.right.right = temp;
            temp.left = oldRR;
            temp.updateHeight(); 
            curr.right.updateHeight();
            curr.updateHeight();
        }
        // Right-Right Case
        TreeNode<K, V> oldHead = curr;
        curr = curr.right;
        oldHead.right = curr.left;
        curr.left = oldHead;
        oldHead.updateHeight();
        curr.updateHeight();
        return (curr);
    }

    private TreeNode<K, V> rightRotate(TreeNode<K, V> curr, K key){
        // Left-Right Case
        if (key.compareTo(curr.left.key) > 0){
            TreeNode<K, V> temp = curr.left;
            curr.left = curr.left.right;

            TreeNode<K, V> oldLL = curr.left.left;
            curr.left.left = temp;
            temp.right = oldLL;
            temp.updateHeight(); 
            curr.left.updateHeight();
            curr.updateHeight();
        }
        // Left-Left Case
        TreeNode<K, V> oldHead = curr;
        curr = curr.left;
        oldHead.left = curr.right;
        curr.right = oldHead;
        oldHead.updateHeight();
        curr.updateHeight();
        return (curr);
    }
    

}

