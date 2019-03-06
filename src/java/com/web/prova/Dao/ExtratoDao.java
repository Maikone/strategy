/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.prova.Dao;

import com.web.prova.Entidade.Extrato;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author maikone
 */
public class ExtratoDao {
   
     public Extrato addExtrato(Extrato extrato) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(extrato);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return extrato;
    }

    public List<Extrato> listExtrato(String cpfUser) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Extrato e where e.cpfUser = :cpfUser");
            query.setParameter("cpfUser", cpfUser);
            List extratos = query.list();
            if (extratos != null && extratos.isEmpty()) {
                return null;
            } else {
                return (List<Extrato>) extratos;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Extrato>();
        } finally {
            session.close();
        }
    }

    
    
}
