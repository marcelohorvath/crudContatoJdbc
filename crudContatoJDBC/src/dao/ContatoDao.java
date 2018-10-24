package dao;

import entidade.Contato;
import java.util.List;


public interface ContatoDao {
    
    public void salvar(Contato contato);
    
    public void excluir(Contato contato);
    
    public List<Contato> getAll();
    
}
