package Page.Ebay;

import Page.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // =============================
    // STATIC ELEMENTS (PAKAI FINDBY)
    // =============================
    @FindBy(xpath = "//button[.//span[normalize-space()='Shop by category']]")
    private WebElement buttonShopCategory;

    @FindBy(xpath = "//a[normalize-space()='Cell Phones, Smart Watches & Accessories']")
    private WebElement dropdownShopCategory;

    @FindBy(xpath = "//a[normalize-space()='Cell Phones & Smartphones']")
    private WebElement buttonCellSmartphone;

    @FindBy(xpath = "//button[.//span[normalize-space()='All Filters']]")
    private WebElement buttonAllFilter;

    @FindBy(xpath = "//h3[normalize-space()='Condition']")
    private WebElement headerCondition;

    @FindBy(xpath = "//h3[normalize-space()='Price']")
    private WebElement headerPrice;

    @FindBy(xpath = "//h3[normalize-space()='Item location']")
    private WebElement headerLocation;

    @FindBy(xpath = "//button[contains(@class,'btn-submit')]")
    private WebElement buttonApply;

    @FindBy(xpath = "//button[contains(@class,'filter-button--selected')]")
    private WebElement filtersAppliedButton;

    @FindBy(xpath = "//a[contains(@class,'btn-reset')]")
    private WebElement resetButton;


    // =============================
    // ACTIONS
    // =============================

    public void Shopbycategory() {
        click(buttonShopCategory);
    }

    public void BtnShopCategory() {
        click(dropdownShopCategory);
    }

    public void BtnCellSmartphone() {
        click(buttonCellSmartphone);
    }

    public void openAllFilters() {
        click(buttonAllFilter);
    }

    public void openCondition() {
        click(headerCondition);
    }

    public void openPrice() {
        click(headerPrice);
    }

    public void openLocation() {
        click(headerLocation);
    }

    public void clickReset() {
        waitForClickable(resetButton);
        click(resetButton);
    }

    public void selectCategory(String categoryName) {

        By locator = By.xpath("//a[normalize-space()='" + categoryName + "']");

        WebElement category = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        category.click();
    }

    public void selectCondition(String condition) {
        click(headerCondition);

        WebElement checkbox = wait.until(driver ->
                driver.findElement(By.id("group-checkbox-" + condition))
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkbox);
    }

    public void selectPrice(String price) {
        click(headerPrice);

        WebElement checkbox = wait.until(driver ->
                driver.findElement(By.id("group-radio-under-" + price))
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkbox);
    }

    public void selectLocation(String location) {
        click(headerLocation);

        WebElement radio = wait.until(driver ->
                driver.findElement(By.xpath("//input[@value='" + location + "']"))
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", radio);
    }

    public void clickApply() {
        waitForClickable(buttonApply);
        click(buttonApply);
    }

    public boolean isFilterApplied() {
        return filtersAppliedButton.isDisplayed();
    }

    public int getAppliedFilterCount() {
        String text = filtersAppliedButton.getText();
        return Integer.parseInt(text.split(" ")[0]);
    }

    public boolean isFilterButtonActive() {
        return filtersAppliedButton.isDisplayed();
    }

    public boolean isFilterAppliedrreset() {
        return driver.getCurrentUrl().contains("LH_ItemCondition");
    }
}
