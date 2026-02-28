package Page.Ebay;

import Page.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SearchResultPage extends BasePage {

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "gh-ac")
    private WebElement searchInput;

    @FindBy(id = "gh-cat")
    private WebElement categoryDropdown;

    @FindBy(id = "gh-search-btn")
    private WebElement searchButton;

    @FindBy(xpath = "//h1[contains(@class,'srp-controls__count-heading')]")
    private WebElement resultHeading;


    public void searchProduct(String keyword, String category) {

        type(searchInput, keyword);
        Select select = new Select(categoryDropdown);
        select.selectByVisibleText(category);
        waitForClickable(searchButton);
        click(searchButton);
    }

    public boolean isKeywordInUrl(String keyword) {
        wait.until(ExpectedConditions.urlContains("_nkw=" + keyword)
        );
        return driver.getCurrentUrl().contains("_nkw=" + keyword);
    }

    public boolean isKeywordInHeading(String keyword) {
        String text = resultHeading.getText().toLowerCase();
        return text.contains(keyword.toLowerCase());
    }




}
