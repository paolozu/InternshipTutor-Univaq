/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.Impl;

import Model.Bean.Annuncio;
import Model.Bean.Azienda;
import Model.Bean.Convenzione;
import Model.Bean.Referente;
import Model.Bean.Studente;
import Model.Bean.Docente;
import Model.Bean.Tirocinio;
import Model.Bean.Utente;
import Model.DAO.Interface.AziendaDAO;
import Model.DAO.Interface.UtenteDAO;
import Framework.data.DB;
import Framework.data.DataLayerException;
import Framework.result.FailureResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author lorenzo
 */
public class AziendaDAOImpl implements AziendaDAO {

    private static final String GET_RICHIESTE = "SELECT Studente.idStudente,Studente.nome,Studente.cognome,Utente.email, "
            + "                                  Studente.codFiscale, Studente.Telefono FROM Azienda JOIN Richiesta  ON Azienda.idAzienda=Richiesta.Azienda_idAzienda "
            + "                                  JOIN Studente ON Richiesta.Studente_idStudente = Studente.idStudente "
            + "                                  JOIN Utente ON Utente.idUtente = Studente.idStudente "
            + "                                  WHERE Azienda.idAzienda = ?";

    private static final String GET_AZIENDE = "SELECT azienda.idAzienda,azienda.ragSociale FROM azienda";

    private static final String GET_TIROCINANTI = "SELECT Studente.idStudente, Studente.nome, Studente.cognome, Utente.email, "
            + "                                    Studente.codFiscale, Studente.Telefono, Tirocinio.Annuncio_idAnnuncio FROM Studente "
            + "                                     JOIN Tirocinio ON Studente.idStudente = Tirocinio.Studente_idStudente "
            + "                                     JOIN Annuncio ON Tirocinio.Annuncio_idAnnuncio = Annuncio.idAnnuncio "
            + "                                     JOIN Azienda ON Azienda.idAzienda = Annuncio.Azienda_idAzienda "
            + "                                  JOIN Utente ON Utente.idUtente = Studente.idStudente "
            + "                                         WHERE idAzienda = ? AND Tirocinio.Resoconto_idResoconto IS NULL";

    private static final String GET_CONVENZIONE = "SELECT Convenzione.nome, Convenzione.directory, Convenzione.estensione, Convenzione.peso "
            + "                                     FROM Azienda "
            + "                                      JOIN Convenzione ON Azienda.idConvenzione = Convenzione.idConvenzione "
            + "                                         WHERE idAzienda = ?";

    private static final String GET_APPROVAZIONE = "SELECT * FROM Azienda "
            + "                                         JOIN Convenzione ON Azienda.idConvenzione = Convenzione.idConvenzione "
            + "                                             WHERE Azienda.idAzienda=?";

    
    private static final String SET_CONCLUDI_TIROCINIO = "INSERT INTO `resoconto` (oreSvolte, attivitaSvolta, risConseguito) VALUES (?,?,?);";

    private static final String UPDATE_ID_RESOCONTO_TIROCINIO = "UPDATE Tirocinio SET Resoconto_idResoconto=? WHERE Annuncio_idAnnuncio=? AND Studente_idStudente=?";

    private static final String REGISTRAZIONE_AZIENDA = "INSERT INTO azienda ( stato, ragSociale, indirizzoSede, pIVA, foro, cap, citta, provincia, nomeRap, cognomeRap, telResponsabile, nomeResponsabile, cognomeResponsabile, emailResponsabile,idAzienda) "
            + "                                         VALUES ('REGISTRATA',?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    private static final String UPDATE_STATO = "UPDATE Azienda SET Stato=? WHERE idAzienda=?";

    private static final String GET_STATO = "SELECT Azienda.Stato FROM Azienda WHERE idAzienda=?";
 
    private static final String IS_CONVENZIONATA = "SELECT Azienda.Stato FROM Azienda WHERE idAzienda=? AND Stato='CONVENZIONATA'";

