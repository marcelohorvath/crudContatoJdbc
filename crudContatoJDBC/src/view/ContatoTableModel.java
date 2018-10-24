package view;

import entidade.Contato;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ContatoTableModel extends AbstractTableModel {

    private List<Contato> contatos = new ArrayList();

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    @Override
    public int getRowCount() {
        return contatos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String valor = "";
        Contato umContato = contatos.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                valor = umContato.getId().toString();
                break;
            }
            case 1: {
                valor = umContato.getNome();
                break;
            }
            case 2: {                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                valor = sdf.format(umContato.getDataNascimento());
                break;
            }
            case 3: {
                if (umContato.getAtivo()) {
                    valor = "ATIVO";
                } else {
                    valor = "INATIVO";
                }
                break;
            }
        }
        return valor;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: {
                return "ID";
            }
            case 1: {
                return "NOME";
            }
            case 2: {
                return "NASCIMENTO";
            }
            case 3: {
                return "ATIVO";
            }
            default: {
                return "";
            }

        }
    }

}
