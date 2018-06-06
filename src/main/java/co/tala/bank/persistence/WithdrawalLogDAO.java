package co.tala.bank.persistence;

import javax.annotation.Resource;
import co.tala.bank.persistence.entities.WithdrawalLog;
import co.tala.bank.util.DateFormatter;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persistence methods for manipulating {@link WithdrawalLog}.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Repository
@Transactional
public class WithdrawalLogDAO {

    @Resource
    private SessionFactory sessionFactory;

    /**
     *
     */
    public WithdrawalLogDAO() {
    }

    /**
     * Log/Add a withdrawal transaction.
     *
     * @param withdrawalLog 
     * @return <code>true</code> if transaction was logged successfully false,
     * otherwise.
     */
    public boolean logTransaction(WithdrawalLog withdrawalLog) {
        boolean isAdded = false;
        try {
            Session session = sessionFactory.openSession();
            session.save(withdrawalLog);
            session.flush();

            isAdded = true;
        } catch (Exception e) {
            //logger.error(e);
            e.printStackTrace();
        }

        return isAdded;
    }

    /**
     * Get the count/frequency of withdrawals made by this account holder for
     * the specified date.
     *
     * @param accountNumber account holder
     * @param transactionDate date
     * @return a count of the number of transactions.
     */
    public int getTransactionCount(String accountNumber, Date transactionDate) {
        Session session = sessionFactory.getCurrentSession();
        int transactionCount = 0;
        try {

            Date startDate = DateFormatter.formatDate(transactionDate);

            DateTime dt = new DateTime(startDate);
            Date endDate = dt.plusDays(1).toDate();
            
            System.out.println("");

            Criteria criteria = session.createCriteria(WithdrawalLog.class);
            criteria.add(Restrictions.like("accountNumber", accountNumber));
            criteria.add(Restrictions.between("transactionDate", startDate, endDate));
            transactionCount = criteria.list().size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionCount;
    }

    /**
     * Get the value of withdrawals transactions made by this account holder for
     * the specified date.
     *
     * @param accountNumber account holder
     * @param transactionDate date
     * @return value of transactions for the specified date.
     */
    public double getTransactionAmount(String accountNumber, Date transactionDate) {
        Session session = sessionFactory.getCurrentSession();
        double transactionTotal = 0;
        try {

            Date startDate = DateFormatter.formatDate(transactionDate);

            DateTime dt = new DateTime(startDate);
            Date endDate = dt.plusDays(1).toDate();

            Criteria criteria = session.createCriteria(WithdrawalLog.class);
            criteria.setProjection(Projections.property("amount"));
            criteria.add(Restrictions.like("accountNumber", accountNumber));
            criteria.add(Restrictions.between("transactionDate", startDate, endDate));
            List<Double> transactionAmountList = criteria.list();

            
            for (Double transactionAmount : transactionAmountList) {
            transactionTotal += transactionAmount;
            }
            //transactionTotal = transactionAmountList.stream().map((transactionAmount) -> transactionAmount).reduce(transactionTotal, (accumulator, _item) -> accumulator + _item);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionTotal;
    }

}
