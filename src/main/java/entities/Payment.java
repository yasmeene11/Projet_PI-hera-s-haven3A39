package entities;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodCreateParams;

public class Payment {

    public String createPaymentToken(String cardNumber, long expMonth, long expYear, String cvc) throws StripeException {
        // Configurez votre clé d'API Stripe
        Stripe.apiKey = "sk_test_51OpuslIobZsNpOWZGRgL1kyHnKIfMNRKKWk3QgZuP2Ry48sU5UOlPGZnp8dFMJlkfoYvcDGwQxvpK0qDWgydmt6h00pIUO7trA";

        // Créez les détails de la carte pour la méthode de paiement
        PaymentMethodCreateParams.CardDetails cardDetails = PaymentMethodCreateParams.CardDetails.builder()
                .setNumber(cardNumber)
                .setExpMonth(expMonth)
                .setExpYear(expYear)
                .setCvc(cvc)
                .build();

        // Créez une méthode de paiement
        PaymentMethodCreateParams paymentMethodParams = PaymentMethodCreateParams.builder()
                .setType(PaymentMethodCreateParams.Type.CARD)
                .setCard(cardDetails)
                .build();

        // Créez un objet de méthode de paiement
        PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

        // Renvoie l'identifiant du paiement
        return paymentMethod.getId();
    }
}
