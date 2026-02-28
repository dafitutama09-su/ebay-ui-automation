package Ebay;

import Page.Ebay.SearchResultPage;
import core.BaseTest;
import core.DriverManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchResultTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return new Object[][]{
                {"MacBook", "Computers/Tablets & Networking"}
        };
    }

    @Test(dataProvider = "searchData")
    public void testSearchProduct(String keyword, String category) {

        SearchResultPage searchResultPage = new SearchResultPage(DriverManager.getDriver());

        searchResultPage.searchProduct(keyword, category);

        Assert.assertTrue(
                searchResultPage.isKeywordInUrl(keyword),
                "Keyword tidak masuk ke URL"
        );

        Assert.assertTrue(
                searchResultPage.isKeywordInHeading(keyword),
                "Keyword tidak ada di heading"
        );

    }




}
