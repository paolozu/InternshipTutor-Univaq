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
import Model.Bean.Resoconto;
import Model.Bean.Richiesta;
import java.io.InputStream;
import java.sql.Blob;
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
import javax.servlet.http.Part;

/**
 *
 * @author lorenzo
 */
public class AziendaDAOImpl implements AziendaDAO {

    private static final String GET_RICHIESTE = "SELECT Studente.idStudente,Studente.nome,Studente.cognome,Utente.email, Studente.codFiscale, Studente.Telefono, Annuncio.idAnnuncio "
            + "                                           FROM Richiesta  \n"
            + "                                                           JOIN Annuncio  ON Annuncio.idAnnuncio=Richiesta.Annuncio_idAnnuncio \n"
            + "                                                           JOIN Azienda ON Annuncio.Azienda_idAzienda = Azienda.idAzienda \n"
            + "                                                           JOIN Studente ON Richiesta.Studente_idStudente = Studente.idStudente  \n"
            + "                                                           JOIN Utente ON Utente.idUtente = Studente.idStudente  \n"
            + "                                                               WHERE Azienda.idAzienda = ?";

    private static final String GET_RICHIESTA_STUDENTE = "SELECT * FROM Richiesta JOIN STUDENTE ON Richiesta.Studente_idStudente=Studente.idStudente WHERE Studente.idStudente=? AND Richiesta.Annuncio_idAnnuncio=?";

    private static final String GET_AZIENDE = "SELECT azienda.idAzienda,azienda.ragSociale FROM azienda";

    private static final String GET_AZIENDA = "SELECT * FROM Azienda WHERE Azienda.idAzienda=?";

    private static final String GET_TIROCINANTI = "SELECT Studente.idStudente, Studente.nome, Studente.cognome, Utente.email, "
            + "                                    Studente.codFiscale, Studente.Telefono, Tirocinio.Annuncio_idAnnuncio FROM Studente "
            + "                                     JOIN Tirocinio ON Studente.idStudente = Tirocinio.Studente_idStudente "
            + "                                     JOIN Annuncio ON Tirocinio.Annuncio_idAnnuncio = Annuncio.idAnnuncio "
            + "                                     JOIN Azienda ON Azienda.idAzienda = Annuncio.Azienda_idAzienda "
            + "                                  JOIN Utente ON Utente.idUtente = Studente.idStudente "
            + "                                         WHERE idAzienda = ? AND Tirocinio.Resoconto_idResoconto IS NULL";

    private static final String GET_CONVENZIONE = "SELECT Convenzione.nome, Convenzione.file, Convenzione.estensione, Convenzione.peso "
            + "                                     FROM Azienda "
            + "                                      JOIN Convenzione ON Azienda.Convenzione_idConvenzione = Convenzione.idConvenzione "
            + "                                         WHERE Azienda.idAzienda = ?";

    private static final String GET_MODULO_CONVENZIONE = "SELECT Convenzione.nome, Convenzione.file, Convenzione.estensione, Convenzione.peso FROM Convenzione WHERE Convenzione.idConvenzione = 0";

    private static final String GET_APPROVAZIONE = "SELECT * FROM Azienda "
            + "                                         JOIN Convenzione ON Azienda.idConvenzione = Convenzione.idConvenzione "
            + "                                             WHERE Azienda.idAzienda=?";

    private static final String SET_CONCLUDI_TIROCINIO = "INSERT INTO `resoconto` (oreSvolte, attivitaSvolta, risConseguito) VALUES (?,?,?);";

    private static final String UPDATE_ID_RESOCONTO_TIROCINIO = "UPDATE Tirocinio SET Resoconto_idResoconto=? WHERE Annuncio_idAnnuncio=? AND Studente_idStudente=?";

    private static final String REGISTRAZIONE_AZIENDA = "INSERT INTO azienda ( stato, ragSociale, indirizzoSede, pIVA, foro, cap, citta, provincia, nomeRap, cognomeRap, telResponsabile, nomeResponsabile, cognomeResponsabile, emailResponsabile, idAzienda, dataIscrizione, dataTermine) "
            + "                                         VALUES ('REGISTRATA',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    private static final String UPDATE_STATO = "UPDATE Azienda SET Stato=? WHERE idAzienda=?";

