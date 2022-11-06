package pages;

import org.openqa.selenium.WebDriver;

public abstract class CommonLoggedOutPage extends CommonPage{

    /**
     * A constructor
     *
     * @param driver {WebDriver} - WebDriver
     */
    public CommonLoggedOutPage(WebDriver driver) {
        super(driver);
    }

}
