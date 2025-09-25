package com.org.tests;

import com.org.base.Base;
import com.org.pages.HomePage;
import com.org.pages.RegisterPage;
import com.org.pages.RegisterResultPage;
import com.org.utility.DataGetter;
import com.org.utils.LoggerHelper;
import com.org.utils.TypeOfValidation;
import com.org.utils.configReader;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;


@Listeners(com.org.utility.TestListener.class)
public class RegisterTest extends Base {
    HomePage homePage;
    RegisterPage registerPage;
    RegisterResultPage register_result_page;
    private static final Logger logger = LoggerHelper.getLogger(RegisterTest.class);

    //Run this before every test case to initialise driver
    @BeforeMethod
    public void Setup(){
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        Base.openApplication(configReader.getProperty("browser"),configReader.getProperty("baseUrl"));
        homePage = new HomePage();
        registerPage = new RegisterPage();
        register_result_page = new RegisterResultPage();
    }

    @Test(dataProvider = "RegisterData", dataProviderClass = DataGetter.class)
    public void testcase001Registartionwithmailid(String gender, String firstname, String lastname, String emailId, String password, String confirmPassword, TypeOfValidation type){
        homePage.headerLink("Register");
        registerPage.selectUserGender(gender);
        registerPage.fillFirstNameField(firstname);
        registerPage.fillLastNameField(lastname);
        registerPage.fillEmailIdField(emailId);
        registerPage.fillPasswordField(password);
        registerPage.fillConfirmPasswordField(confirmPassword);



        switch (type) {
            case VALID:
                register_result_page = registerPage.clickOnRegisterButton();
                Assert.assertTrue(register_result_page.isMessageDisplay(),
                        "Registration message was not displayed!");
                break;

            case INVALID:
                registerPage.clickOnRegisterButton();
                String errorMsg1 = registerPage.getErrorMessage(TypeOfValidation.INVALID);
                Assert.assertEquals(errorMsg1, configReader.getProperty("wrong_emailtext"),
                        "Invalid email error mismatch");
                break;

            case DUPLICATE:
                registerPage.clickOnRegisterButton();
                String errorMsg2 = registerPage.getErrorMessage(TypeOfValidation.DUPLICATE);
                Assert.assertEquals(errorMsg2, configReader.getProperty("duplicate_emailText"),
                        "Duplicate email error mismatch");
                break;
        }
    }

    @AfterMethod
    public void closedBrowser(){
        Base.closeApplication();
        logger.info("Browser closed successfully.");
    }
}
