package test;

import date.CardInfo;
import date.DbUtils;
import org.junit.jupiter.api.*;
import page.StartingPage;

import static com.codeborne.selenide.Selenide.open;
import static date.DataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {

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
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.successfulPaymentCreditCard();
            String actual = DbUtils.getStatusCredit();
            assertEquals("APPROVED", actual);
        }

        @Test
        void shouldPaymentWithApprovedCardExpires() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(0), getYearCard(0), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.successfulPaymentCreditCard();
            String actual = DbUtils.getStatusCredit();
            assertEquals("APPROVED", actual);
        }

        @Test
        void shouldPaymentWithDeclinedCard() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getSecondCardNumber(), getMonthCard(0), getYearCard(1), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.invalidPaymentCreditCard();
            String actual = DbUtils.getStatusCredit();
            assertEquals("DECLINED", actual);
        }

        @Test
        void shouldPaymentWithDeclinedCardExpires() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getSecondCardNumber(), getMonthCard(0), getYearCard(0), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.invalidPaymentCreditCard();
            String actual = DbUtils.getStatusCredit();
            assertEquals("DECLINED", actual);
        }

        @Test
        void shouldPaymentWithInvalidCardNumber() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getInvalidCardNumber(), getMonthCard(2), getYearCard(1), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.invalidPaymentCreditCard();
        }

        @Test
        void shouldPaymentWithInvalidCardNumberShort() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getInvalidShortCardNumber(), getMonthCard(2), getYearCard(1), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidFormat();
        }

        @Test
        void shouldPaymentExpiredCard() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(0), getYearCard(-1), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkCardExpired();
        }

        @Test
        void shouldPaymentIncorrectCardExpirationDate() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(-1), getYearCard(0), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidCardValidityPeriod();
        }

        @Test
        void shouldPaymentCardValidMoreThanFiveYears() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(1), getYearCard(6), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidCardValidityPeriod();
        }

        @Test
        void shouldPaymentCardInvalidYearOneDigit() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(2), getInvalidYearCard(), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidFormat();
        }

        @Test
        void shouldPaymentInvalidOwnerCard() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(1), getYearCard(3), getInvalidOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidOwner();
        }

        @Test
        void shouldPaymentInvalidOwnerCardInCyrillic() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                    getInvalidOwnerCardCyrillic(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.incorrectOwner();
        }

        @Test
        void shouldPaymentInvalidOwnerCardWithNumbers() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                    getInvalidOwnerCardWithNumbers(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.incorrectOwner();
        }

        @Test
        void shouldPaymentInvalidOwnerCardOneLetterName() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(1), getYearCard(3),
                    getInvalidOwnerCardOneLetterName(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.incorrectOwner();
        }

        @Test
        void shouldPaymentCardInvalidCvc() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(1), getYearCard(2), getOwnerCard(), getInvalidCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidFormat();
        }


        @Test
        void shouldPaymentCardInvalidMonthOneDigit() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getInvalidMonthCardOneDigit(), getYearCard(2), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidFormat();
        }

        @Test
        void shouldPaymentCardInvalidMonthInvalidPeriod() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getInvalidMonthCardInvalidPeriod(), getYearCard(2), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidCardValidityPeriod();
        }

        @Test
        void shouldPaymentCardInvalidMonth() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getInvalidMonthCard(), getYearCard(2), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidCardValidityPeriod();
        }

        @Test
        void shouldPaymentEmptyFieldNumberCard() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    null, getMonthCard(1), getYearCard(2), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidFormat();
        }

        @Test
        void shouldPaymentEmptyFieldMonth() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), null, getYearCard(2), getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidFormat();
        }

        @Test
        void shouldPaymentEmptyFieldYears() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(2), null, getOwnerCard(), getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkInvalidFormat();
        }

        @Test
        void shouldPaymentEmptyFieldOwner() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(2), getYearCard(3), null, getCvc());
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkEmptyField();
        }

        @Test
        void shouldPaymentEmptyFieldCvc() {
            var startPage = new StartingPage();
            CardInfo card = new CardInfo(
                    getFirstCardNumber(), getMonthCard(2), getYearCard(3), getOwnerCard(), null);
            var creditPage = startPage.paymentOnCredit();
            creditPage.getFillCardDetails(card);
            creditPage.checkEmptyField();
        }
}
