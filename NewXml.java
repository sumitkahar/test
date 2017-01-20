package zLearn;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class NewXml {

	public static int rowCnt=1;
	public static String countryName="";
	public static Boolean conuntryExist=false;
	//XSSFSheet spreadsheet = myWorkBook.createSheet("Detailed_Report_"+timestamp);

	//public static File myFile = new File("./Controller.xlsx");
	//public static XSSFWorkbook myWorkBook = new XSSFWorkbook ();
	//public static XSSFSheet spreadsheet = myWorkBook.createSheet("Detailed_Report");
	//public static XSSFRow row;

	public static void main(String[] args) throws InterruptedException, IOException {

		WebDriver driver;
		//			deleteFile();
		String []Menu_heading = {"Countries","Menu","SubMenu","SubMenu_1","ActualEng_Title","Overridden_Title","XML URL","Actual URL","Results","Final Results"};
		String []countries={"UK","Germany","France","Austria","Netherlands","Belgium-FR","Belgium-NL","Norway","Sweden","Denmark","Finland","Estonia","Portugal","Spain","Latvia","Italy","Lithuania","Greece","Poland","Russia","Serbia","Romania","Ukraine-RU","Ukraine-UK","Czech Republic","Slovakia","Croatia","Slovenia","Switzerland-DE","Switzerland-FR","Switzerland-IT","Hungary"};
		String []domain={"co.uk/men/system/","de/maenner/system/","fr/hommes/system/","at/maenner/system/","nl/tenamen/system/","be/fr/hommes/system/","be/nl/tenamen/system/","no/menn/system/","nu/men/system/","dk/maend/system/","fi/miehet/system/","ee/mehed/system/","pt/homens/system/","es/hombres/system/","lv/viriesiem/system/","it/uomini/system/","lt/vyrai/system/","gr/andres/system/","pl/mezczyzni/system/","ru/nederzhanieumuzhchin/system/","rs/muskarci/system/","ro/barbati/system/","ua/ru/nederzhanieumuzhchin/system/","ua/uk/nederzhanieumuzhchin/system/","http://www.tenacz.cz/muzi/system/","sk/muzi/system/","hr/muskarci/system/","si/moski/system/","ch/de/fuermaenner/system/","ch/fr/hommes/system/","ch/it/uomini/system/","hu/ferfiaknb/system"};
		String initUrl="https://www.tena.";

		File myFile = new File("./Controller.xlsx");
		if(!myFile.exists())
			createSheet("Controller.xlsx","Detailed_Report",Menu_heading);
		else
		{
			moveFile();
			createSheet("Controller.xlsx","Detailed_Report",Menu_heading);    			
		}

		//writeInFile("Controller.xlsx","Detailed_Report",hell);
		//String timestamp=readPreTimestampFromMasterSheet();
		for(int c=0;c<1;c++){

			String URL= initUrl+domain[c];

			if(URL.contains(domain[c])){
				System.out.println(countries[c]);
				countryName=countries[c];
				conuntryExist=true;
			}

			Map<String, String[]> data = new HashMap<String, String[]>();
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			driver=new ChromeDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			driver.get(URL+"navigation.xml");

			driver.manage().window().maximize();
			Thread.sleep(10000);            
			String javascript = "return arguments[0].innerHTML";
			List<WebElement> links=driver.findElements(By.tagName("item"));

			String []arrMainString = null; 
			String arrURL = null;
			String arrDisplaytitle=null;
			String arrOverriddentitle=null;
			Boolean urll=false;
			Boolean dispTitle=false;
			Boolean overTitle=false;
			//char []chr = null;
			//String fURL = "";

			String []f_text;

			for(int i=0;i<links.size();i++)
			{
				String pageSource=(String)((JavascriptExecutor) driver).executeScript(javascript, links.get(i));
				System.out.println(pageSource);

				data.put(String.valueOf(i),new String[]{pageSource});                   
			}

			Set<String> newRows = data.keySet();
			int count = 0;

			for (String key : newRows)
			{
				//Row row = mySheet.createRow(rownum++);
				Object[] objArr = data.get(key);

				for (Object obj : objArr)
				{

					if(obj.toString().startsWith("<item id=")){
						count++;
						System.out.println("Incrementing the counter");                                      
						arrMainString = obj.toString().split("<item id=");

						for(int i= 0; i<= arrMainString.length-1;i++){
							//            System.out.println(arrMainString[i]);

							if(arrMainString[i].length() > 2){
								f_text = arrMainString[i].split("\" ");

								for(String rString:f_text){
									if(rString.toLowerCase().startsWith("url")){
										arrURL =rString.substring(rString.indexOf("=")+2, rString.length());
										System.out.println(arrURL);
										//menuGrouping(arrURL," "," ");  
										urll=true;


									}else if(rString.toLowerCase().startsWith("displaytitle")){
										arrDisplaytitle = rString.substring(rString.indexOf("=")+2, rString.length());
										System.out.println("displaytitle= "+arrDisplaytitle);
										//   menuGrouping(" ","displaytitle= "+arrDisplaytitle ," ");
										dispTitle=true;


									}else if(rString.toLowerCase().startsWith("overriddentitle")){
										arrOverriddentitle = rString.substring(rString.indexOf("=")+2, rString.length());
										System.out.println("overriddentitle= "+arrDisplaytitle);
										overTitle=true;
									}
									if((urll==true) && (dispTitle==true) && (overTitle==true))
									{
										menuGrouping(countryName,arrURL,arrDisplaytitle,arrOverriddentitle);
										urll=false;
										dispTitle=false;
										overTitle=false;
									}

								}  





								//f_URL = arrMainString[i].split(" ");

								/* for(String sURL:f_URL){
                           if(sURL.toLowerCase().startsWith("url")){
                             arrURL =sURL.substring(sURL.indexOf("=")+2, sURL.length()-1);
                             System.out.println(arrURL);

                             menuGrouping(arrURL);
                            break;
                        }
                      }*/

							}

						}
						break;
					}

				}

				if(count>0)
					break;
			}
			conuntryExist=false;
			driver.quit();
		} //BADA FOR	

	}

	public static void createSheet(String fileName, String sheetName,String []objectArr) throws FileNotFoundException
	{		

		try {

			File f = new File("./"+fileName);
			if(f.exists()) { 

			}else{

				XSSFWorkbook myWorkBook = new XSSFWorkbook ();

				XSSFSheet spreadsheet = myWorkBook.createSheet(sheetName);
				XSSFRow row;
				int rowcount=0;

				row = spreadsheet.createRow(rowcount);

				for(int i = 0;i<=objectArr.length-1;i++ ){

					Cell cell = row.createCell(i);
					cell.setCellValue(objectArr[i]);

				}

				FileOutputStream os = new FileOutputStream(fileName);
				myWorkBook.write(os);
				System.out.println("Controller creation finished ...");
				os.close();
			}	

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void menuGrouping(String countryName,String actURL,String displaytitle,String overriddentitle) throws IOException {
		// TODO Auto-generated method stub				
		
		System.out.println("latest_data :actURL "+actURL);
		System.out.println("latest_data : displaytitle "+displaytitle);
		System.out.println("latest_data : overriddentitle "+overriddentitle);
		String []arrMenu;

		arrMenu = actURL.split("/");

		switch(arrMenu.length){
		case 3:
			if(conuntryExist)
			{ 
				System.out.println("latest_data : countryName "+countryName);
				System.out.println("Menu: " + arrMenu[2]);
				writeInFile("Controller.xlsx","Detailed_Report",countryName,arrMenu[2],"","",displaytitle,overriddentitle,actURL);
				// writeExcel(rowCnt,arrMenu[2],"","",actURL);
				conuntryExist=false;
				break;
			}
			System.out.println("Menu: " + arrMenu[2]);
			writeInFile("Controller.xlsx","Detailed_Report","",arrMenu[2],"","",displaytitle,overriddentitle,actURL);
			// writeExcel(rowCnt,arrMenu[2],"","",actURL);
			break;
		case 4:
			System.out.println("SubMenu: " + arrMenu[3]);
			writeInFile("Controller.xlsx","Detailed_Report","","",arrMenu[3],"",displaytitle,overriddentitle,actURL);
			//  writeExcel(rowCnt,"",arrMenu[3],"",actURL);
			break;
		case 5:
			System.out.println("SubMenu_1: " + arrMenu[4]);
			writeInFile("Controller.xlsx","Detailed_Report","","","",arrMenu[4],displaytitle,overriddentitle,actURL);
			// writeExcel(rowCnt,"","",arrMenu[4],actURL);
			break;
		default:
			System.out.println("Basic Page!");
			break;
		}


	}

	/*public static void writeExcel(int rCnt, String menu,String subMenu,String subMenu_1,String xmlURL) throws IOException {

	 row = spreadsheet.createRow(rCnt);
	 Cell cell;
	 for(int colCnt=0;colCnt<=3;colCnt++)
	 { 
		 cell = row.createCell(colCnt);
		 if(colCnt==0)
		 {
			 cell.setCellValue(menu);
			 cell = null;
		 }
		 else if(colCnt==1)
		 {
			 cell.setCellValue(subMenu);
			 cell =null;
		 }
		 else if(colCnt==2)
		 {
			 cell.setCellValue(subMenu_1);
			 cell = null;
		 }
		 else if(colCnt==3)
		 {
			 cell.setCellValue(xmlURL);
			 cell=null;
		 }

		 FileOutputStream os = new FileOutputStream(myFile);
		 myWorkBook.write(os);
		 os.close();
	 }





	System.out.println("rowCnt :"+rowCnt);
	System.out.println("menu :"+menu);
	System.out.println("subMenu :"+subMenu);
	System.out.println("subMenu_1 :"+subMenu_1);
	System.out.println("xmlURL :"+xmlURL);

	rowCnt++;

//	 	FileOutputStream os = new FileOutputStream(myFile);

	public static XSSFWorkbook myWorkBook = new XSSFWorkbook ();
	//XSSFSheet spreadsheet = myWorkBook.createSheet("Detailed_Report_"+timestamp);
	public static XSSFSheet spreadsheet = myWorkBook.createSheet("Detailed_Report");
	public static XSSFRow row;
	public static File myFile = new File("./Controller.xlsx");



//		System.out.println("Controller Sheet Creation finished ...");
//		os.close();
}*/

	public static void moveFile() throws IOException
	{
		InputStream inStream = null;
		OutputStream outStream = null;

		File directory = new File("./data");
		if (!directory.exists()) {
			if (directory.mkdir()) {
				System.out.println("data Directory is created!");
			} else {
				System.out.println("Failed to create Results directory!");
			}
		}

		File oldpath =new File("./Controller.xlsx");
		File newpath =new File("./data/Controller.xlsx");

		inStream = new FileInputStream(oldpath);
		outStream = new FileOutputStream(newpath);

		byte[] buffer = new byte[1024];

		int length;
		//copy the file content in bytes
		while ((length = inStream.read(buffer)) > 0)
		{

			outStream.write(buffer, 0, length);
		}


		inStream.close();
		outStream.close();

		//delete the original file
		oldpath.delete();

		System.out.println("File is copied successful!");

	}


	public static void writeInFile(String fileName,String sheetName, String cntry, String menu,String subMenu,String subMenu_1,String ActualEng_Title,String Overridden_Title,String xmlURL) throws IOException {		

		//int rowcount=0;

		//   row = spreadsheet.createRow(rowcount);


		//Create a object of File class to open xlsx file

		File myFile =  new File("./" + fileName);

		//Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(myFile);

		Workbook myWorkbook = null;

		//Find the file extension by spliting file name in substring and getting only extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		//Check condition if the file is xlsx file	

		if(fileExtensionName.equals(".xlsx")){

			//If it is xlsx file then create object of XSSFWorkbook class

			myWorkbook = new XSSFWorkbook(inputStream);
			System.out.println("Extension of file "+fileName +" is .xlsx");

		}

		//Check condition if the file is xls file

		else if(fileExtensionName.equals(".xls")){

			//If it is xls file then create object of XSSFWorkbook class

			myWorkbook = new HSSFWorkbook(inputStream);
			System.out.println("Extension of file "+fileName +" is .xlx");

		}

		//Read sheet inside the workbook by its name

		Sheet mySheet = myWorkbook.getSheet(sheetName);

		//Find number of rows in excel file

		int rowCount = mySheet.getLastRowNum() - mySheet.getFirstRowNum();


		Row row = mySheet.getRow(0);

		//Create a new row and append it at last of sheet

		Row newRow = mySheet.createRow(rowCount+1);


		//Create a loop over the cell of newly created Row
		for(int colCnt=0;colCnt<=6;colCnt++)
		{ 
			Cell cell = newRow.createCell(colCnt);

			if(colCnt==0)
			{
				cell.setCellValue(cntry);
			}
			else if(colCnt==1)
			{
				cell.setCellValue(menu);
			}
			else if(colCnt==2)
			{
				cell.setCellValue(subMenu);
			}
			else if(colCnt==3)
			{
				cell.setCellValue(subMenu_1);
			}
			else if(colCnt==4)
			{
				cell.setCellValue(ActualEng_Title);
			}
			else if(colCnt==5)
			{
				cell.setCellValue(Overridden_Title);
			}
			else if(colCnt==6)
			{
				cell.setCellValue(xmlURL);
			}
		}
		/*  for(int j = 0; j < row.getLastCellNum(); j++){

	        //Fill data in row

	        Cell cell = newRow.createCell(j);

	        cell.setCellValue("test");

	    }*/

		//Close input stream

		inputStream.close();

		//Create an object of FileOutputStream class to create write data in excel file

		FileOutputStream opStream = new FileOutputStream(myFile);

		//write data in the excel file

		myWorkbook.write(opStream);
		//close output stream
		opStream.close();
		//	for(int i = 0;i<=objectArr.length-1;i++ ){

		//  Cell cell = row.createCell(i);
		//     cell.setCellValue(objectArr[i]);

		//    }

		//File myFile = new File("./Controller.xlsx");
		//  FileOutputStream os = new FileOutputStream(myFile);
		//	myWorkBook.write(os);
		System.out.println("Controller Sheet Creation finished ...");
		//	os.close();


	}



}
