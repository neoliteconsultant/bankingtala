package co.tala.bank.persistence.entities;
// Generated Jul 28, 2017 9:06:03 PM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A bean/pojo mapping to the withdrawal_log table. Keeps track of all the
 * withdrawals performed by an bank account holder.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017.
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Entity
@Table(name="withdrawal_log"
    ,catalog="taladb"
)
public class WithdrawalLog  implements java.io.Serializable {


     private Integer id;
     private String accountNumber;
     private double amount;
     private Date transactionDate;

    public WithdrawalLog() {
    }

    public WithdrawalLog(String accountNumber, double amount, Date transactionDate) {
       this.accountNumber = accountNumber;
       this.amount = amount;
       this.transactionDate = transactionDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="accountNumber", nullable=false, length=15)
    public String getAccountNumber() {
        return this.accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    
    @Column(name="amount", nullable=false, precision=22, scale=0)
    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="transactionDate", nullable=false, length=19)
    public Date getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }




}


