package entities.hicodefirst.billingsystem;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;


@Entity
@DiscriminatorColumn(name = "cc")
public class CreditCard extends BillingDetail{
    private String cardType;
    private int expirationMouth;
    private int expirationYear;

    public CreditCard() {
    }

    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    @Column(name = "expiration_mount")
    public int getExpirationMouth() {
        return expirationMouth;
    }

    public void setExpirationMouth(int expirationMouth) {
        this.expirationMouth = expirationMouth;
    }
    @Column(name = "expiration_year")
    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int year) {
        this.expirationYear = year;
    }
}
