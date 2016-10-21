import java.util.Random;


public class name {

	public static void main(String[] args) {

		System.out.println("---- Random String Generation ----");
		
		String alphabet = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
		int n = alphabet.length();
		String result = new String(); 
		Random r = new Random();
		for (int j=0; j<10; j++)
		{
			result= "";
			for (int i=0; i<7; i++)
				{
					result = result + alphabet.charAt(r.nextInt(n));
					//masterSheetXLS.setCellData(conf.getValues("ExcelSheet"), "FirstName", 2, result); 
				}
			System.out.println(result);
		}	
		
		System.out.println("---- Random Number Generation ----");
		
		String numbers = new String("0123456789");
		int no = numbers.length();
		String Phno = new String(); 
		Random rdm = new Random();
		for (int j=0; j<10; j++)
		{
			Phno="9";
			for (int i=0; i<9; i++)
				{
				Phno = Phno + numbers.charAt(rdm.nextInt(no));
					//masterSheetXLS.setCellData(conf.getValues("ExcelSheet"), "FirstName", 2, result); 
				}
			System.out.println(Phno);
		}	

	}

}

