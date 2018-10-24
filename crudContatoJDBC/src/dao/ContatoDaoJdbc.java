package dao;

import entidade.Contato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContatoDaoJdbc implements ContatoDao {

    @Override
    public void salvar(Contato contato) {
        if (contato.getId() == null) {
            try {
                Connection conn = Conexao.getConexao();
                String query = "INSERT INTO CONTATO(nome, dataNascimento, ativo) VALUES (?,?,?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, contato.getNome());
                long dateAsLong = contato.getDataNascimento().getTime();
                ps.setDate(2, new java.sql.Date(dateAsLong));
                ps.setBoolean(3, contato.getAtivo());
                ps.executeUpdate();
                //  conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Connection conn = Conexao.getConexao();
                String QUERY_UPDATE = "update Contato set nome = ?, dataNascimento = ?, ativo = ?  where idcontato = ? ";
                PreparedStatement ps = conn.prepareStatement(QUERY_UPDATE);
                ps.setString(1, contato.getNome());
                long dateAsLong = contato.getDataNascimento().getTime();
                ps.setDate(2, new java.sql.Date(dateAsLong));
                ps.setBoolean(3, contato.getAtivo());
                ps.setInt(4, contato.getId());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void excluir(Contato contato) {
        try {
            Connection conn = Conexao.getConexao();
            String query = "DELETE FROM CONTATO WHERE idcontato = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, contato.getId());
            ps.executeUpdate();
            //    conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Contato> getAll() {
        List<Contato> lista = new ArrayList();
        Connection conn = Conexao.getConexao();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from contato");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getInt("idcontato"));
                c.setNome(rs.getString("nome"));
                c.setDataNascimento(rs.getDate("dataNascimento"));
                c.setAtivo(rs.getBoolean("ativo"));
                lista.add(c);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}
