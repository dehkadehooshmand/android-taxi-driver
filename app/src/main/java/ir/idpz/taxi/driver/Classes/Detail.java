
package ir.idpz.taxi.driver.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("sheba")
    @Expose
    private String sheba;
    @SerializedName("card_number")
    @Expose
    private Long cardNumber;
    @SerializedName("card_ex_date")
    @Expose
    private String cardExDate;

    public String getSheba() {
        return sheba;
    }

    public void setSheba(String sheba) {
        this.sheba = sheba;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExDate() {
        return cardExDate;
    }

    public void setCardExDate(String cardExDate) {
        this.cardExDate = cardExDate;
    }

}
