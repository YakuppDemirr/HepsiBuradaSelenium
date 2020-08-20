import com.csvreader.CsvWriter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BasePage{

    public WebDriver driver;
    public static final Logger logger = Logger.getLogger(HomePage.class);

    String csv = "hepsiburada.txt";
    CsvWriter writer = new CsvWriter(new FileWriter(csv, true), '\n');

    public BasePage() throws IOException {
    }

    public void setById(String id, String value){
        WebElement element = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        element.clear();
        element.sendKeys(value);
    }

    public void setByXpath(String xpath, String value){
        WebElement element = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        element.clear();
        element.sendKeys(value);
    }

    public void dropdownSelect(String css,String text)
    {
        Select dropdown = new Select(driver.findElement(By.cssSelector(css)));
        dropdown.selectByVisibleText(text);
    }

    public void hoverElement(By by) {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(by)).build().perform();
    }

    public void clickById(String id){
        WebElement element = new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.id(id)));
        driver.findElement(By.id(id)).click();
    }

    public void clickByxpath(String xpath){
        WebElement element = new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        driver.findElement(By.xpath(xpath)).click();
    }

    public void clickElementCssSelector(String key) {
        WebElement element = driver.findElement(By.cssSelector(key));
        clictToElement(element);
    }

    public void clictToElement(WebElement element) {
        scrollToElement(element);
        waitBySeconds(1);
        element.click();
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})", element);
    }

    public void optionClick(String key,Integer number){
        List<WebElement> elements = driver.findElements(By.cssSelector(key));
        clictToElement(elements.get(number));
    }

    public void waitBySeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


