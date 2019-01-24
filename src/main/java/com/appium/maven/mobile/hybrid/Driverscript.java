package com.appium.maven.mobile.hybrid;


import com.appium.maven.mobile.hybrid.util.ProjectConstants;
import com.appium.maven.mobile.hybrid.util.XlsReader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Driverscript {
	
	static AppKeyword keywords;
	ExtentTest test;
	
	public Driverscript(ExtentTest test) {

		this.test=test;
	}

	public void executekeyword(String testUnderExecution) {
		
		String keywords_sheet = "keywords";
		// testUnderExecution = "TC1";
		keywords=new AppKeyword(test);
		
		XlsReader xls = new XlsReader(System.getProperty("user.dir")+"//data//Data.xlsx");
		
		int rows = xls.getRowCount(keywords_sheet);
		System.out.println("rows are ::" + rows);

		test.log(LogStatus.INFO, "rows are ::" + rows);
		
		for(int rNum=2;rNum<=rows;rNum++) {
			
			String tcid = xls.getCellData(keywords_sheet, ProjectConstants.TCID_COL, rNum);
			Boolean result = tcid.equalsIgnoreCase(testUnderExecution);
			if(result) {
				String keyword = xls.getCellData(keywords_sheet, ProjectConstants.KEYWORD_COL, rNum);
				System.out.println(keyword);
				String object = xls.getCellData(keywords_sheet,ProjectConstants.OBJECT_COL, rNum);
				String dataKey = xls.getCellData(keywords_sheet, ProjectConstants.DATA_COL, rNum);
	          //  System.out.println(tcid + "---"+ keyword + "---"+ object+"---"+ dataKey);
	            
	            String response=null;
				if(keyword.equals("openapp"))
				 response=keywords.openapp();
				else if(keyword.equals("click"))
					keywords.click(object);
				else if(keyword.equals("type"))
					keywords.type(object,dataKey);
				else if(keyword.equals("verifyText"))
					keywords.verifyTest(object,dataKey);
				else if(keyword.equals("addContact"))
					keywords.addContact();
			test.log(LogStatus.PASS, tcid+"------"+keyword+"---------"+object+"----"+dataKey);
			}
			
		}
		
	}
	  public AppKeyword getKeywords() {
		  return keywords;
	  }

}
