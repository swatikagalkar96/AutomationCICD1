package swatikagalkaracademy.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import swatikagalkaracademy.TestComponents.BaseTest;
import swatikagalkaracademy.pageobjects.AbstractComponent;
import swatikagalkaracademy.pageobjects.CartPage;
import swatikagalkaracademy.pageobjects.CheckoutPage;
import swatikagalkaracademy.pageobjects.ConfirmationPage;
import swatikagalkaracademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest
{
	@Test(groups= {"ErrorHandling"})
	public void LoginErrorValidation() throws IOException
	{
		String productName="ZARA COAT 3";
		landingPage.loginApplication("anshika@gmail.com", "Iamking@00");
		Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() throws IOException
	{
		String productName="ZARA COAT 3";
		ProductCatalogue productCatalogue=landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
		List<WebElement> products=productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage=productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}
	
	
}
