//package com.ruets_er.KHUJEDEI.daoImplementation;
//
//import com.ruets_er.KHUJEDEI.dao.CatagoryDao;
//import com.ruets_er.KHUJEDEI.model.Catagory;
//import org.hibernate.Session;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@Repository
//public class CatagoryDaoImpl implements CatagoryDao {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public List<Catagory> findall() {
//        Session currentSession = entityManager.unwrap(Session.class);
//        Query<Catagory> query = currentSession.createQuery
//                ("Select c.id,c.catagoryName from Catagory c");
//        List<Catagory> list = query.getResultList();
//        return list;
//    }
//}