    private static final String GET_STATO = "SELECT Azienda.Stato FROM Azienda WHERE idAzienda=?";

    private static final String IS_CONVENZIONATA = "SELECT Azienda.Stato FROM Azienda WHERE idAzienda=? AND Stato='CONVENZIONATA'";

    private static final String DELETE_TIROCINIO = "DELETE FROM Tirocinio WHERE Tirocinio.Annuncio_idAnnuncio=? AND Studente_idStudente=?";

    private static final String DELETE_RICHIESTA = "DELETE FROM Richiesta WHERE Richiesta.Annuncio_idAnnuncio=? AND Richiesta.Studente_idStudente=?";

    private static final String SET_NUOVO_TIROCINIO = "INSERT INTO Tirocinio (Annuncio_idAnnuncio, Studente_idStudente, dataInizio, dataFine)\n"
            + "VALUES (?,?,?,?)";

    private static final String GET_AZIENDE_CONVENZIONATE = "SELECT azienda.ragSociale FROM azienda WHERE Stato='CONVENZIONATA'";

    private static final String REMOVE_AZIENDA = "DELETE FROM AZIENDA WHERE idAzienda=?";
    
    

    @Override
    public List<Richiesta> getRichieste(long idAzienda) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Richiesta> richieste = new ArrayList();
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_RICHIESTE);
            ps.setLong(1, idAzienda);
            rset = ps.executeQuery();
            while (rset.next()) {

                Studente s = new Studente(rset.getLong("idStudente"), rset.getString("nome"), rset.getString("cognome"), rset.getString("email"), rset.getString("codFiscale"), rset.getString("telefono"));
                Annuncio a = new Annuncio(rset.getLong("idAnnuncio"));
                richieste.add(new Richiesta(a, s));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE GET RICHIESTE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return richieste;
    }

    @Override
    public Richiesta getRichiestaStudente(Richiesta richiesta) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_RICHIESTA_STUDENTE);
            ps.setLong(1, richiesta.getStudente().getId());
            ps.setLong(2, richiesta.getAnnuncio().getId());
            rset = ps.executeQuery();

            if (rset.next()) {
                
                
                Studente s = new Studente(
                        rset.getLong("idStudente"),
                        rset.getString("nome"), rset.getString("cognome"),
                        rset.getString("codFiscale"), rset.getString("telefono"),
                        rset.getString("indirizzoResidenza"), rset.getString("corsoLaurea"),
                        rset.getString("cap_Residenza"), rset.getString("citta_Residenza"),
                        rset.getString("provincia_Residenza"), rset.getBoolean("handicap"),
                        rset.getDate("dataNascita").toLocalDate()
                );
                
                Annuncio a = new Annuncio(rset.getLong("Richiesta.Annuncio_idAnnuncio"));
                richiesta = new Richiesta(a, s);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE GET CONVEZIONE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return richiesta;
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
            throw new DataLayerException("ERRORE GET AZIENDE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
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
                Studente stu = new Studente(rset.getLong("idStudente"), rset.getString("nome"), rset.getString("cognome"), rset.getString("email"), rset.getString("codFiscale"), rset.getString("telefono"));

                tirocini.add(new Tirocinio(stu, ann));

            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE GET TIROCINI", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return tirocini;
    }

    @Override
    public Azienda getApprovazione(long id) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rset = null;
        Azienda azienda = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(GET_APPROVAZIONE);
            ps.setLong(1, id);
            rset = ps.executeQuery();

            if (rset.next()) {
                azienda = new Azienda(rset.getLong("idAzienda"), rset.getString("nomeRap"), rset.getString("cognomeRap"), rset.getString("telResponsabile"), rset.getString("nomeResponsabile"), rset.getString("cognomeResponsabile"), rset.getString("emailResponsabile"), rset.getString("ragSociale"), rset.getString("indirizzoSede"), rset.getString("pIVA"), rset.getString("foro"), rset.getString("cap"), rset.getString("citta"), rset.getString("provincia"));
            }

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE APPROVAZIONE AZIENDA", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return azienda;
    }

    /**
     * Salva il resoconto aggiornando la tabella tirocinio
     *
     * @param tirocinio
     * @return 
     * @throws Framework.data.DataLayerException
     */
    @Override
    public Resoconto setConcludiTirocinio(Tirocinio tirocinio) throws DataLayerException {
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

                long idResoconto = generatedKeys.getLong(1);

                ps = connection.prepareStatement(UPDATE_ID_RESOCONTO_TIROCINIO);
                ps.setLong(1, idResoconto);
                ps.setLong(2, tirocinio.getAnnuncio().getId());
                ps.setLong(3, tirocinio.getStudente().getId());

                int result2 = ps.executeUpdate();
                if (result2 != 0) {
                } else {
                    throw new DataLayerException("ERRORE UPDATE TIROCINIO");
                }
                return new Resoconto(idResoconto);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CONCLUDI TIROCINIO", ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        
        return null;

    }

    @Override
    public int setRegistrazioneAzienda(Azienda azienda) throws DataLayerException {
        int result = 0;
        //Creazione Utente
        UtenteDAO u = new UtenteDAOImpl();
        Utente nuovoUtente = u.nuovoUtente(new Utente(azienda.getUsername(), azienda.getPassword(), azienda.getEmail(), "AZ"));

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
                ps.setDate(15, java.sql.Date.valueOf(azienda.getDataIscrione()));
                ps.setDate(16, java.sql.Date.valueOf(azienda.getDataTermine()));

                result = ps.executeUpdate();

            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE REGISTRAZIONE AZIENDA", ex);
            } finally {
                //CHIUSURA CONNESSIONE
                try {
                    ps.close();
                    connection.close();
                } catch (SQLException ex) {
                    throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
                }
            }
        } else {//NUOVO UTENTE - ERRORE
            throw new DataLayerException("ERRORE CREAZIONE - UTENTE");
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
            throw new DataLayerException("ERRORE UPDATE STATO AZIENDA", ex);
        } finally {
            //CHIUSURA CONNESSIONE
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }

        return result;
    }

    @Override
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
            throw new DataLayerException("ERRORE QUERY CONVENZIONE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return false;
    }

    @Override
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
            throw new DataLayerException("ERRORE QUERY STATO AZIENDA", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return result;

    }

    /**
     * Rimnuovi il tirocinio sostenuto dallo studente presso l'azienda
     *
     * @param tirocinio
     * @throws Framework.data.DataLayerException
     */
    @Override
    public void removeTirocinio(Tirocinio tirocinio) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(DELETE_TIROCINIO);
            ps.setLong(1, tirocinio.getAnnuncio().getId());
            ps.setLong(2, tirocinio.getStudente().getId());

            int result = ps.executeUpdate();
            if (result != 0) {
            } else {
                throw new DataLayerException("ERRORE DELETE TIROCINIO");
            }

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE QUERY RIMOZIONE TIROCINIO", ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }

    }

    /**
     * Rimnuovi il tirocinio sostenuto dallo studente presso l'azienda
     *
     * @param richiesta
     * @param tirocinio
     * @throws Framework.data.DataLayerException
     */
    @Override
    public void removeRichiesta(Richiesta richiesta) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(DELETE_RICHIESTA);
            ps.setLong(1, richiesta.getAnnuncio().getId());
            ps.setLong(2, richiesta.getStudente().getId());

            int result = ps.executeUpdate();

            if (result != 0) {
            } else {
                throw new DataLayerException("ERRORE DELETE RICHIESTA");
            }

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE QUERY RIMOZIONE TIROCINIO", ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }

    }

    /**
     * Rimnuovi il tirocinio sostenuto dallo studente presso l'azienda
     *
     * @param tirocinio
     * @return 
     * @throws Framework.data.DataLayerException
     */
    @Override
    public int setNuovoTirocinio(Tirocinio tirocinio) throws DataLayerException {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            connection = DB.getConnection();
            ps = connection.prepareStatement(SET_NUOVO_TIROCINIO);
            ps.setLong(1, tirocinio.getAnnuncio().getId());
            ps.setLong(2, tirocinio.getStudente().getId());
            ps.setDate(3, java.sql.Date.valueOf(tirocinio.getDataInizio()));
            ps.setDate(4, java.sql.Date.valueOf(tirocinio.getDataFine()));

            result = ps.executeUpdate();

            if (result != 0) {
            } else {
                throw new DataLayerException("ERRORE NUOVO TIROCINIO");
            }

        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE QUERY NUOVO TIROCINIO", ex);
        } finally {
            try {
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return result;
    }

    @Override
    public List<Azienda> getAllAziendeConvenzionate() throws DataLayerException {
        Connection connection = DB.getConnection();
        PreparedStatement ps = null;
        ResultSet rset = null;
        List<Azienda> aziende = new ArrayList();
        try {
            ps = connection.prepareStatement(GET_AZIENDE_CONVENZIONATE);
            rset = ps.executeQuery();
            while (rset.next()) {
                aziende.add(new Azienda(rset.getString("ragSociale")));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE GET AZIENDE CONVENZIONATE", ex);
        } finally {
            try {
                rset.close();
                ps.close();
                connection.close();
            } catch (SQLException ex) {
                throw new DataLayerException("ERRORE CHIUSURA CONNESSIONE", ex);
            }
        }
        return aziende;
    }

    @Override
    public Azienda getAzienda(Azienda azienda) throws DataLayerException {

        PreparedStatement ps;
        ResultSet rset;

        try (Connection connection = DB.getConnection()) {
            ps = connection.prepareStatement(GET_AZIENDA);
            ps.setLong(1, azienda.getId());
            rset = ps.executeQuery();

            if (rset.next()) {

                azienda.setNomeRappresentante(rset.getString("nomeRap"));
                azienda.setCognomeRappresentante(rset.getString("nomeRap"));
                azienda.setTelResponsabile(rset.getString("telResponsabile"));
                azienda.setNomeResponsabile(rset.getString("nomeResponsabile"));
                azienda.setCognomeResponsabile(rset.getString("cognomeResponsabile"));
                azienda.setEmailResponsabile(rset.getString("emailResponsabile"));
                azienda.setRagioneSociale(rset.getString("ragSociale"));
                azienda.setIndirizzoSede(rset.getString("indirizzoSede"));
                azienda.setPartitaIva(rset.getString("pIVA"));
                azienda.setForoCompetente(rset.getString("foro"));
                azienda.setCap(rset.getString("cap"));
                azienda.setCitta(rset.getString("citta"));
                azienda.setProvincia(rset.getString("provincia"));
                azienda.setDataIscrione(rset.getDate("dataIscrizione").toLocalDate());
                azienda.setDataTermine(rset.getDate("dataTermine").toLocalDate());

            }

            ps.close();
            connection.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Error get company", ex);
        }
        return azienda;
    }

    @Override
    public int removeAzienda(Azienda azienda) throws DataLayerException {

        PreparedStatement ps;
        int result = 0;
        try (Connection connection = DB.getConnection()) {
            ps = connection.prepareStatement(REMOVE_AZIENDA);
            ps.setLong(1, azienda.getId());

            result = ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Error remove company", ex);
        }
        return result;
    }

    @Override
    public InputStream getConvenzione(Azienda azienda) throws DataLayerException {
        PreparedStatement ps;
        InputStream inputStream = null;

        try (Connection connection = DB.getConnection()) {
            ps = connection.prepareStatement(GET_CONVENZIONE);
            ps.setLong(1, azienda.getId());

            ResultSet rset = ps.executeQuery();

            if (rset.next()) {
                Blob blob = rset.getBlob("file");
                inputStream = blob.getBinaryStream();
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            throw new DataLayerException("GET CONVENZIONE", e);
        }

        return inputStream;
    }

    @Override
    public InputStream getModuloConvenzione() throws DataLayerException {
        PreparedStatement ps;
        InputStream inputStream = null;

        try (Connection connection = DB.getConnection()) {
            ps = connection.prepareStatement(GET_MODULO_CONVENZIONE);

            ResultSet rset = ps.executeQuery();

            if (rset.next()) {
                Blob blob = rset.getBlob("file");
                inputStream = blob.getBinaryStream();
            }

            ps.close();
            connection.close();

        } catch (SQLException e) {
            throw new DataLayerException("Get modulo convenzione", e);
        }

        return inputStream;
    }

}
