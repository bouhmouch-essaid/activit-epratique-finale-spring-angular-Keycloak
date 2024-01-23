package com.keepllly.auth.domain.embedded;

public class CreditCard {

    private String cardNumber;

    private String cardHolderName;

    private String expiryDate;

    private String cvv;

    public CreditCard() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((cardHolderName == null) ? 0 : cardHolderName.hashCode());
        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
        result = prime * result + ((cvv == null) ? 0 : cvv.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreditCard other = (CreditCard) obj;
        if (cardNumber == null) {
            if (other.cardNumber != null)
                return false;
        } else if (!cardNumber.equals(other.cardNumber))
            return false;
        if (cardHolderName == null) {
            if (other.cardHolderName != null)
                return false;
        } else if (!cardHolderName.equals(other.cardHolderName))
            return false;
        if (expiryDate == null) {
            if (other.expiryDate != null)
                return false;
        } else if (!expiryDate.equals(other.expiryDate))
            return false;
        if (cvv == null) {
            if (other.cvv != null)
                return false;
        } else if (!cvv.equals(other.cvv))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CreditCard [cardNumber=" + cardNumber + ", cardHolderName=" + cardHolderName + ", expiryDate="
                + expiryDate + ", cvv=" + cvv + "]";
    }

    
}
