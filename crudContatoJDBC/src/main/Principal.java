package main;

import controller.ContatoController;
import dao.ContatoDao;
import dao.ContatoDaoJdbc;
import entidade.Contato;
import java.util.Date;
import java.util.List;
import view.EditContatoView;
import view.ListContatoView;

public class Principal {

    static ContatoDao dao = new ContatoDaoJdbc();

    public static void main(String[] args) {
        
        
        ListContatoView view = new ListContatoView();
        EditContatoView editView = new EditContatoView();
        ContatoController controller = new ContatoController(view, editView);
        controller.iniciar();
//        
//
//        try {            
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            
//            String dataAsString = "16/10/2018";            
//            Date umaData = sdf.parse(dataAsString);
//
//            Date dateAsDate = new Date();
//            String str = sdf.format(dateAsDate);      
//            
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        } 

//adicionarContatos();
        // recuperarContatos();       
        // apagarUmContato();
        // alterarUmContato();
    }

    private static void adicionarContatos() {
        Contato c = new Contato();
        c.setNome("Joao");
        c.setDataNascimento(new Date());
        c.setAtivo(true);

        Contato c1 = new Contato();
        c1.setNome("Pedro");
        c1.setDataNascimento(new Date());
        c1.setAtivo(true);

        Contato c2 = new Contato();
        c2.setNome("José");
        c2.setDataNascimento(new Date());
        c2.setAtivo(true);

        //  ContatoDao dao = new ContatoDaoJdbc();
        dao.salvar(c);
        dao.salvar(c1);
        dao.salvar(c2);
    }

    private static void recuperarContatos() {
        // ContatoDao dao = new ContatoDaoJdbc();
        List<Contato> listagem = dao.getAll();
        for (Contato umContato : listagem) {
            System.out.println(umContato.getNome());
        }
    }

    private static void apagarUmContato() {
        //  ContatoDao dao = new ContatoDaoJdbc();
        List<Contato> listagem = dao.getAll();
        System.out.println("Antes da exclusão...");
        for (Contato umContato : listagem) {
            System.out.println(umContato.getId());
        }
        Contato c = listagem.get(0);
        dao.excluir(c);
        System.out.println("Depois da exclusão...");
        for (Contato umContato : dao.getAll()) {
            System.out.println(umContato.getId());
        }
    }

    private static void alterarUmContato() {
        Contato vitima = dao.getAll().get(0);
        vitima.setNome("João da Silva Junior Costa e Silva");
        dao.salvar(vitima);
    }

}
