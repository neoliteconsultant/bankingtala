package co.tala.bank.persistence;

import javax.annotation.Resource;
import co.tala.bank.persistence.entities.DepositLog;
import co.tala.bank.util.DateFormatter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persistence methods for manipulating {@link DepositLog}.
 * <p>
 * Copyright (c) Tala Ltd., July 27, 2017
 *
 * author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@Repository
@Transactional
public class DepositLogDAO {

    @Resource
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     *
     */
    public DepositLogDAO() {
    }

    /**
     * Log/Add a deposit transaction.
     *
     * @param depositlog
     * @return <code>true</code> if transaction was logged successfully false,
     * otherwise.
     */
    public boolean logTransaction(DepositLog depositlog) {
        boolean isAdded = true;
        try {
            Session session = sessionFactory.openSession();
            session.save(depositlog);
            session.flush();

            
        } catch (Exception e) {
            //logger.error(e);
            isAdded = false;
            System.out.println(e.getMessage());

        }

        return isAdded;
    }

    /**
     * Get the count/frequency of deposits made by this account holder for the
     * specified date.
     *
     * @param accountNumber account holder
     * @param transactionDate date
     * @return a count of the number of transactions.
     */
    public int getTransactionCount(String accountNumber, LocalDate transactionDate) {
        Session session = sessionFactory.getCurrentSession();
        int transactionCount = 0;
        try {

            LocalDate startDate = transactionDate;
            LocalDate endDate = startDate.plus(1, ChronoUnit.DAYS);
            
            //https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html

//            Date startDate = DateFormatter.formatDate(new Date());
//
//            DateTime dt = new DateTime(startDate);
//            Date endDate = dt.plusDays(1).toDate();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<DepositLog> query = builder.createQuery(DepositLog.class);
            Root<DepositLog> root = query.from(DepositLog.class);
            query.select(root);
            query.where( builder.equal( root.get("accountNumber"), accountNumber) );
            //query.where(builder.between(root.get(DepositLog_.transactionDate)), startDate, endDate);
           
            //criteria.where(Restrictions.like("accountNumber", accountNumber));
            //q.where(Restrictions.between("transactionDate", startDate, endDate));
          transactionCount = session.createQuery(query).getResultList().size();

        } catch (Exception e) {
            logger.equals(e);
        }
        return transactionCount;
    }

    /**
     * Get the value of deposit transactions made by this account holder for the
     * specified date.
     *
     * @param accountNumber account holder
     * @param transactionDate date
     * @return value of transactions for the specified date.
     */
    public double getTransactionAmount1(String accountNumber, Date transactionDate) {
        Session session = sessionFactory.getCurrentSession();
        double transactionTotal = 0;
        try {

            Date startDate = DateFormatter.formatDate(transactionDate);

            DateTime dt = new DateTime(startDate);
            Date endDate = dt.plusDays(1).toDate();

            Criteria criteria = session.createCriteria(DepositLog.class);
            criteria.setProjection(Projections.property("amount"));
            criteria.add(Restrictions.like("accountNumber", accountNumber));
            criteria.add(Restrictions.between("transactionDate", startDate, endDate));
            List<Double> transactionAmountList = criteria.list();

            for (Double transactionAmount : transactionAmountList) {
                transactionTotal += transactionAmount;
            }
            //transactionTotal = transactionAmountList.stream().map((transactionAmount) -> transactionAmount).reduce(transactionTotal, (accumulator, _item) -> accumulator + _item);
        } catch (Exception e) {
            logger.equals(e);
        }
        return transactionTotal;
    }

    /**
     * Get the value of deposit transactions made by this account holder for the
     * specified date.
     *
     * @param accountNumber account holder
     * @param transactionDate date
     * @return value of transactions for the specified date.
     */
    public double getTransactionAmount(String accountNumber, LocalDate transactionDate) {
        Session session = sessionFactory.getCurrentSession();
        double transactionTotal = 0;
        try {

//            LocalDate startDate = transactionDate;            
//            LocalDate endDate = transactionDate.plus(1, ChronoUnit.DAYS);
            Date startDate = DateFormatter.formatDate(new Date());

            DateTime dt = new DateTime(startDate);
            Date endDate = dt.plusDays(1).toDate();

            Criteria criteria = session.createCriteria(DepositLog.class);
            criteria.setProjection(Projections.property("amount"));
            criteria.add(Restrictions.like("accountNumber", accountNumber));
            criteria.add(Restrictions.between("transactionDate", startDate, endDate));
            List<Double> transactionAmountList = criteria.list();

            for (Double transactionAmount : transactionAmountList) {
                transactionTotal += transactionAmount;
            }
            //transactionTotal = transactionAmountList.stream().map((transactionAmount) -> transactionAmount).reduce(transactionTotal, (accumulator, _item) -> accumulator + _item);
        } catch (Exception e) {
            logger.equals(e);
        }
        return transactionTotal;
    }
}
