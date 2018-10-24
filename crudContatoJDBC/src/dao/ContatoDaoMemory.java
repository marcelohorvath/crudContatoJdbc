package dao;

import entidade.Contato;
import java.util.ArrayList;
import java.util.List;

public class ContatoDaoMemory implements ContatoDao {

    private static List<Contato> lista = new ArrayList();

    @Override
    public void salvar(Contato contato) {
        lista.add(contato);
    }

    @Override
    public void excluir(Contato contato) {
        lista.remove(contato);
    }

    @Override
    public List<Contato> getAll() {
        return lista;
    }

}
