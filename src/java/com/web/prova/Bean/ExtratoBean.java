/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.prova.Bean;

import com.web.prova.Dao.ExtratoDao;
import com.web.prova.Entidade.Extrato;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

/**
 *
 * @author maikone
 */
public class ExtratoBean {

    private List<Extrato> extratos = new ArrayList<>();
    private Extrato extrato = null;

    public ExtratoBean() {
        extrato = new Extrato();
    }

    public void salvarExtrato() {
        java.util.Date d = new Date();
        extrato.setBeneficiado(UsuarioBean.NomeEx);
        extrato.setTipo(UsuarioBean.tipoEx);
        extrato.setValor(UsuarioBean.valorEx);
        extrato.setCpfUser(UsuarioBean.cpfSession);
        extrato.setData(d);
        ExtratoDao extratoDao = new ExtratoDao();
        this.extrato = extratoDao.addExtrato(this.extrato);
        this.listarExtrato();
    }

    public void listarExtrato() {
        ExtratoDao ExtratoDao = new ExtratoDao();
        this.extratos = ExtratoDao.listExtrato(UsuarioBean.cpfSession);
    }

    public List<Extrato> getExtratos() {
        return extratos;
    }

    public void setExtratos(List<Extrato> extratos) {
        this.extratos = extratos;
    }

    public Extrato getExtrato() {
        return extrato;
    }

    public void setExtrato(Extrato extrato) {
        this.extrato = extrato;
    }

}
