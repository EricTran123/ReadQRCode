package CreateShortLinks;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateLinks  extends BaseTest{
    @DataProvider(name = "ShortLink")
    public Object[][] Data_InitialValue() {
        return getDataForProvider("Result.xlsx", "Result");
    }
    /**
     * TestCase's Name: Verify that all initial values of fields in Delivery Operator page are correct.
     * @DataProvider(name = "ShortLink")
     * */
    @Test(dataProvider = "ShortLink", priority = 1, description = "Verify that all initial values of fields in Delivery Operator page", enabled = true)
    public void verify_InitialValue(Shortlink shortlink) throws InterruptedException {
       super.getshortLink(shortlink);
    }
}
