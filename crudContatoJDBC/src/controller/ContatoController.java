package controller;

import dao.ContatoDao;
import dao.ContatoDaoJdbc;
import entidade.Contato;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import view.ContatoTableModel;
import view.EditContatoView;
import view.ListContatoView;

public class ContatoController implements ActionListener, MouseListener {

    private ListContatoView listView;
    private EditContatoView editView;
    private Contato contatoSelecionado = null;

    public ContatoController(ListContatoView listView, EditContatoView editView) {
        this.listView = listView;
        this.editView = editView;
    }

    public void iniciar() {
        limparCampos();
        listView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editView.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        editView.getTfId().setEnabled(false);
        construirEAssinarEventos();
        listView.setVisible(true);
    }

    private void construirEAssinarEventos() {
        popularTabela();
        //  view.getBtnMostrar().addActionListener(this);
        //  view.getTbContato().addMouseListener(this);
        listView.getBtnExcluir().addActionListener(this);
        listView.getBtnEditar().addActionListener(this);
        editView.getBtnOk().addActionListener(this);
        editView.getBtnCancelar().addActionListener(this);
        listView.getBtnNovo().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        if (botao == listView.getBtnExcluir()) {
            selecionarNaTabela();
            if (contatoSelecionado != null) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Escluir contato", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    ContatoDao dao = new ContatoDaoJdbc();
                    dao.excluir(contatoSelecionado);
                    contatoSelecionado = null;
                    popularTabela();
                } else if (resposta == JOptionPane.NO_OPTION) {
                    //Usuário clicou em não. Executar o código correspondente.
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um contato na tabela");
            }
        }
        if (botao == listView.getBtnEditar()) {
            selecionarNaTabela();
            if (contatoSelecionado != null) {
                limparCampos();
                contatoSelecionadoParaEditView();
                editView.setVisible(true);
            }
        }
        if (botao == editView.getBtnCancelar()) {
            limparCampos();
            contatoSelecionado = null;
            editView.setVisible(false);
            listView.getTbContato().clearSelection();
        }
        if (botao == editView.getBtnOk()) {
            editViewParaContatoSelecionado();
            ContatoDao dao = new ContatoDaoJdbc();
            dao.salvar(contatoSelecionado);
            contatoSelecionado = null;
            limparCampos();
            popularTabela();
            editView.setVisible(false);
        }
        if (botao == listView.getBtnNovo()) {
            contatoSelecionado = new Contato();
            limparCampos();
            editView.setVisible(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int posicaoSelecionada = listView.getTbContato().getSelectedRow();
        if (posicaoSelecionada > -1) {
            TableModel tm = listView.getTbContato().getModel();
            ContatoTableModel ctm = (ContatoTableModel) tm;
            List<Contato> lista = ctm.getContatos();
            Contato c = lista.get(posicaoSelecionada);
//            view.getLblId().setText(c.getId().toString());
//            view.getLblNome().setText(c.getNome());
//            view.getLblDataNascimento().setText(c.getDataNascimento().toString());
//            view.getLblAtivo().setText(c.getAtivo().toString());
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void limparCampos() {
//        view.getLblId().setText("");
//        view.getLblNome().setText("");
//        view.getLblDataNascimento().setText("");
//        view.getLblAtivo().setText("");
        editView.getTfId().setText("");
        editView.getTfNome().setText("");
        editView.getTfDataNascimento().setText("");
        editView.getCbAtivo().setSelected(false);

    }

    private void popularTabela() {
        ContatoDao dao = new ContatoDaoJdbc();
        ContatoTableModel ctm = new ContatoTableModel();
        ctm.setContatos(dao.getAll());
        listView.getTbContato().setModel(ctm);
    }

    private void selecionarNaTabela() {
        int posicaoSelecionada = listView.getTbContato().getSelectedRow();
        if (posicaoSelecionada > -1) {
            contatoSelecionado = ((ContatoTableModel) (listView.getTbContato().getModel())).getContatos().get(posicaoSelecionada);
        } else {
            contatoSelecionado = null;
        }
    }

    private void contatoSelecionadoParaEditView() {
        editView.getTfId().setText(contatoSelecionado.getId().toString());
        editView.getTfNome().setText(contatoSelecionado.getNome());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        editView.getTfDataNascimento().setText(sdf.format(contatoSelecionado.getDataNascimento()));
                
        editView.getCbAtivo().setSelected(contatoSelecionado.getAtivo());
    }

    private void editViewParaContatoSelecionado() {
        //nome
        contatoSelecionado.setNome(editView.getTfNome().getText());
        // data de nascimento
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dataDigitada = sdf.parse(editView.getTfDataNascimento().getText());
            contatoSelecionado.setDataNascimento(dataDigitada);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        // ativo
        contatoSelecionado.setAtivo(editView.getCbAtivo().isSelected());
    }

}
