package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");

  public DashboardPage() {
    heading.shouldBe(visible);
  }

  // к сожалению, разработчики не дали нам удобного селектора, поэтому так
//  private ElementsCollection cards = $$(".list__item div");
  private SelenideElement card1 = $(DataHelper.Cards.getCards().getDataTestIdCard1());
  private SelenideElement card2 = $(DataHelper.Cards.getCards().getDataTestIdCard2());
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";

//  public void Dashboard() {
//  }

  public int getCardBalance1() {
    String text = card1.text();
    return extractBalance(text);
  }

  public int getCardBalance2() {
    String text = card2.text();
    return extractBalance(text);
  }

  private int extractBalance(String text) {
    int start = text.indexOf(balanceStart);
    int finish = text.indexOf(balanceFinish);
    String value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }

  private SelenideElement actionDeposit = $("[data-test-id=action-deposit");
  private SelenideElement amount = $("[data-test-id=amount] [class=input__control]");
  private SelenideElement from = $("[data-test-id=from] [class=input__control]");
  private SelenideElement to = $("[data-test-id=to] [class=input__control]");
  private SelenideElement actiontTansfer = $("[data-test-id=action-transfer]");
  private SelenideElement actionCancel = $("[data-test-id=action-cancel]");

  public void transferToCard1(int sum) {
    $(DataHelper.Cards.getCards().getDataTestIdCard1() + " " + actionDeposit).click();
    $(amount).setValue(String.valueOf(sum));
    $(from).setValue(DataHelper.Cards.getCards().getNumberCard2());
    $(actiontTansfer).click();

  }
}
