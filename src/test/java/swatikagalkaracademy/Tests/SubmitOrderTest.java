package swatikagalkaracademy.Tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import swatikagalkaracademy.TestComponents.BaseTest;
import swatikagalkaracademy.pageobjects.AbstractComponent;
import swatikagalkaracademy.pageobjects.CartPage;
import swatikagalkaracademy.pageobjects.CheckoutPage;
import swatikagalkaracademy.pageobjects.ConfirmationPage;
import swatikagalkaracademy.pageobjects.LandingPage;
import swatikagalkaracademy.pageobjects.OrderPage;
import swatikagalkaracademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest
{
	String productName="ZARA COAT 3";
	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException
	{
		
		ProductCatalogue productCatalogue=landingPage.loginApplication(input.get("email"),input.get("password"));
		List<WebElement> products=productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage=productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage =cartPage.goToCheckOut();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage=checkoutPage.submitOrder();
		String confirmMessage= confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
	}
	@Test(dependsOnMethods={"submitOrder"})
	public void OrderHistory()
	{
		ProductCatalogue productCatalogue=landingPage.loginApplication("anshika@gmail.com","Iamking@000");
		OrderPage orderPage=productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}
	
	//1.driving data from data provider
	//@DataProvider
	//public Object[][] getData()
	//{
		//return new Object[][] 
				//{{"anshika@gmail.com","Iamking@000","ZARA COAT 3"},{"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL"}};
	//}
	
	//2.Driving data using hashmap
	
	//@DataProvider
	//public Object[][] getData()
	//{
		//HashMap<String,String> map = new HashMap<String,String>();
		//map.put("email", "anshika@gmail.com");
		//map.put("password", "Iamking@000");
		//map.put("product", "ZARA COAT 3");
		
		//HashMap<String,String> map1 = new HashMap<String,String>();
		//map1.put("email", "shetty@gmail.com");
		//map1.put("password", "Iamking@000");
		//map1.put("product", "ADIDAS ORIGINAL");
		
		//return new Object[][] {{map},{map1}};
	//}
	
	//3.Reading data from JSON
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,String>> data = 
				getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//swatikagalkaracademy//data//PurchaseOrder.json");
				return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}