    @Override
    public List<Studente> getRichieste(long id) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Studente> richieste = new ArrayList();
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_RICHIESTE);
            ps.setLong(1, id);
            rset = ps.executeQuery();
            while (rset.next()) {
                richieste.add(new Studente(rset.getLong("idStudente"), rset.getString("nome"), rset.getString("cognome"), rset.getString("email"), rset.getString("codFiscale"), rset.getString("telefono")));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return richieste;
    }

    @Override
    public List<Azienda> getAziende() throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Azienda> aziende = new ArrayList();
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_AZIENDE);
            rset = ps.executeQuery();
            while (rset.next()) {
                aziende.add(new Azienda(rset.getInt("idAzienda"), rset.getString("ragSociale")));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return aziende;
    }

    @Override
    public List<Tirocinio> getTirocini(long id) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Tirocinio> tirocini = new ArrayList();
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_TIROCINANTI);
            ps.setLong(1, id);
            rset = ps.executeQuery();
            while (rset.next()) {
                
                Annuncio ann = new Annuncio(rset.getLong("Tirocinio.Annuncio_idAnnuncio"));
                Studente stu= new Studente(rset.getLong("idStudente"), rset.getString("nome"), rset.getString("cognome"), rset.getString("email"), rset.getString("codFiscale"), rset.getString("telefono"));
            
                tirocini.add(new Tirocinio(stu,ann));
            
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE TIROCINANTI", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tirocini;
    }

    @Override
    public Convenzione getConvenzione(long id) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Convenzione convenzione = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_CONVENZIONE);
            ps.setLong(1, id);
            rset = ps.executeQuery();

            if (rset.next()) {
                convenzione = new Convenzione(rset.getString("nome"), rset.getString("directory"), rset.getString("estensione"), rset.getLong("peso"));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return convenzione;
    }

    @Override
    public Azienda getApprovazione(long id) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Convenzione convenzione = null;
        Azienda azienda = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_APPROVAZIONE);
            ps.setLong(1, id);
            rset = ps.executeQuery();

            if (rset.next()) {

                convenzione = new Convenzione(rset.getInt("durataConvenzione"), rset.getDate("dataConvenzione").toLocalDate());
                azienda = new Azienda(rset.getLong("idAzienda"), rset.getString("nomeRap"), rset.getString("cognomeRap"), rset.getString("telResponsabile"), rset.getString("nomeResponsabile"), rset.getString("cognomeResponsabile"), rset.getString("emailResponsabile"), rset.getString("ragSociale"), rset.getString("indirizzoSede"), rset.getString("pIVA"), rset.getString("foro"), rset.getString("cap"), rset.getString("citta"), rset.getString("provincia"), convenzione);
            }

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return azienda;
    }

    /**
     * Salva nel db il resoconto aggiornando la tabella tirocinio
     *
     * @param tirocinio
     */
    @Override
    public void setConcludiTirocinio(Tirocinio tirocinio) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(SET_CONCLUDI_TIROCINIO, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, tirocinio.getResoconto().getOreSvolte());
            ps.setString(2, tirocinio.getResoconto().getAttivitaSvolta());
            ps.setString(3, tirocinio.getResoconto().getRisultatoConseguito());

            int result = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next() && result != 0) {
                
                System.out.println("3");
                long idResoconto = generatedKeys.getLong(1);

                ps = connection.prepareStatement(UPDATE_ID_RESOCONTO_TIROCINIO);
                ps.setLong(1, idResoconto);
                ps.setLong(2, tirocinio.getAnnuncio().getId());
                ps.setLong(3, tirocinio.getStudente().getId());

                int result2 = ps.executeUpdate();
                if ( result2 != 0) {
                }else{
                    throw new DataLayerException("ERRORE UPDATE UTENTE");
                }
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public int setRegistrazioneAzienda(Azienda azienda) throws DataLayerException {
        int result = 0;
        //Creazione Utente
        UtenteDAO u = new UtenteDAOImpl();
        Utente nuovoUtente = u.nuovoUtente(new Utente(azienda.getUsername(), azienda.getPassword(), azienda.getEmail(), azienda.getTipo()));

        if (nuovoUtente != null) {//NUOVO UTENTE - OK

            Connection connection = null;
            PreparedStatement ps = null;

            try {
                connection = DB.getConnection(); // CREAZIONE CONNESSIONE

                ps = connection.prepareStatement(REGISTRAZIONE_AZIENDA);

                ps.setString(1, azienda.getRagioneSociale());
                ps.setString(2, azienda.getIndirizzoSede());
                ps.setString(3, azienda.getPartitaIva());
                ps.setString(4, azienda.getForoCompetente());
                ps.setString(5, azienda.getCap());
                ps.setString(6, azienda.getCitta());
                ps.setString(7, azienda.getProvincia());
                ps.setString(8, azienda.getNomeRappresentante());
                ps.setString(9, azienda.getCognomeRappresentante());
                ps.setString(10, azienda.getTelResponsabile());
                ps.setString(11, azienda.getNomeResponsabile());
                ps.setString(12, azienda.getCognomeResponsabile());
                ps.setString(13, azienda.getEmailResponsabile());
                ps.setLong(14, nuovoUtente.getId());

                result = ps.executeUpdate();

            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
            } finally {
                //CHIUSURA CONNESSIONE
                try {
                    ps.close();
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {//NUOVO UTENTE - ERRORE
            Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "ERRORE CREAZIONE - UTENTE");
            //Verificare presenza utenti
        }
        return result;
    }

    @Override
    public int updateStato(Azienda azienda, String stato) throws DataLayerException {
        int result = 0;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DB.getConnection(); // CREAZIONE CONNESSIONE

            ps = connection.prepareStatement(UPDATE_STATO);

            ps.setString(1, stato);
            ps.setLong(2, azienda.getId());

            result = ps.executeUpdate();

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            //CHIUSURA CONNESSIONE
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, "ERRORE CHIUSURA CONNESSIONE ", ex);
            }
        }

        return result;
    }

    public boolean isConvenzionata(long id) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        

        String result = null;
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(IS_CONVENZIONATA);
            ps.setLong(1, id);
            rset = ps.executeQuery();

            if (rset.next()) {
                if (rset.getString("Stato").equals("CONVENZIONATA")) {
                    return true;
                }
            }
        } catch (SQLException ex) {
             Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DataLayerException("ERRORE STATO UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public String getStato(long id) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Azienda azienda = null;

        String result = null;
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_STATO);
            ps.setLong(1, id);
            rset = ps.executeQuery();

            if (rset.next()) {
                result = rset.getString("Stato");
            }

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CREDENZIALI UTENTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudenteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;

    }
}
