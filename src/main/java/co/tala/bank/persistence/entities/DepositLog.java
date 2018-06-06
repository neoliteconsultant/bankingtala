package co.tala.bank.persistence.entities;



import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A bean/pojo mapping to the deposit_log table. Keeps track of all the deposit
 * transactions performed by an bank account holder.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017.
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Entity
@Table(name="deposit_log"
    ,catalog="taladb"
)
public class DepositLog  implements java.io.Serializable {


     private Integer id;
     private String accountNumber;
     private double amount;
     private LocalDateTime transactionDate;

    public DepositLog() {
    }

    public DepositLog(String accountNumber, double amount, LocalDateTime transactionDate) {
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

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name="transactionDate", nullable=false, length=19)
    public LocalDateTime getTransactionDate() {
        return this.transactionDate;
    }
    
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }




}


