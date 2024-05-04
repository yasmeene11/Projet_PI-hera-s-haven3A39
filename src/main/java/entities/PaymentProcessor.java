package entities;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;

public class PaymentProcessor {
    public String createPaymentToken(String cardNumber, int expMonth, int expYear, String cvc) throws StripeException {
        Stripe.apiKey = "sk_test_your_stripe_api_key";
        TokenCreateParams params = TokenCreateParams.builder()
                .setCard(TokenCreateParams.Card.builder()
                        .setNumber(cardNumber)
                        .setExpMonth(String.valueOf(expMonth))
                        .setExpYear(String.valueOf(expYear))
                        .setCvc(cvc)
                        .build())
                .build();
        Token token = Token.create(params);
        return token.getId();
    }
}
