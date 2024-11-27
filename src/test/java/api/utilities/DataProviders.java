package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	private static final String path = System.getProperty("user.dir") + "\\src\\test\\resources\\Userdata.xlsx";

	@DataProvider(name = "Data")
	public Object[][] getAllData() {

		XLUtility utility = new XLUtility(path);
		int rowNum = utility.getRowCount("Sheet1");
		int cellNum = utility.getCellCount("Sheet1", 1);

		Object[][] apiData = new Object[rowNum][cellNum];

		for (int i = 1; i <= rowNum; i++) {
			for (int j = 0; j < cellNum; j++) {
				apiData[i - 1][j] = utility.getCellData("Sheet1", i, j);
			}
		}
		return apiData;
	}

	@DataProvider(name = "UserNames")
	public Object[] getUsername() {
		XLUtility utility = new XLUtility(path);
		int rowNum = utility.getRowCount("Sheet1");
		Object[] apiData = new Object[rowNum];

		for (int i = 1; i <= rowNum; i++) {
			apiData[i - 1] = utility.getCellData("Sheet1", i, 1);
		}

		return apiData;
	}

}
