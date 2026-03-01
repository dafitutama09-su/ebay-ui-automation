package Ebay;

import Page.Ebay.SearchResultPage;
import core.BaseTest;
import core.DriverManager;
import core.TestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchResultTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return TestUtils.getTestDataFromCsv("data/testData.csv");
    }

    @Test(dataProvider = "searchData")
    public void testSearchProduct(String keyword, String category, String type) {

        SearchResultPage searchResultPage =
                new SearchResultPage(DriverManager.getDriver());

        searchResultPage.searchProduct(keyword, category);

        Assert.assertTrue(
                searchResultPage.isKeywordInUrl(keyword),
                "Keyword tidak masuk ke URL"
        );

        if (type.equalsIgnoreCase("valid")) {

            Assert.assertTrue(
                    searchResultPage.isKeywordInHeading(keyword),
                    "Keyword tidak ada di heading"
            );

        } else if (type.equalsIgnoreCase("invalid")) {

            Assert.assertTrue(
                    searchResultPage.isNoResultMessageDisplayed(),
                    "Pesan 'No exact matches found' tidak muncul"
            );
        }
    }




}
