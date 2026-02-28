package Ebay;

import Page.Ebay.HomePage;
import core.BaseTest;
import core.DriverManager;
import core.TestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {

    @DataProvider(name = "filterData")
    public Object[][] filterData() {
        return TestUtils.getTestDataFromCsv("data/filterData.csv");
    }

    @Test(dataProvider = "filterData")
    public void testFilter(String condition,
                           String price,
                           String location) {

        HomePage homePage = new HomePage(DriverManager.getDriver());

        homePage.Shopbycategory();
        homePage.BtnShopCategory();
        homePage.BtnCellSmartphone();
        homePage.openAllFilters();

        homePage.selectCondition(condition);

        homePage.selectPrice(price);

        homePage.selectLocation(location);

        homePage.clickApply();

        Assert.assertTrue(homePage.isFilterApplied(),
                "Filter tidak berhasil diterapkan");

        Assert.assertEquals(
                homePage.getAppliedFilterCount(),
                3,
                "Jumlah filter tidak sesuai"
        );
    }

    @Test(dataProvider = "filterData")
    public void testFilterPersistAfterRefresh(String condition,
                           String price,
                           String location) {

        HomePage homePage = new HomePage(DriverManager.getDriver());

        homePage.Shopbycategory();
        homePage.BtnShopCategory();
        homePage.BtnCellSmartphone();

        homePage.openAllFilters();
        homePage.selectCondition(condition);
        homePage.selectPrice(price);
        homePage.selectLocation(location);
        homePage.clickApply();

        String urlBeforeRefresh = DriverManager.getDriver().getCurrentUrl();
        DriverManager.getDriver().navigate().refresh();

        Assert.assertEquals(
                DriverManager.getDriver().getCurrentUrl(),
                urlBeforeRefresh,"URL berubah setelah refresh"
        );

        // Assert 2: Filter masih aktif
        Assert.assertTrue(
                homePage.isFilterButtonActive(),
                "Filter tidak aktif setelah refresh"
        );


    }

    @Test(dataProvider = "filterData")
    public void testResetAfterApply(String condition,
                                    String price,
                                    String location) {

        HomePage homePage = new HomePage(DriverManager.getDriver());

        homePage.Shopbycategory();
        homePage.BtnShopCategory();
        homePage.BtnCellSmartphone();

        homePage.openAllFilters();
        homePage.selectCondition(condition);
        homePage.selectPrice(price);
        homePage.selectLocation(location);
        homePage.clickApply();

        homePage.openAllFilters();
        homePage.clickReset();

        Assert.assertFalse(
                homePage.isFilterAppliedrreset(),
                "Filter masih aktif setelah melakukan reset"
        );
    }
}








