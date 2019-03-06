/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.web.prova.Bean.UsuarioBean;
import com.web.prova.Dao.UsuarioDao;
import com.web.prova.Entidade.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author maikone
 */
@ManagedBean
public class Bank {

    private Usuario usuario = null;
    public Double totalDeposito;
    public Double totalSaque;
    public Double pt1Transfer;
    public Double pt2Transfer;

    public void showMessageLogin() {
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Cpf ou senha incorretos."));
    }

    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso !", "Ação Efetuada com Sucesso !: "));

    }

    public double CalcDeposito() {
        UsuarioDao userDao = new UsuarioDao();
        String cpf = UsuarioBean.cpfSession;
        this.usuario = userDao.buscarPorCpf(cpf);
        double saldo = usuario.getSaldo();
        totalDeposito = UsuarioBean.saldoIn + saldo;
        return totalDeposito;
    }

    public double CalcSaque() {
        UsuarioDao userDao = new UsuarioDao();
        String cpf = UsuarioBean.cpfSession;
        this.usuario = userDao.buscarPorCpf(cpf);
        double saldo = usuario.getSaldo();
        totalSaque = saldo - UsuarioBean.saldoIn;
        return totalSaque;
    }

    public double CalcTranferRetirar() {
        UsuarioDao userDao = new UsuarioDao();
        String cpf = UsuarioBean.cpfSession;
        this.usuario = userDao.buscarPorCpf(cpf);
        double saldo = usuario.getSaldo();
        pt1Transfer = saldo - UsuarioBean.saldoIn;
        System.out.println("Id sessao" + cpf);
        return pt1Transfer;

    }

    public double CalcTranferDepo() {
        UsuarioDao userDao = new UsuarioDao();
        String cpf = UsuarioBean.cpfInTRansf;
        this.usuario = userDao.buscarPorCpf(cpf);
        double saldo = usuario.getSaldo();
        pt2Transfer = saldo + UsuarioBean.saldoIn;       
        return pt2Transfer;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getTotalDeposito() {
        return totalDeposito;
    }

    public void setTotalDeposito(Double totalDeposito) {
        this.totalDeposito = totalDeposito;
    }

    public Double getTotalSaque() {
        return totalSaque;
    }

    public void setTotalSaque(Double totalSaque) {
        this.totalSaque = totalSaque;
    }

    public double getPt1Transfer() {
        return pt1Transfer;
    }

    public void setPt1Transfer(double pt1Transfer) {
        this.pt1Transfer = pt1Transfer;
    }

}
