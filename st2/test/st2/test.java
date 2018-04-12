package st2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;

import com.csvreader.CsvReader;


import java.util.List;

@RunWith(Parameterized.class)
public class test {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String id,pwd,github;
  
  public test(String id,String github)
  {
	  this.id = id;
	  
	  this.github = github;
	  this.pwd = id.substring(4);
	  System.out.println(this.id+" "+this.github+" "+this.pwd);
	  
  }
  
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://psych.liebes.top";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl+"/st");
  }
  
  @Parameters
  public static Collection<Object[]> getData() throws Exception
  {
	  Object[][] obj = new Object[117][];
	  CsvReader r = new CsvReader("C:\\Users\\imac\\Desktop\\2018\\»Ìº˛≤‚ ‘\\input.csv",',',
			  Charset.forName("GBK"));
	  //System.out.println(r);
	  int count = 0;
	  r.readHeaders();
	  while(r.readRecord())
	  {
		  obj[count] = new Object[]{r.get("id"), r.get("git") };
		  count++;
		  //System.out.println(r.get("git"));
	  }
	  System.out.println(count);
	  return Arrays.asList(obj);
  }
  

  @Test
  public void test10() throws Exception {
   
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys(this.id);
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys(this.pwd);
    driver.findElement(By.id("submitButton")).click();
    //driver.findElement(By.cssSelector("p.login-box-msg")).click();
    
    assertEquals(this.github, driver.findElement(By.cssSelector("p.login-box-msg")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
