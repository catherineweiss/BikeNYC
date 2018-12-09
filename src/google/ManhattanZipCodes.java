package google;

import java.awt.Color;
import java.util.ArrayList;
/**
 * An ArrayList of zipcodes in Manhattan
 * @author Catherine Weiss
 *
 */
public class ManhattanZipCodes {
	
		private ArrayList<String> zipcodes = new ArrayList<String>();
		/**
		 * getter method for an ArrayList of zipcodes in Manhattan
		 * @return ArrayList of zipcodes
		 */
		public ArrayList<String> getZipcodes(){

			zipcodes.add("10001");
			zipcodes.add("10002");
			zipcodes.add("10003");
			zipcodes.add("10004");
			zipcodes.add("10005");
			zipcodes.add("10007");
			zipcodes.add("10009");
			zipcodes.add("10010");
			zipcodes.add("10011");
			zipcodes.add("10012");
			zipcodes.add("10013");
			zipcodes.add("10014");
			zipcodes.add("10016");
			zipcodes.add("10017");
			zipcodes.add("10018");
			zipcodes.add("10019");
			zipcodes.add("10020");
			zipcodes.add("10021");
			zipcodes.add("10022");
			zipcodes.add("10023");
			zipcodes.add("10024");
			zipcodes.add("10025");
			zipcodes.add("10026");
			zipcodes.add("10027");
			zipcodes.add("10028");
			zipcodes.add("10029");
			zipcodes.add("10030");
			zipcodes.add("10031");
			zipcodes.add("10032");
			zipcodes.add("10033");
			zipcodes.add("10034");
			zipcodes.add("10035");
			zipcodes.add("10036");
			zipcodes.add("10037");
			zipcodes.add("10038");
			zipcodes.add("10039");
			zipcodes.add("10040");
			zipcodes.add("10044");			
		
		return zipcodes;
		
}	
		
		public static void main(String[] args) {
			
			
			//practice parsing out zipcode
			String a1 = "Fifth Avenue, New York, NY 10001, USA";
			String a2 = "411 N New York Ave, Atlantic City, NJ 08401, USA";
			String a3 = "20 W 34th St, New York, NY 10001, USA";
			String a4 = "430 E 57th St, New York, NY 10022, USA";
			
			String[] parts = a1.split(",");
			System.out.println(parts[parts.length-2]);
			
			String zip = parts[parts.length-2].trim();
			zip = zip.replaceAll("[a-zA-Z]*", "").trim();
			System.out.println(zip);
			
			zip = "06840";
			
			
			
			ManhattanZipCodes mzc = new ManhattanZipCodes();
			ArrayList<String> zipcodes = new ArrayList<>();
			zipcodes = mzc.getZipcodes();
			
			
			if (!zipcodes.contains(zip)) {
				System.out.println("Enter an address in Manhattan:");
			} else {
				System.out.println("Continue on with rest of program.");
			}
			
			System.out.println();
			
		}
		
		
		
		
}
