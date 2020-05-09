package testClasses;

import org.testng.annotations.Test;


public class MyAccountTest {

    @Test(dataProvider = "myAccountDataProvider")
    public void testSuccessfulRegistration(String username, String email, String password){
        //String username =
    }

    @Test(dataProvider = "myAccountDataProvider")
    public void testUnsuccessfulRegistration(){

    }
}
