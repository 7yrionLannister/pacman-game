package dataStructures;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.HashMap;

public class UnionFind<E> {
	
    /**A utility struct holding an an object's parent and rank.
     */
    private class Link {
        public E parent;
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
    private final Map<E, Link> elems = new HashMap<E, Link>();
    /**Creates a new UnionFind structure that is initially empty.
     */
    public UnionFind() {
    }
    /**Creates a new UnionFind structure that initially contains all of the elements from the specified container.  Duplicate elements
     * will appear with multiplicity one.
     * @param elems The elements to store in the UnionFind structure.
     */
    public UnionFind(Collection<? extends E> elems) {
        /* Iterate across the collection, adding each to this structure. */
        for (E elem: elems)
            add(elem);
    }
    /**Inserts a new element into the UnionFind structure that initially
     * is in its own partition.  If the element already exists, this function returns false.  Otherwise, it returns true.
     * @param elem The element to insert.
     * @return Whether the element was successfully inserted.
     * @throws NullPointerException If elem is null.
     */
    public boolean add(E elem) {
        /* Check for null. */
        if (elem == null)
            throw new NullPointerException("UnionFind does not support null.");

        /* Check whether this entry exists; fail if it does. */
        if (elems.containsKey(elem))
            return false;

        /* Otherwise add the element as its own parent. */
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
        /* Check whether the element exists; fail if it doesn't. */
        if (!elems.containsKey(elem))
            throw new NoSuchElementException(elem + " is not an element.");

        /* Recursively search the structure and return the result. */
        return recFind(elem);
    }
    /**Given an element which is known to be in the structure, searches for its representative element and returns it, applying path
     * compression at each step.
     * @param elem The element to look up.
     * @return The representative of the set containing it.
     */
    private E recFind(E elem) {
        /* Get the info on this object. */
        Link info = elems.get(elem);

        /* If the element is its own parent, it's the representative of its
         * class and we should say so.
         */
        if (info.parent.equals(elem))
            return elem;

        /* Otherwise, look up the parent of this element, then compress the
         * path.
         */
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
        /* Get the link info for the parents.  This also handles the exception
         * guarantee.
         */
        Link oneLink = elems.get(find(one));
        Link twoLink = elems.get(find(two));

        /* If these are the same object, we're done. */
        if (oneLink == twoLink) return;

        /* Otherwise, link the two.  We'll do a union-by-rank, where the parent
         * with the lower rank will merge with the parent with higher rank.
         */
        if (oneLink.rank > twoLink.rank) {
            /* Since each parent must link to itself, the value of oneLink.parent
             * is the representative of one.
             */
            twoLink.parent = oneLink.parent;
        } else if (oneLink.rank < twoLink.rank) {
            /* Same logic as above. */
            oneLink.parent = twoLink.parent;
        } else {
            /* Arbitrarily wire one to be the parent of two. */
            twoLink.parent = oneLink.parent;
            
            /* Bump up the representative of one to the next rank. */
            oneLink.rank++;
        }
    }
}