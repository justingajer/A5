import java.util.*;

/**
 * COMP 2503 Winter 2020 Assignment 4
 * 
 * This program must read a input stream and keeps track of the frequency at
 * which an avenger is mentioned either by name or alias. The program must use HashMaps
 * for keeping track of the Avenger Objects, and it must use TreeMaps
 * for storing the data. 
 * 
 * @author Hoang, Justin, Zain
 * @date Fall 2020
 */

public class A4 {
	
	public String[][] avengerRoster = { { "captainamerica", "rogers" }, { "ironman", "stark" },
			{ "blackwidow", "romanoff" }, { "hulk", "banner" }, { "blackpanther", "tchalla" }, { "thor", "odinson" },
	 		{ "hawkeye", "barton" }, { "warmachine", "rhodes" }, { "spiderman", "parker" },
	 		{ "wintersoldier", "barnes" } };

		private int topN = 4;
		private int totalwordcount = 0;
		private Scanner input = new Scanner(System.in);

		//Hashmap with Avenger alias as key holding Avenger Object data
		private HashMap <String, Avenger> hmap = new HashMap <String, Avenger>();

		// Stores the map of avengers in popular order
		private TreeMap<Avenger, Avenger> mostPopAvengers = new TreeMap<Avenger, Avenger>(new PopComparator());
	
		//Stores the map of avengers in least popular order
		private TreeMap<Avenger, Avenger> leastPopAvengers = new TreeMap<Avenger, Avenger>(new LeastComparator()); 
	
		//Stores the map of avengers in alphabetical order
		private TreeMap<Avenger, Avenger> alphabeticalAvengers = new TreeMap<Avenger, Avenger>();

		//Treemap of avengers in mention order
		private TreeMap<Avenger, Avenger> mentionOrderAvengers = new TreeMap<Avenger, Avenger> (new MentionIndexComparator());

	/* 
	 * Create the necessary hashMap and treeMap objects to keep track of the Avenger objects 
	 * Remember that a hashtable does not have any inherent ordering of its contents.
	 * But for this assignment we want to be able to create the sorted lists avenger objects.
	 * Use TreeMap objects (which are binary search trees, and hence have an
	 * ordering) the following orders: alphabetical, mention order, most popular, and least popular
	 * The alphabetical order TreeMap must be constructed with the natural order of the Avenger objects.
	 * The other three orderings must be created by passing the corresponding Comparators to the 
	 * TreeMap constructor. 
	 */
	public static void main(String[] args) {
		A4 a4 = new A4();
		a4.run();
	}

	public void run() {
		readInput();
		createOrderedTreeMaps();
		printResults();
	}

	/**
	 * read the input stream and keep track how many times avengers are mentioned by
	 * alias or last name.
	 */
	private void readInput() {
		
		int mentionIndex = 0;

		while (input.hasNext()) {

			String word = cleanWord(input.next());
			

			if (word.length() > 0) {
				totalwordcount++;
				
				int rosterIndex = getRosterIndex(word);
				
				if (rosterIndex != -1) {
					
					if (hmap.get(word) != null) {
						hmap.get(word).mentioned();
					}
					else {
						Avenger hero = createAvenger(mentionIndex, rosterIndex);					
						hmap.put(hero.getAlias(), hero);
						mentionIndex++;
					}
				}
			}
		}
	}

	private String cleanWord(String next) {
		// First, if there is an apostrophe, the substring
		// before the apostrophe is used and the rest is ignored.
		// Words are converted to all lowercase.
		// All other punctuation and numbers are skipped.
		String ret;
		int inx = next.indexOf('\'');
		if (inx != -1)
			ret = next.substring(0, inx).toLowerCase().trim().replaceAll("[^a-z]", "");
		else
			ret = next.toLowerCase().trim().replaceAll("[^a-z]", "");
		return ret;
	}

	/**
	 *  Requires changes if AVENGER_ROSTER indices go beyond [x][y>1].
	 * 
	 * 	If no match  is found, returns -1; 
	 *  If a match is found in AVENGER_ROSTER, returns the first index of avengersRoster
	 * 
	 * @param input - String to be matched against AVENGER_ROSTER.
	 * @return int - first index of corresponding Avenger within avengersRoster. 
	 */
	private int getRosterIndex(String input) {
		int index= -1;
		for(int i = 0; i < avengerRoster.length; i++) {
			for (int j = 0; j < 2; j++) {
				if (avengerRoster[i][j].equals(input)) {
					index = i;
					return index;
				}
			}
		}
		return index;
	}
	
	private Avenger createAvenger(int mentionIndex, int rosterIndex) {
		return new Avenger(mentionIndex, avengerRoster[rosterIndex][0], avengerRoster[rosterIndex][1]);
	}

	private void createOrderedTreeMaps() {

		Set<String> keySet = hmap.keySet();
		Iterator<String> hmapIterate = keySet.iterator();

		while (hmapIterate.hasNext()) {
			String key = (String)hmapIterate.next();
			Avenger data = hmap.get(key);

			mostPopAvengers.put(data,data);
            leastPopAvengers.put(data,data);
			alphabeticalAvengers.put(data,data);
			mentionOrderAvengers.put(data,data);
		}
	}
	
	/**
	 * print the results
	 */
	private void printResults() {
	
		System.out.println("Total number of words: " + totalwordcount);
		
		System.out.println("Number of Avengers Mentioned: " + hmap.size());

		System.out.println();

		//Print the list of avengers in the order they appeared in the input
		System.out.println("All avengers in the order they appeared in the input stream:");
		for (Map.Entry<Avenger, Avenger> mOrderEntry : mentionOrderAvengers.entrySet()) {
			System.out.println(mOrderEntry.getKey());
		}
		System.out.println();

		//Print the most popular avengers, see the instructions for tie breaking
		System.out.println("Top " + topN + " most popular avengers:");
		int b = 0;
		for (Map.Entry<Avenger, Avenger> mostPopEntry : mostPopAvengers.entrySet()){
			if (b<topN){
				b++;
				System.out.println(mostPopEntry.getKey());
			}
		}
		System.out.println();

		//Print the least popular avengers, see the instructions for tie breaking
		System.out.println(leastPopAvengers);
		System.out.println("Top " + topN + " least popular avengers:");
		int c = 0;
		for (Map.Entry<Avenger, Avenger> leastPopEntry : leastPopAvengers.entrySet()) {
			if (c<topN){
				c++;
				System.out.println(leastPopEntry.getKey());
			}
		}
		System.out.println();

		//Print the list of avengers in alphabetical order
		System.out.println("All mentioned avengers in alphabetical order:");
		for (Map.Entry<Avenger, Avenger> entry : alphabeticalAvengers.entrySet()) {
			System.out.println(entry.getKey());
		}
	}
}