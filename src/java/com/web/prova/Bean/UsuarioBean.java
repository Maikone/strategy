/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.prova.Bean;

import com.web.prova.Dao.UsuarioDao;
import com.web.prova.Entidade.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import util.Bank;

/**
 *
 * @author maikone
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {

    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuario = null;
    private Usuario usuarioRece = null;
    public static double saldoIn;
    public static String cpfSession;
    public static String cpfIn;
    public static String cpfInTRansf;
    public static String tipoEx;
    public static String NomeEx;
    public static double valorEx;
    
  
    
    public UsuarioBean() {
        usuario = new Usuario();  
    }

    public String login() {
        UsuarioDao usuDao = new UsuarioDao();
        Bank dia = new Bank();
        Usuario us;
        String resultado;
        try {
            us = usuDao.BuscaLogin(this.usuario);

            if (us != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", us);

                FacesContext fc = FacesContext.getCurrentInstance();
                Usuario usuarioLogado = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");
                cpfSession = usuarioLogado.getCpf();
                resultado = "principal.jsf";

            } else {
                resultado = "index.jsf";

                dia.showMessageLogin();
            }
        } catch (Exception e) {
            throw e;
        }
        return resultado;
    }

    public void salvar() {
        UsuarioDao usuarioDAO = new UsuarioDao();
        usuario.setSaldo(0.0);
        this.usuario = usuarioDAO.addUsuario(this.usuario);
    }

    public void buscarPorId() {
        UsuarioDao usuarioDAO = new UsuarioDao();
        this.usuario = usuarioDAO.buscarPorId(this.usuario.getId());
    }

    public void buscarPorCpf() {
        UsuarioDao usuarioDAO = new UsuarioDao();
        this.usuario = usuarioDAO.buscarPorCpf(this.usuario.getCpf());
    }
    
    public void preenche() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Usuario usuarioLogado = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");
        Integer id = usuarioLogado.getId();
        UsuarioDao usuarioDAO = new UsuarioDao();
        this.usuario = usuarioDAO.buscarPorId(id);
    }

    public void transferir() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Usuario usuarioLogado = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");
        cpfSession = usuarioLogado.getCpf();
        saldoIn = usuario.getSaldo();
        Bank calc = new Bank();
        UsuarioDao usuarioDAO = new UsuarioDao();
        usuarioDAO.updateSaldo(cpfSession, calc.CalcTranferRetirar());
        cpfInTRansf = usuario.getCpf();
        usuarioDAO.updateSaldo(cpfInTRansf, calc.CalcTranferDepo());
        tipoEx = "Transferencia";
        valorEx = saldoIn;        
        NomeEx = usuario.getNome();        
        ExtratoBean extBean = new ExtratoBean();
        extBean.salvarExtrato();
        extBean.listarExtrato();
        Bank dia = new Bank();
        dia.saveMessage();
    }

   
    
    public void depositar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Usuario usuarioLogado = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");     
        cpfSession = usuarioLogado.getCpf();
        saldoIn = usuario.getSaldo();
        Bank calc = new Bank();
        calc.CalcDeposito();
        double resultDep = calc.getTotalDeposito();
        UsuarioDao usuarioDAO = new UsuarioDao();
        usuarioDAO.updateSaldo(cpfSession, resultDep);
        usuario = new Usuario();
        tipoEx = "Deposito";
        valorEx = saldoIn;
        NomeEx = usuarioLogado.getNome();
        ExtratoBean extBean = new ExtratoBean();
        extBean.salvarExtrato();
        extBean.listarExtrato();
        Bank dia = new Bank();
        dia.saveMessage();
    }

    public String sair() {
        //remover:
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuario");
        //recriar:
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", null);

        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sessao.invalidate();

        return "index"; 

    }

    public void saque() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Usuario usuarioLogado = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");
        cpfSession = usuarioLogado.getCpf();
        saldoIn = usuario.getSaldo();
        Bank calc = new Bank();
        calc.CalcSaque();
        double resultSaque = calc.getTotalSaque();
        UsuarioDao usuarioDAO = new UsuarioDao();
        usuarioDAO.updateSaldo(cpfSession, resultSaque);
        tipoEx = "Saque";
        valorEx = saldoIn;
        NomeEx = usuarioLogado.getNome();
        ExtratoBean extBean = new ExtratoBean();
        extBean.salvarExtrato();
        extBean.listarExtrato();
        Bank dia = new Bank();
        dia.saveMessage();
    }

    public static double getSaldoIn() {
        return saldoIn;
    }

    public static void setSaldoIn(double saldoIn) {
        UsuarioBean.saldoIn = saldoIn;
    }

    public void listar() {
        UsuarioDao UsuarioDao = new UsuarioDao();
        this.usuarios = UsuarioDao.listUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    private UsuarioDao usuarioDao = new UsuarioDao();

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public Usuario getUsuarioRece() {
        return usuarioRece;
    }

    public void setUsuarioRece(Usuario usuarioRece) {
        this.usuarioRece = usuarioRece;
    }

    public static String getCpfIn() {
        return cpfIn;
    }

    public static void setCpfIn(String cpfIn) {
        UsuarioBean.cpfIn = cpfIn;
    }

    public static String getCpfSession() {
        return cpfSession;
    }

    public static void setCpfSession(String cpfSession) {
        UsuarioBean.cpfSession = cpfSession;
    }

    public static String getCpfInTRansf() {
        return cpfInTRansf;
    }

    public static void setCpfInTRansf(String cpfInTRansf) {
        UsuarioBean.cpfInTRansf = cpfInTRansf;
    }

}
