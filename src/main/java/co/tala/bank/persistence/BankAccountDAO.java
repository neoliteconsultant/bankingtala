package co.tala.bank.persistence;

import javax.annotation.Resource;
import co.tala.bank.persistence.entities.BankAccount;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persistence methods for manipulating {@link BankAccount}.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Repository
@Transactional
public class BankAccountDAO {
    

    @Resource
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     *
     */
    public BankAccountDAO() {
    }

    /**
     * Get bank account details corresponding to this account number.
     *
     * @param accountNumber
     * @return {@link BankAccount}
     */
    public BankAccount getAccount(String accountNumber) {
        BankAccount bankAccount = null;
        Session session = sessionFactory.getCurrentSession();
        try {

            Criteria criteria = session.createCriteria(BankAccount.class);
            criteria.add(Restrictions.like("accountNumber", accountNumber));
            bankAccount = (BankAccount) criteria.uniqueResult();

        } catch (Exception e) {
            logger.error(e);
        }
        return bankAccount;
    }

    /**
     * Update bank account details.
     *
     * @param bankAccount updated account details.
     * @return
     */
    public boolean updateAccount(BankAccount bankAccount) {
        boolean isUpdated = false;
        try {
            System.out.println(bankAccount);
            Session session = sessionFactory.getCurrentSession();

            /*
            Session session = sessionFactory.openSession();
            String sql = "UPDATE bank_account SET availableBalance =:availableBalance WHERE accountNumber =:accountNumber";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("availableBalance", bankAccount.getAvailableBalance());
            query.setParameter("accountNumber", bankAccount.getAccountNumber());
            query.executeUpdate();
             */
            session.update(bankAccount);

            isUpdated = true;
        } catch (Exception e) {
            logger.error(e);
          
        }

        return isUpdated;
    }
    
    
    

}
