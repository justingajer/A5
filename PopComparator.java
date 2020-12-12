import java.util.Comparator;

/**
 * The Class finds the most popular avenger that is based off their avenger object.
 * It gathers the frequency and compares them to each other, The highest frequency is
 * put at the top of the list
 * 
 * @author Hoang, Justin, Zain
 */

 public class PopComparator implements Comparator<Avenger>{

    @Override
    public int compare(Avenger a1, Avenger a2){
        if (a1.getFrequencyMentioned() < a2.getFrequencyMentioned()){
            return 1;
            }

            else if (a1.getFrequencyMentioned()== a2.getFrequencyMentioned()){
                return a1.getAlias().compareTo(a2.getAlias());

            }
            else if (a1.getFrequencyMentioned()>a2.getFrequencyMentioned()){
                return -1;
            }
            return 0;
    }


 }