package dataStructures;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;

public class UnionFind<E> {
	
    private class Link {
    	
        /**This is the parent of the its respective Link.
         */
    	public E parent;
    	/**It represents the rank of the Link.
    	 */
        public int rank = 0;

        /**Creates a new Link object with the specified parent.
         * @param parent The initial value of the parent field.
         */
        Link(E parent) {
            this.parent = parent;
        }
    }
    /**A map from objects in the UnionFind structure to their associated rank and parent.
     */
    private HashMap<E, Link> elems = new HashMap<E, Link>();
    
    /**Creates a new UnionFind structure that is initially empty.
     */
    public UnionFind() {
    }
    /**Creates a new UnionFind structure that initially contains all of the elements from the specified container.  Duplicate elements
     * will appear with multiplicity one.
     * @param elems The elements to store in the UnionFind structure.
     */
    public UnionFind(ArrayList<E> elems) {
        for (E elem: elems) {
            add(elem);
        }
    }
    /**Inserts a new element into the UnionFind structure that initially
     * is in its own partition.  If the element already exists, this function returns false.  Otherwise, it returns true.
     * @param elem The element to insert.
     * @return Whether the element was successfully inserted.
     * @throws NullPointerException If elem is null.
     */
    public boolean add(E elem) {
        if (elem == null)
            throw new NullPointerException("UnionFind does not support null.");
        if (elems.containsKey(elem))
            return false;
        elems.put(elem, new Link(elem));
        return true;
    }
    /**Given an element, returns the representative element of the set containing that element.  If the element is not contained in the
     * structure, this method throws a NoSuchElementException.
     * @param elem The element to look up.
     * @return The representative of the set containing the element.
     * @throws NoSuchElementException If the element does not exist.
     */
    public E find(E elem) {
        if (!elems.containsKey(elem))
            throw new NoSuchElementException(elem + " is not an element.");
        return recFind(elem);
    }
    /**Given an element which is known to be in the structure, searches for its representative element and returns it, applying path
     * compression at each step.
     * @param elem The element to look up.
     * @return The representative of the set containing it.
     */
    private E recFind(E elem) {
        Link info = elems.get(elem);
        if (info.parent.equals(elem))
            return elem;
        info.parent = recFind(info.parent);
        return info.parent;
    }  
    /**Given two elements, unions together the sets containing those elements.  If either element is not contained in the set,
     * throws a NoSuchElementException.
     * @param one The first element to link.
     * @param two The second element to link.
     * @throws NoSuchElementException If either element does not exist.
     */
    public void union(E one, E two) {       
        Link oneLink = elems.get(find(one));
        Link twoLink = elems.get(find(two));
        if (oneLink == twoLink) return;
        if (oneLink.rank > twoLink.rank) {
            twoLink.parent = oneLink.parent;
        } else if (oneLink.rank < twoLink.rank) {
            oneLink.parent = twoLink.parent;
        } else {
            twoLink.parent = oneLink.parent;
            oneLink.rank++;
        }
    }
}