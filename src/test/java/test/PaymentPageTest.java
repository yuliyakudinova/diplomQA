package test;

import date.CardInfo;
import date.DbUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.StartingPage;

import static com.codeborne.selenide.Selenide.open;
import static date.DataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentPageTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        DbUtils.deleteTables();
    }

    @Test
    void shouldPaymentWithApprovedCard() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(1), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.successfulPaymentDebitCard();
        String actual = DbUtils.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldPaymentWithApprovedCardExpires() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(0), getYearCard(0), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.successfulPaymentDebitCard();
        String actual = DbUtils.getStatusPayment();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldPaymentWithDeclinedCard() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getSecondCardNumber(), getMonthCard(0), getYearCard(1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
        String actual = DbUtils.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldPaymentWithDeclinedCardExpires() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getSecondCardNumber(), getMonthCard(0), getYearCard(0), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
        String actual = DbUtils.getStatusPayment();
        assertEquals("DECLINED", actual);
    }

    @Test
    void shouldPaymentWithInvalidCardNumber() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getInvalidCardNumber(), getMonthCard(2), getYearCard(1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.invalidPaymentDebitCard();
    }

    @Test
    void shouldPaymentWithInvalidCardNumberShort() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getInvalidShortCardNumber(), getMonthCard(2), getYearCard(1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentExpiredCard() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(0), getYearCard(-1), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkCardExpired();
    }

    @Test
    void shouldPaymentIncorrectCardExpirationDate() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(-1), getYearCard(0), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentCardValidMoreThanFiveYears() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(1), getYearCard(6), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentCardInvalidYearOneDigit() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(2), getInvalidYearCard(), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentInvalidOwnerCard() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3), getInvalidOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidOwner();
    }

    @Test
    void shouldPaymentInvalidOwnerCardInCyrillic() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                getInvalidOwnerCardCyrillic(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @Test
    void shouldPaymentInvalidOwnerCardWithNumbers() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                getInvalidOwnerCardWithNumbers(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @Test
    void shouldPaymentInvalidOwnerCardOneLetterName() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                getInvalidOwnerCardOneLetterName(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.incorrectOwner();
    }

    @Test
    void shouldPaymentCardInvalidCvc() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(1), getYearCard(2), getOwnerCard(), getInvalidCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentCardInvalidMonthOneDigit() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getInvalidMonthCardOneDigit(), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentCardInvalidMonthInvalidPeriod() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getInvalidMonthCardInvalidPeriod(), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentCardInvalidMonth() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getInvalidMonthCard(), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidCardValidityPeriod();
    }

    @Test
    void shouldPaymentEmptyFieldNumberCard() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                null, getMonthCard(1), getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldMonth() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), null, getYearCard(2), getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldYears() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(2), null, getOwnerCard(), getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkInvalidFormat();
    }

    @Test
    void shouldPaymentEmptyFieldOwner() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(2), getYearCard(3), null, getCvc());
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkEmptyField();
    }

    @Test
    void shouldPaymentEmptyFieldCvc() {
        var startPage = new StartingPage();
        CardInfo card = new CardInfo(
                getFirstCardNumber(), getMonthCard(2), getYearCard(3), getOwnerCard(), null);
        var paymentPage = startPage.payment();
        paymentPage.getFillCardDetails(card);
        paymentPage.checkEmptyField();
    }

}
