import java.util.Comparator;

/***
 * Comparator for Avenger objects. Sorts Avengers by sequenceMentionedIndex in ascending order, or the order they were mentioned.
 * 
 * @author Hoang, Justin, Zain
 */
public class MentionIndexComparator implements Comparator<Avenger> {
    public int compare(Avenger a1, Avenger a2) {
        return a1.getMentionedIndex() - a2.getMentionedIndex();
    }
}
