import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class FirstAutomation {
    WebDriver driver;
    @Before
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @Test
    public  void  getTitle() throws InterruptedException {
        driver.get("https://demoqa.com/");
        String title_actual= driver.getTitle();
        String title_expected="DEMOQA";
        Assert.assertEquals(title_actual,title_expected);
        Thread.sleep(4000);
    }
    @Test
    public void checkImageExists() throws InterruptedException {
        driver.get("https://demoqa.com/");
        boolean status = driver.findElement(By.xpath("//header/a[1]/img[1]")).isDisplayed();

        System.out.println(status);
        Assert.assertTrue(status);
        Thread.sleep(4000);
    }
    @Test
    public void submitForm() throws InterruptedException {
        driver.get("https://demoqa.com/text-box");
       // driver.findElement(By.id("userName")).sendKeys("Test User");//id
       // driver.findElement(By.cssSelector("[type=text]")).sendKeys("Test User");//type
        List<WebElement> userName=driver.findElements(By.className("form-control"));
        userName.get(0).sendKeys("Munna Abdullah");
        Thread.sleep(4000);
        userName.get(1).sendKeys("cmxmunna@gmail.com");
        Thread.sleep(4000);
        //driver.findElements(By.className("form-control")).get(0).sendKeys("Test User");//class
        //driver.findElement(By.id("userEmail")).sendKeys("test@gmail.com");
       // driver.findElements(By.className("form-control")).get(1).sendKeys("test@gmail.com");
//        driver.findElement(By.id("currentAddress")).sendKeys("Dhaka");
        WebElement currentAddress=driver.findElement(By.cssSelector("[placeholder= 'Current Address']"));
        currentAddress.sendKeys("Purbachal");
        driver.findElement(By.id("permanentAddress")).sendKeys("Kaliganj Gazipur");
        Thread.sleep(4000);

         JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");//scrolling
        //js.executeScript("window.scrollBy(0,500)");// scrolling by 500 pixel

        List <WebElement> BtnSubmit =driver.findElements(By.tagName("button"));
        BtnSubmit.get(1).click();

      //  driver.findElement(By.id("submit")).click();

        String name_actual = driver.findElement(By.id("name")).getText();
        String name_expected = "Munna Abdullah";
        Assert.assertTrue(name_actual.contains(name_expected));
        Thread.sleep(4000);
    }
    @Test
    public void clickOnButton() throws InterruptedException {
        driver.get("https://demoqa.com/buttons");
        List <WebElement> buttons =  driver.findElements(By.cssSelector("[type=button]"));
        Actions actions = new Actions (driver);
        actions.doubleClick(buttons.get(1)).perform();
        actions.contextClick(buttons.get(2)).perform();
        actions.click(buttons.get(3)).perform();

        //doubleClick assert
        String actual_message= driver.findElement(By.id("doubleClickMessage")).getText();
        String expected_message="You have done a double click";
        Assert.assertTrue(actual_message.contains(expected_message));

        //Click me assert
        String msg=driver.findElement(By.id("dynamicClickMessage")).getText();
        Assert.assertTrue(msg.contains("You have done a dynamic click"));
        Thread.sleep(4000);

    }
    @Test
    public void alert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
//        driver.findElement(By.id("alertButton")).click();
//        Thread.sleep(2000);
//        driver.switchTo().alert().accept();
//
//        driver.findElement(By.id("confirmButton")).click();
//        Thread.sleep(2000);
//        driver.switchTo().alert().accept();

        driver.findElement(By.id("promtButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().sendKeys("Himu");
    }
@Test
    public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        WebElement calander=  driver.findElement(By.id("datePickerMonthYearInput"));
        calander.sendKeys(Keys.CONTROL+"a", Keys.BACK_SPACE);
        calander.sendKeys("02/09/1998");
        calander.sendKeys(Keys.ENTER);


    }
    //dropdown solve code by salman vai
//    @Test
//    public void selectDropDownAjax() throws InterruptedException {
//        driver.get("https://demoqa.com/select-menu%22);
//                driver.findElements(By.className("css-yk16xz-control")).get(2).click();
//        Thread.sleep(1000);
//        Actions actions=new Actions(driver);
//        actions.keyDown(Keys.ARROW_DOWN).sendKeys(Keys.ENTER,Keys.ENTER,Keys.ENTER).perform();
//
//    }
@Test
    public void selectDropdownClassic(){
        driver.get("https://demoqa.com/select-menu");
        Select select=new Select(driver.findElement(By.id("oldSelectMenu")));
        select.selectByValue("4");

    }
    @Test
    public void selectDropDownAjax() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        driver.findElements(By.className("css-yk16xz-control")).get(2).click();
        Thread.sleep(1000);
        Actions actions=new Actions(driver);
        actions.keyDown(Keys.ARROW_DOWN).sendKeys(Keys.ENTER,Keys.ENTER,Keys.ENTER).perform();

    }
@Test
    public void capLetter() throws InterruptedException {
        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("Selenium Webdriver").keyUp(Keys.SHIFT).doubleClick().contextClick().perform();
        Thread.sleep(2000);
    }
    @Test
    public void takeScreenShot() throws IOException {
        driver.get("https://demoqa.com");
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);
    }
@Test
    public void handleMultipleTabs() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String> w = new ArrayList(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertEquals(text,"This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));

    }
    @Test
    public void handleChildWindow(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text= driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
            }
        }
        driver.close();
        driver.switchTo().window(mainWindowHandle);

    }

    @Test
public void webTables() throws InterruptedException {
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.xpath("//span[@id='edit-record-1']//*[@stroke='currentColor']")).click();
        Thread.sleep(2000);
        WebElement edit= driver.findElement(By.id("department"));
        edit.sendKeys(Keys.CONTROL+"a", Keys.BACK_SPACE);
        Thread.sleep(2000);
        edit.sendKeys("Computer");
        driver.findElement(By.id("submit")).click();
    }
@Test
    public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i=0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num["+i+"] "+ cell.getText());
            }
        }
    }
    @Test
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }



    @After
    public void  closeBrowser(){
        driver.quit();
    }
}
