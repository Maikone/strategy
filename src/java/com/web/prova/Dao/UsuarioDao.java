/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.prova.Dao;

import com.web.prova.Entidade.Usuario;
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
public class UsuarioDao {

    public Usuario addUsuario(Usuario usuario) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(usuario);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuario;
    }

    public List<Usuario> listUsuario() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuario p");
            List usuarios = query.list();
            if (usuarios != null && usuarios.isEmpty()) {
                return null;
            } else {
                return (List<Usuario>) usuarios;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Usuario>();
        } finally {
            session.close();
        }
    }

    public Usuario buscarPorId(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuario p where p.id = :id");
            query.setParameter("id", id);
            List usuarios = query.list();
            if (usuarios != null && usuarios.isEmpty()) {
                return null;
            } else {
                return (Usuario) usuarios.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    public Usuario buscarPorCpf(String cpf) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuario p where p.cpf = :cpf");
            query.setParameter("cpf", cpf);
            List usuarios = query.list();
            if (usuarios != null && usuarios.isEmpty()) {
                return null;
            } else {
                return (Usuario) usuarios.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void updateUsuario(Usuario usuario) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.saveOrUpdate(usuario);
            session.flush();
        } catch (Exception e) {
        } finally {
            session.close();
        }
    }

    public void updateSaldo(String cpf, double saldo) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction beginTransaction = session.beginTransaction();
            Query query = session.createQuery("update Usuario set saldo = :saldo where cpf = :cpf");
            query.setParameter("cpf", cpf);
            query.setParameter("saldo", saldo);
            query.executeUpdate();
            beginTransaction.commit();
        } catch (Exception e) {
        } finally {

            session.close();
        }
    }

    public void deleteUsuario(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction beginTransaction = session.beginTransaction();
            Query query = session.createQuery("delete from Pessoa p where p.idPessoa = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            beginTransaction.commit();
        } catch (Exception e) {
        } finally {

            session.close();
        }
    }
    private Session session;

    public Usuario BuscaLogin(Usuario usuario) {
        Usuario us = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Usuario where cpf = '" + usuario.getCpf()
                    + "' and senha = '" + usuario.getSenha() + "'";
            Query query = session.createQuery(hql);
            if (!query.list().isEmpty()) {
                us = (Usuario) query.list().get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return us;
    }
    
    

}
