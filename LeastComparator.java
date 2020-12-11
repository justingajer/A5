import java.util.Comparator;

/**
 * Class finds the least popular avenger. It takes the freq of each hero name and
 * compares it with other heroes freq. If the names are different then it get the freq
 * of each name and compare them. The one that has the lowest freq will be sent to the top
 * of the output. If it is tied, then it sorts them by alphabetical order. 
 */


public class LeastComparator implements Comparator<Avenger> {

    @Override
    public int compare (Avenger a1, Avenger a2){
    if( a1.getFrequencyMentioned() < a2.getFrequencyMentioned()){
        return -1;
    } 
    
    else if (a1.getFrequencyMentioned() == a2.getFrequencyMentioned()){
        if (a1.getLastName().length() < a2.getLastName().length()){
            return -1;
        }
        else if (a1.getLastName().length() == a2.getLastName().length()){
            return a1.getLastName() .compareTo (a2.getLastName());
        }

        else if(a1.getLastName().length() > a2.getLastName().length()) {
            return 1;
        }
        else if (a1.getFrequencyMentioned() > a2.getFrequencyMentioned()){
            return 1;
        }
    
       }
       return 0;
    }
}
