package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StartingPage {
    private SelenideElement headingStart = $("h2.heading");
    private SelenideElement paymentButton = $$(".button").find(exactText("Купить"));
    private SelenideElement creditButton = $$(".button").find(exactText("Купить в кредит"));

    public PaymentPage payment() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage paymentOnCredit() {
        creditButton.click();
        return new CreditPage();
    }
}
