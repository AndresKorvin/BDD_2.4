package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
      open("http://localhost:9999");
      var loginPage = new LoginPage();
      var authInfo = DataHelper.getAuthInfo();
      var verificationPage = loginPage.validLogin(authInfo);
      var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      dashboardPage = verificationPage.validVerify(verificationCode);
    }

  @Test
  void shouldTransferMoneyFromFirstToSecond() {
      var firstCard = DataHelper.getFirstCardInfo();
      var secondCard = DataHelper.getSecondCardInfo();
      var firstCardBalance = dashboardPage.getCardBalance(firstCard);
      var secondCardBalance = dashboardPage.getCardBalance(secondCard);
      var amount = DataHelper.generateValidAmount(firstCardBalance);
      var expectedBalanceFirstCard = firstCardBalance - amount;
      var expectedBalanceSecondCard = secondCardBalance + amount;
      var transferPage = dashboardPage.selectCardToTransfer(secondCard);
      dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount),firstCard);
      var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
      var actualSecondCardBalance =dashboardPage.getCardBalance(secondCard);
      assertEquals(expectedBalanceFirstCard,actualFirstCardBalance);
      assertEquals(expectedBalanceSecondCard,actualSecondCardBalance);
  }

  @Test
  void shouldGetErrorMessageIfTransferMoreBalance() {
    var firstCard = DataHelper.getFirstCardInfo();
    var secondCard = DataHelper.getSecondCardInfo();
    var firstCardBalance = dashboardPage.getCardBalance(firstCard);
    var secondCardBalance = dashboardPage.getCardBalance(secondCard);
    var amount = DataHelper.generateInvalidValidAmount(secondCardBalance);
    var transferPage = dashboardPage.selectCardToTransfer(firstCard);
    transferPage.makeTransfer(String.valueOf(amount),secondCard);
    transferPage.findErrorMessage("Сумма перевода, превышает остаток на карте");
    var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
    var actualSecondCardBalance =dashboardPage.getCardBalance(secondCard);
    assertEquals(firstCardBalance,actualFirstCardBalance);
    assertEquals(secondCardBalance,actualSecondCardBalance);
  }
}
