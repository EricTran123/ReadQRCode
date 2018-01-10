package CreateShortLinks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public WebDriver driver;
    public Excelbuilder excelbuilder = new Excelbuilder();
    public Boolean result = false;
    public int rowIndex = 1;
    public String excelFile = System.getProperty("user.dir") + File.separator + "Result.xlsx";
    @BeforeMethod
    public void beforeMethod() {
        System.out.println( System.getProperty("user.dir") + File.separator );
        System.setProperty("webdriver.chrome.driver", "BrowserDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Delete all cookies.
        driver.manage().deleteAllCookies();
        //It sets the amount of time Selenium to wait for a page load to complete before throwing an error.
        // If the timeout is negative, page loads can be indefinite.
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
    }


    @AfterMethod
    public void afterMethod() {
        if (driver != null){
            driver.close();
        }
        rowIndex ++;

    }

    public Object[][] getDataForProvider(String excelFile, String sheetName) {

        // Get list dataTest from excel file
        ArrayList<Shortlink> data = excelbuilder.createDataTest(excelFile, sheetName);

        // Put data to two-dimensional array for data provider
        Object[][] myData = new Object[data.size()][1];
        for (int iz = 0; iz < data.size(); iz++) {
            myData[iz] = new Object[]{data.get(iz)};
        }
        return myData;
    }
    public void getshortLink(Shortlink shortlink) throws InterruptedException {
            driver.get("http://order.sg/user/login");
            //Input username
            driver.findElement(By.xpath("//input[@type='text'][@placeholder='Enter email']")).sendKeys("admin");
              //Input password
             driver.findElement(By.xpath("//input[@type='password'][@placeholder='Password']")).sendKeys("Getzsupport");
             driver.findElement(By.xpath("//button[text()='Login']")).click();

             //Input original link
            WebElement webElement = driver.findElement(By.xpath("//input[@type='text'][@placeholder='Paste a long url']"));
             webElement.sendKeys(shortlink.getOriginalLink());
              List<WebElement> allLinks = driver.findElements(By.tagName("a"));
              for (WebElement link: allLinks){
                  if (link.getText().equals("Advanced Options")){
                      link.click();
                  }
              }
            // Input Alias
            driver.findElement(By.xpath("//input[@type='text'][@placeholder='Type your custom alias here']")).sendKeys(shortlink.getAlias());

        //Click Shorten button
            driver.findElement(By.xpath("//*[@id=\"shortenurl\"]")).click();
            Thread.sleep(12000);
            if (driver.getPageSource().contains("http://order.sg/" + shortlink.getAlias())){
                String[]result1 = driver.getPageSource().split("http://order.sg/" + shortlink.getAlias());
                if (result1.length ==7){
                    result = true;
                    shortlink.setShortLink("http://order.sg/" + shortlink.getAlias());
                    try {
                        ExcelUtil.writeResult(excelFile,"Result",rowIndex,shortlink.getShortLink());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    result = false;
                }
            }
             Assert.assertTrue(result);



    }


}
