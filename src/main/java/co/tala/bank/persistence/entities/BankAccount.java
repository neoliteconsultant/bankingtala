package co.tala.bank.persistence.entities;
// Generated Jul 28, 2017 9:06:03 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A bean/pojo mapping to the bank_account table. Encaspulates an account holder in a
 * bank.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017.
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Entity
@Table(name = "bank_account", catalog = "taladb"
)
public class BankAccount implements java.io.Serializable {

    private Integer id;
    private String accountName;
    private String accountNumber;
    private double availableBalance;
    private Date creationDate;

    public BankAccount() {
    }

    public BankAccount(String accountName, String accountNumber, double availableBalance, Date creationDate) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.availableBalance = availableBalance;
        this.creationDate = creationDate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "accountName", nullable = false, length = 30)
    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Column(name = "accountNumber", nullable = false, length = 15)
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "availableBalance", nullable = false, precision = 22, scale = 0)
    public double getAvailableBalance() {
        return this.availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false, length = 19)
    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.accountName);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.availableBalance) ^ (Double.doubleToLongBits(this.availableBalance) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BankAccount other = (BankAccount) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.accountName, other.accountName)) {
            return false;
        }
        if (!Objects.equals(this.accountNumber, other.accountNumber)) {
            return false;
        }
        if (Double.doubleToLongBits(this.availableBalance) != Double.doubleToLongBits(other.availableBalance)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "id=" + id + ", accountName=" + accountName + ", accountNumber=" + accountNumber + ", availableBalance=" + availableBalance + ", creationDate=" + creationDate + '}';
    }

}
