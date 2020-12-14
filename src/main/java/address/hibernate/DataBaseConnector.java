package address.hibernate;

import address.model.dataBaseModel.ContactTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DataBaseConnector {
    private Session session = null;

    public static Session startHibernateSession() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        return sessionFactory.openSession();
    }

    public void addContactsToDataBase(ContactTable contactTable) {
        Session session = startHibernateSession();
        try {
            Transaction transaction = session.beginTransaction();

            session.save(contactTable);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().getRollbackOnly();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /*public static void deleteContactsFromDataBase() {
        try {

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

        }
    }*/

   /* public  getContactsFromDataBase() {
        return;
    }*/
}
