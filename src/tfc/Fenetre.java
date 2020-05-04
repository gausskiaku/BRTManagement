/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tfc;

import Connexion.Connexion;
import Connexion.pasvVde;
import Entites.*;
import Dao.*;
import JImage.JIResizeImage;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Gauss
 */
public class Fenetre extends javax.swing.JFrame {
    
   
    Connection conn;
    pasvVde w =new pasvVde();
    boolean we=false;
    Player player;
    BufferedImage bi;
    String nfile;
    byte[]person_image = null;
    DateFormatSymbols d = new DateFormatSymbols();
    SimpleDateFormat dt = new SimpleDateFormat("YYYY/MM/dd", d);
    
    Thread t;
    Dao<Emplacement> emplacementDao  = new EmplacementDAO();
    Dao<Materiel> materielDao = new MaterielDAO();
    Dao<Evenement_mat> evenement_matDao = new Evenement_matDAO();
    Dao<Agent> agentDao = new AgentDAO();
    Dao<Parrain> parrainDao = new ParrainDAO();
    Dao<Enfant> enfantDao = new EnfantDAO();
    Dao<Assumer> assumerDao = new AssumerDAO();
    Dao<Animateur> animateurDao = new AnimateurDAO();
    Dao<Fonction> fonctionDao = new FonctionDAO();
    Dao<Programme> programmeDao = new ProgrammeDAO();
    Dao<Activite> activiteDao = new ActiviteDAO();
    Dao<Presence> presenceDao = new PresenceDAO();
    Dao<Horaire_agent> horaire_agentDao = new Horaire_agentDAO();
    Dao<User> userDao = new UserDAO();
    
    public Fenetre() {
        initComponents();
        conn = Connexion.getInstance();
        design();
             
    }
public void design (){
    
    this.setContentPane(pan);
    this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("az.png")));
    Date_et_Heure();
    table_emplacement();
    cbo_emplacement();
    table_parrain();
    table_fonction();
    table_personnel();
    table_materiel();
    table_activite();
    table_tele();
    table_presence();
    table_enfant();
    table_user();
    cbo_activite("Television");
    cbo_activite("Radio");
    cbo_parrain();
    cbo_parrain_affiche();
    cbo_fonction();
    cbo_agent();
    cbo_presence();
    cbo_presence2();
    Matricule();
    MenuBar.setVisible(false);
    m.setVisible(false);
    yes.setVisible(false);
    this.setResizable(false);
    bg.add(rb_date);
    bg.add(rb_nom);
//    bg_Radio.add(rb_date1);
//    bg_Radio.add(rb_nom1);
    cdar_date_horaire.setEnabled(false);
    txt_nom_horaire.setEnabled(false);
//    cdar_date_horaire1.setEnabled(false);
//    txt_nom_horaire1.setEnabled(false);
    
    jPanel2.setVisible(false);
//    pan.getRootPane().setDefaultButton(ok);
//    pan_tech_stock.getRootPane().setDefaultButton(Rech_Stock);
    
        }
public void Repaindre(JPanel panParent, JPanel panFils){
        panParent.removeAll();
        panParent.repaint();
        panParent.revalidate();
        panParent.add(panFils);
        panParent.repaint();
        panParent.revalidate();
}

private void initCamera() throws IOException,NoPlayerException,CannotRealizeException {
    MediaLocator ml = new MediaLocator("vfw://0");
    player = Manager.createRealizedPlayer(ml);
     this.tbCamera.add(player.getVisualComponent());
     player.start();
    }
private void setImageButton (JToggleButton tbutton, Image image){
tbutton.setIcon(new ImageIcon(image));
}
//class sevine implements Runnable{
//@Override
//public void run() {
//cb_nomparrain_ag.removeAllItems();
//cbo_parrain();
//}
//}
private void Matricule(){
txt_matricule_ag.setText(new AgentDAO().Matricule());
}
public void cbo_emplacement(){
    try {
        ResultSet r = new EmplacementDAO().PourTable();
        while(r.next()){
//        cmbo_emplacement.setModel(new javax.swing.DefaultComboBoxModel(String[] {""}));
        cmbo_emplacement.addItem(r.getString("Nom de l'Emplacement"));
        cmbo_empl_ev.addItem(r.getString("Nom de l'Emplacement"));
        cmbo_empl_sor.addItem(r.getString("Nom de l'Emplacement"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
class sevine implements Runnable{
@Override
public void run() {
    try {
        initCamera();
    } catch (Exception e) {
    }
}
}
public void Date_et_Heure(){
   Thread clock = new Thread(){
   public void run(){
   for(;;){
   Calendar cal = new GregorianCalendar();
    int mois = cal.get(Calendar.MONTH);
    int annee = cal.get(Calendar.YEAR);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    date_presence.setText(""+day+"/"+(mois +1)+"/"+annee);
    
    
   int seconde = cal.get(Calendar.SECOND);
   int minute = cal.get(Calendar.MINUTE);
   int heure = cal.get(Calendar.HOUR_OF_DAY);
   heure_ev.setText(""+heure+":"+(minute)+":"+seconde);
   heureSortie.setText(""+heure+":"+(minute)+":"+seconde);
   heure_presence.setText(""+heure+":"+(minute)+":"+seconde);
       try {
           sleep(1000);
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
   }
   }
   };
   clock.start();
}
public void  cbo_parrain(){
    try {
        ResultSet r = new ParrainDAO().PourTable();
        while(r.next()){
        cb_nomparrain_ag.addItem(r.getString("Nom du parrain"));}
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void cbo_activite(String departement){
    try {
        ResultSet r = new ActiviteDAO().Combo(departement);
        nomActi_pro.setSelectedIndex(-1);
//        nomActi_pro1.setSelectedIndex(-1);
        while(r.next()){
        nomActi_pro.addItem(r.getString("Nom de l'activité"));
//        nomActi_pro1.addItem(r.getString("Nom de l'activité"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void cbo_agent(){
    try {
        ResultSet r = new AgentDAO().PourTable();
        while(r.next()){
        cbo_agent_Enfant.addItem(r.getString("Nom"));
        cbo_agent_ev.addItem(r.getString("Nom"));
        cmbo_agent_Sorti.addItem(r.getString("Nom"));
        cbo_nomAgent_pro.addItem(r.getString("Nom"));
////        cbo_nomAgent_pro1.addItem(r.getString("Nom"));
        cbo_agent_presence.addItem(r.getString("Nom"));
        cbo_agent_presence1.addItem(r.getString("Nom"));
        nomag_adm.addItem(r.getString("Nom"));
        
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
private void cbo_presence(){
    try {
        ResultSet r = new PresenceDAO().Info(date_presence.getText());
        while(r.next()){
        cbo_agent_presence.addItem(r.getString("nom_agent"));
        
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }

}
private void cbo_presence2(){
    try {
        ResultSet r = new PresenceDAO().InfoCombo(date_presence.getText());
        while(r.next()){
        cbo_agent_presence1.addItem(r.getString("nom_agent"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }

}
public void cbo_fonction(){
    try {
        ResultSet r = new FonctionDAO().PourTable();
        while(r.next()){
        cbo_fonction_ag.addItem(r.getString("fonction"));}
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_emplacement(){
    try {
        tble_emplacement.setModel(DbUtils.resultSetToTableModel(new EmplacementDAO().PourTable()));
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_enfant(){
    try {
        tble_enfant.setModel(DbUtils.resultSetToTableModel(new EnfantDAO().PourTable()));
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_user(){
    try {
        tble_user.setModel(DbUtils.resultSetToTableModel(new UserDAO().PourTable()));
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_tele(){
    try {
        tble_tele.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().PourTable("Television")));
//        tble_tele_r.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().PourTable("Radio")));
        
        tble_tele_gestion.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().Gestion("Television")));
//        tble_tele_gestion1.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().Gestion("Radio")));
        
        // Pour l'horaire TELE ET RADIO
        tble_tele_horaire.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().PourTable_Horaire("Television")));
//        tble_tele_horaire1.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().PourTable_Horaire("Radio")));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_personnel(){
    try {
        tble_personnel.setModel(DbUtils.resultSetToTableModel(new AgentDAO().PourTable()));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_materiel(){
    try {
        tble_stockMat.setModel(DbUtils.resultSetToTableModel(new MaterielDAO().PourTable()));
        tble_gestionMat.setModel(DbUtils.resultSetToTableModel(new MaterielDAO().Gestion()));
        table();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_activite(){
    try {
        tble_activite.setModel(DbUtils.resultSetToTableModel(new ActiviteDAO().PourTable("Television")));
//        tble_activite_r.setModel(DbUtils.resultSetToTableModel(new ActiviteDAO().PourTable("Radio")));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_parrain(){
    try {
        tble_parrain.setModel(DbUtils.resultSetToTableModel(new ParrainDAO().PourTable()));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_presence(){
    try {
        tble_presence.setModel(DbUtils.resultSetToTableModel(new PresenceDAO().PourTable(date_presence.getText())));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void table_fonction(){
tble_fonction.setModel(DbUtils.resultSetToTableModel(new FonctionDAO().PourTable()));
}
public void identification(){
    try {
        ResultSet result = new UserDAO().Recherche(mot_de_passe.getText());
        while (result.next()){
        if (result.getString("nom_user").equals("Administracteur")){
        nom_user.setText(result.getString("nom_agent") +" "+ result.getString("prenom_agent")+" : "+ result.getString("matricule_agent"));
        MenuBar.setVisible(true);
        titre.setText("Administracteur");
            Repaindre(pan_base, pan_dir);
            Repaindre(pan_mouv, pan_admi);
        
         }
                   else if (result.getString("nom_user").equals("Direction technique")){
        nom_user.setText(result.getString("nom_agent") +" "+ result.getString("prenom_agent")+" : "+ result.getString("matricule_agent"));
        MenuBar.setVisible(true);
        titre.setText("Direction technique");
            Repaindre(pan_base, pan_dir);
            Repaindre(pan_mouv, pan_tech);
        
                            }
                    else if (result.getString("nom_user").equals("Direction du personnel")){
        nom_user.setText(result.getString("nom_agent") +" "+ result.getString("prenom_agent")+" : "+ result.getString("matricule_agent"));
        MenuBar.setVisible(true);
        titre.setText("Direction du personnel");
//            t = new Thread(new sevine());//Thread 
//            t.start();//Thread
            Repaindre(pan_base, pan_dir);
            Repaindre(pan_mouv, pan_personnel);
        
                          }
                    else if (result.getString("nom_user").equals("Coordination radio")){
        nom_user.setText(result.getString("nom_agent") +" "+ result.getString("prenom_agent")+" : "+ result.getString("matricule_agent"));
        MenuBar.setVisible(true);
        titre.setText("Coordination radio");
            Repaindre(pan_base, pan_dir);
            Repaindre(pan_mouv, pan_tele); 
                    }
                    else if (result.getString("nom_user").equals("Coordination télé")){
        nom_user.setText(result.getString("nom_agent") +" "+ result.getString("prenom_agent")+" : "+ result.getString("matricule_agent"));
        MenuBar.setVisible(true);
        titre.setText("Coordination télé");
            Repaindre(pan_base, pan_dir);
            Repaindre(pan_mouv, pan_tele);
                 
                    }
                    else if (result.getString("nom_user").equals("Authentification")){
        nom_user.setText(result.getString("nom_agent") +" "+ result.getString("prenom_agent")+" : "+ result.getString("matricule_agent"));
        MenuBar.setVisible(true);
        titre.setText("Authentification");
            Repaindre(pan_base, pan_dir);
            Repaindre(pan_mouv, pan_presence);
                
                    }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
private void verify() {
//    
        if (w.bst(mot_de_passe.getText())) {
           m.setText("Mot de passe vide");
           m.setVisible(true);
        } else {
            try {
          
                Statement state = Connexion.getInstance().createStatement();
                String l = "SELECT * FROM t_user";
                ResultSet r = state.executeQuery(l);
                while (r.next()) { 
                    if (r.getString("passwd_user").equals(mot_de_passe.getText())) {
                        we= true;
                        
                        break;
                    } else {
                        we = false;
                    }
                }
            } catch (Exception e) {
            }
            if (we == true) {
yes.setVisible(true);
m.setVisible(false);
MenuBar.setVisible(true);
// Un if au moment opportun

identification();
// Ajout PANEL Suivant
            } else {
                m.setText("Mot de passe incorrect");
                m.setVisible(true);
                yes.setVisible(false);
                mot_de_passe.setText("");
            }
        }

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.ButtonGroup();
        bg_Radio = new javax.swing.ButtonGroup();
        pan = new javax.swing.JPanel();
        pan_base = new javax.swing.JPanel();
        pan_dir = new javax.swing.JPanel();
        pan_titre = new javax.swing.JPanel();
        titre = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nom_user = new javax.swing.JLabel();
        nom_user1 = new javax.swing.JLabel();
        pan_mouv = new javax.swing.JPanel();
        pan_tech = new javax.swing.JPanel();
        pan_tech_bouton = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        pan_mouv_tech = new javax.swing.JPanel();
        pan_tech_accueil = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jPanel74 = new javax.swing.JPanel();
        jLabel177 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        jLabel121 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        pan_tech_enr = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_caracteristique = new javax.swing.JTextArea();
        txt_type_materiel = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jLabel191 = new javax.swing.JLabel();
        cmbo_emplacement = new javax.swing.JComboBox();
        jLabel149 = new javax.swing.JLabel();
        cmbo_disponibilite = new javax.swing.JComboBox();
        jLabel179 = new javax.swing.JLabel();
        pan_tech_es = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cmbo_empl_ev = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cmbo_mat_ev = new javax.swing.JComboBox();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        cbo_agent_ev = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        txt_preagent_tech = new javax.swing.JTextField();
        cbo_Postagent_ev = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        quantite_ev = new javax.swing.JComboBox();
        heure_ev = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmboJour = new javax.swing.JComboBox();
        cmboEtatMateriel_ev = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cdarDate_ev = new com.toedter.calendar.JDateChooser();
        jPanel40 = new javax.swing.JPanel();
        jButton56 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jLabel194 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cmbo_quantite_sorti = new javax.swing.JComboBox();
        heureSortie = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jour_sorti = new javax.swing.JComboBox();
        etat_mat_sortie = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        date_sortie = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        cmbo_empl_sor = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cmbo_mat_evSor = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        cmbo_agent_Sorti = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        txt_prenomSorti = new javax.swing.JTextField();
        cmbo_Postagent_Sorti = new javax.swing.JComboBox();
        jPanel15 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel195 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jLabel197 = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        pan_tech_stock = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tble_stockMat = new javax.swing.JTable();
        jPanel76 = new javax.swing.JPanel();
        Rech_Stock = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        txt_stockRecherche = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jButton111 = new javax.swing.JButton();
        jLabel259 = new javax.swing.JLabel();
        jLabel188 = new javax.swing.JLabel();
        pan_tech_gestion = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tble_gestionMat = new javax.swing.JTable();
        jPanel49 = new javax.swing.JPanel();
        jButton19 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton66 = new javax.swing.JButton();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jLabel185 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        pan_tech_emplacement = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tble_emplacement = new javax.swing.JTable();
        jLabel140 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jButton53 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jLabel200 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jLabel139 = new javax.swing.JLabel();
        txt_emplacement = new javax.swing.JTextField();
        jButton50 = new javax.swing.JButton();
        jLabel199 = new javax.swing.JLabel();
        pan_presence = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel37 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        cbo_agent_presence = new javax.swing.JComboBox();
        cbo_post_presence = new javax.swing.JComboBox();
        txt_prenomAg_pres = new javax.swing.JTextField();
        jPanel67 = new javax.swing.JPanel();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jLabel208 = new javax.swing.JLabel();
        jLabel207 = new javax.swing.JLabel();
        jLabel209 = new javax.swing.JLabel();
        jPanel63 = new javax.swing.JPanel();
        jPanel69 = new javax.swing.JPanel();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        jPanel70 = new javax.swing.JPanel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        cbo_agent_presence1 = new javax.swing.JComboBox();
        cbo_post_presence1 = new javax.swing.JComboBox();
        txt_prenomAg_pres1 = new javax.swing.JTextField();
        jLabel212 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox();
        jTextField6 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jButton107 = new javax.swing.JButton();
        jButton106 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jPanel78 = new javax.swing.JPanel();
        jButton104 = new javax.swing.JButton();
        jButton105 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tble_presence = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jButton36 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jLabel214 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        date_presence = new javax.swing.JLabel();
        heure_presence = new javax.swing.JLabel();
        pan_personnel = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel21 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        txt_postnom_ag = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        cdar_datenaissance_ag = new com.toedter.calendar.JDateChooser();
        txt_prenom_ag = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        txt_marié_ag = new javax.swing.JTextField();
        txt_nom_ag = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txt_nompere_ag = new javax.swing.JTextField();
        txt_lieunaiss_ag = new javax.swing.JTextField();
        cbo_etat_civil = new javax.swing.JComboBox();
        jLabel57 = new javax.swing.JLabel();
        txt_matricule_ag = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        txt_nommere_ag = new javax.swing.JTextField();
        txt_nmbreenfant_ag = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        txt_nationalite_ag = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        Sexe = new javax.swing.JLabel();
        cbo_sexe = new javax.swing.JComboBox();
        jPanel24 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        txt_avenue_ag = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        txt_district_ag = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        txt_province_ag = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        txt_tel_ag = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        txt_teritoire_ag = new javax.swing.JTextField();
        txt_quartier_ag = new javax.swing.JTextField();
        txt_email_ag = new javax.swing.JTextField();
        txt_numadr_ag = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        txt_commune_ag = new javax.swing.JComboBox();
        jLabel73 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        txt_secteur_ag = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        txt_numtelparrain_ag = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        txt_prenomparrain_ag = new javax.swing.JTextField();
        cb_nomparrain_ag = new javax.swing.JComboBox();
        jPanel26 = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        tbCamera = new javax.swing.JToggleButton();
        jButton65 = new javax.swing.JButton();
        jButton98 = new javax.swing.JButton();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        txt_tel_urgent = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        txt_quartier_urgent = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        txt_avenue_urgent = new javax.swing.JTextField();
        txt_num_urgent = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        txt_commune_urgent = new javax.swing.JComboBox();
        cbo_degre_urgent = new javax.swing.JComboBox();
        jPanel33 = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        cdar_datedebut_ag = new com.toedter.calendar.JDateChooser();
        jLabel107 = new javax.swing.JLabel();
        cbo_fonction_ag = new javax.swing.JComboBox();
        jLabel33 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jButton23 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        txt_nomEnfant = new javax.swing.JTextField();
        txt_prenomEnfant = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        cbo_agent_Enfant = new javax.swing.JComboBox();
        jLabel102 = new javax.swing.JLabel();
        txt_prenomag_enfant = new javax.swing.JTextField();
        txt_postNomEnfant = new javax.swing.JTextField();
        jLabel148 = new javax.swing.JLabel();
        txt_postNom_enfant = new javax.swing.JComboBox();
        jPanel48 = new javax.swing.JPanel();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jLabel215 = new javax.swing.JLabel();
        jLabel216 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        tble_enfant = new javax.swing.JTable();
        jPanel65 = new javax.swing.JPanel();
        jButton101 = new javax.swing.JButton();
        jButton99 = new javax.swing.JButton();
        jButton100 = new javax.swing.JButton();
        jLabel218 = new javax.swing.JLabel();
        jLabel217 = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        titre1 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jTextField36 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jTextField37 = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jTextField40 = new javax.swing.JTextField();
        jTextField41 = new javax.swing.JTextField();
        jComboBox17 = new javax.swing.JComboBox();
        jLabel90 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jTextField45 = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox();
        jLabel99 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel100 = new javax.swing.JLabel();
        jPanel81 = new javax.swing.JPanel();
        jButton21 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jLabel224 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tble_personnel = new javax.swing.JTable();
        jPanel47 = new javax.swing.JPanel();
        jButton29 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel227 = new javax.swing.JLabel();
        jLabel228 = new javax.swing.JLabel();
        jLabel203 = new javax.swing.JLabel();
        jPanel86 = new javax.swing.JPanel();
        Rech_Stock1 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        txt_stockRecherche1 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel260 = new javax.swing.JLabel();
        jLabel261 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tble_fonction = new javax.swing.JTable();
        jPanel42 = new javax.swing.JPanel();
        jButton59 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jLabel230 = new javax.swing.JLabel();
        jLabel231 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        txt_fonction = new javax.swing.JTextField();
        jButton57 = new javax.swing.JButton();
        jLabel142 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel146 = new javax.swing.JLabel();
        txt_numtel_parrain = new javax.swing.JTextField();
        txt_nom_parrain = new javax.swing.JTextField();
        jLabel145 = new javax.swing.JLabel();
        txt_prenom_parrain = new javax.swing.JTextField();
        jLabel144 = new javax.swing.JLabel();
        code = new javax.swing.JComboBox();
        plus = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tble_parrain = new javax.swing.JTable();
        jPanel45 = new javax.swing.JPanel();
        jButton63 = new javax.swing.JButton();
        jButton62 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jLabel235 = new javax.swing.JLabel();
        jLabel234 = new javax.swing.JLabel();
        jLabel233 = new javax.swing.JLabel();
        jLabel236 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        pan_tele = new javax.swing.JPanel();
        pan_tele_bouton = new javax.swing.JPanel();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton74 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        pan_mouv_tele = new javax.swing.JPanel();
        pan_tele_accueil = new javax.swing.JPanel();
        pan_tele_accueil1 = new javax.swing.JPanel();
        jLabel250 = new javax.swing.JLabel();
        jLabel251 = new javax.swing.JLabel();
        jLabel252 = new javax.swing.JLabel();
        jLabel253 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        jLabel254 = new javax.swing.JLabel();
        jLabel255 = new javax.swing.JLabel();
        jPanel84 = new javax.swing.JPanel();
        jLabel256 = new javax.swing.JLabel();
        jLabel257 = new javax.swing.JLabel();
        pan_tele_act = new javax.swing.JPanel();
        jLabel134 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jButton67 = new javax.swing.JButton();
        jButton68 = new javax.swing.JButton();
        jLabel237 = new javax.swing.JLabel();
        jLabel238 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tble_activite = new javax.swing.JTable();
        jPanel53 = new javax.swing.JPanel();
        jButton69 = new javax.swing.JButton();
        jButton70 = new javax.swing.JButton();
        jButton71 = new javax.swing.JButton();
        jLabel239 = new javax.swing.JLabel();
        jLabel240 = new javax.swing.JLabel();
        jLabel241 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        cbo_activite_type = new javax.swing.JComboBox();
        txt_activite_nom = new javax.swing.JTextField();
        jLabel135 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txt_activite_description = new javax.swing.JTextArea();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        pan_tele_affi = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tble_tele = new javax.swing.JTable();
        jLabel138 = new javax.swing.JLabel();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jPanel68 = new javax.swing.JPanel();
        jButton72 = new javax.swing.JButton();
        cdar_dateRecherche = new com.toedter.calendar.JDateChooser();
        jButton73 = new javax.swing.JButton();
        jLabel154 = new javax.swing.JLabel();
        jLabel242 = new javax.swing.JLabel();
        jLabel243 = new javax.swing.JLabel();
        pan_tele_prog = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        nomActi_pro = new javax.swing.JComboBox();
        typeAgent_pro = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        txt_prenomAgent_pro = new javax.swing.JTextField();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        cbo_nomAgent_pro = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cbo_postNom_pro = new javax.swing.JComboBox();
        jPanel51 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel244 = new javax.swing.JLabel();
        jLabel245 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        cdar_date_pro = new com.toedter.calendar.JDateChooser();
        cbo_redif = new javax.swing.JComboBox();
        jLabel129 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        cdar_dateredif = new com.toedter.calendar.JDateChooser();
        hd = new javax.swing.JComboBox();
        jLabel152 = new javax.swing.JLabel();
        md = new javax.swing.JComboBox();
        jLabel153 = new javax.swing.JLabel();
        mf = new javax.swing.JComboBox();
        hf = new javax.swing.JComboBox();
        pan_tele_gestion = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tble_tele_gestion = new javax.swing.JTable();
        jPanel55 = new javax.swing.JPanel();
        jButton77 = new javax.swing.JButton();
        jButton76 = new javax.swing.JButton();
        jButton75 = new javax.swing.JButton();
        jLabel246 = new javax.swing.JLabel();
        jLabel247 = new javax.swing.JLabel();
        jLabel248 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        pan_tele_horaire = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tble_tele_horaire = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        rb_date = new javax.swing.JRadioButton();
        rb_nom = new javax.swing.JRadioButton();
        cdar_date_horaire = new com.toedter.calendar.JDateChooser();
        txt_nom_horaire = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel249 = new javax.swing.JLabel();
        jLabel258 = new javax.swing.JLabel();
        pan_admi = new javax.swing.JPanel();
        pan_admi_bouton = new javax.swing.JPanel();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        pan_mouv_admi = new javax.swing.JPanel();
        pan_dmin = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel72 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel110 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        nomag_adm = new javax.swing.JComboBox();
        jLabel112 = new javax.swing.JLabel();
        pag_adm = new javax.swing.JComboBox();
        jLabel116 = new javax.swing.JLabel();
        preag_adm = new javax.swing.JTextField();
        jPanel73 = new javax.swing.JPanel();
        jButton103 = new javax.swing.JButton();
        jButton102 = new javax.swing.JButton();
        jLabel205 = new javax.swing.JLabel();
        jLabel206 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        tble_user = new javax.swing.JTable();
        jPanel82 = new javax.swing.JPanel();
        jButton108 = new javax.swing.JButton();
        jButton109 = new javax.swing.JButton();
        jButton110 = new javax.swing.JButton();
        jLabel220 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jLabel226 = new javax.swing.JLabel();
        pan_aut = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mot_de_passe = new javax.swing.JPasswordField();
        ok = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        m = new javax.swing.JLabel();
        yes = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        load = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Brt Appli");

        pan.setBackground(java.awt.Color.black);

        pan_base.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBoxMenuItem.selectionBackground"));
        pan_base.setLayout(new java.awt.CardLayout());

        pan_dir.setBackground(new java.awt.Color(37, 37, 38));

        pan_titre.setBackground(new java.awt.Color(0, 0, 0));
        pan_titre.setPreferredSize(new java.awt.Dimension(1200, 67));
        pan_titre.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        titre.setFont(new java.awt.Font("Segoe UI Light", 0, 28)); // NOI18N
        titre.setForeground(new java.awt.Color(255, 51, 0));
        titre.setText("Nom de la direction");
        pan_titre.add(titre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, -1, -1));

        jPanel50.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/user.png"))); // NOI18N

        nom_user.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        nom_user.setForeground(new java.awt.Color(255, 51, 0));
        nom_user.setText("Nom User");

        nom_user1.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        nom_user1.setForeground(new java.awt.Color(255, 51, 0));
        nom_user1.setText("Se deconnecter");
        nom_user1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nom_user1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(nom_user)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel50Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(nom_user1)))
                .addContainerGap())
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(nom_user)
                .addGap(0, 0, 0)
                .addComponent(jLabel3))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nom_user1)
                .addContainerGap())
        );

        pan_titre.add(jPanel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 0, 200, -1));

        pan_mouv.setLayout(new java.awt.CardLayout());

        pan_tech.setBackground(new java.awt.Color(37, 37, 38));
        pan_tech.setMaximumSize(new java.awt.Dimension(1200, 620));
        pan_tech.setMinimumSize(new java.awt.Dimension(1200, 620));

        pan_tech_bouton.setBackground(new java.awt.Color(0, 0, 0));
        pan_tech_bouton.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0))));

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/enregistre.png"))); // NOI18N
        jButton1.setToolTipText("Enregistrement de materiel");
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/actua.png"))); // NOI18N
        jButton2.setToolTipText("Entrées & Sorties");
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/stock.png"))); // NOI18N
        jButton3.setToolTipText("Verification Stock");
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/gestion.png"))); // NOI18N
        jButton4.setToolTipText("Gestion");
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton49.setBackground(new java.awt.Color(0, 0, 0));
        jButton49.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButton49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/emplacement.png"))); // NOI18N
        jButton49.setToolTipText("Emplacement");
        jButton49.setContentAreaFilled(false);
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_tech_boutonLayout = new javax.swing.GroupLayout(pan_tech_bouton);
        pan_tech_bouton.setLayout(pan_tech_boutonLayout);
        pan_tech_boutonLayout.setHorizontalGroup(
            pan_tech_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_boutonLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(pan_tech_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addGroup(pan_tech_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        pan_tech_boutonLayout.setVerticalGroup(
            pan_tech_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_boutonLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jButton1)
                .addGap(36, 36, 36)
                .addComponent(jButton49)
                .addGap(45, 45, 45)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(38, 38, 38)
                .addComponent(jButton4)
                .addGap(28, 28, 28))
        );

        pan_mouv_tech.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pan_mouv_tech.setLayout(new java.awt.CardLayout());

        pan_tech_accueil.setBackground(new java.awt.Color(0, 0, 0));

        jLabel117.setBackground(new java.awt.Color(51, 153, 255));
        jLabel117.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(255, 51, 0));
        jLabel117.setText("Direction technique");

        jLabel118.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/tele.png"))); // NOI18N

        jLabel119.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/radio.png"))); // NOI18N

        jLabel120.setBackground(new java.awt.Color(51, 255, 51));
        jLabel120.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(255, 255, 255));
        jLabel120.setText("Brt Africa");

        jPanel74.setBackground(new java.awt.Color(0, 0, 0));

        jLabel177.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel177.setForeground(new java.awt.Color(255, 255, 255));
        jLabel177.setText("La voix de l'Afrique");

        jLabel122.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(255, 51, 0));
        jLabel122.setText("Brt Africa la radio: 98.6 Mhz");

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel74Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel177)
                        .addGap(57, 57, 57))
                    .addComponent(jLabel122, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel122)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel177)
                .addContainerGap())
        );

        jPanel75.setBackground(new java.awt.Color(0, 0, 0));

        jLabel121.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(255, 51, 0));
        jLabel121.setText("Brt Africa la télé: 679.20 Mhz");

        jLabel178.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel178.setForeground(new java.awt.Color(255, 255, 255));
        jLabel178.setText("Télé business");

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel75Layout.createSequentialGroup()
                        .addComponent(jLabel178)
                        .addGap(91, 91, 91))
                    .addComponent(jLabel121, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel121)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel178)
                .addContainerGap())
        );

        javax.swing.GroupLayout pan_tech_accueilLayout = new javax.swing.GroupLayout(pan_tech_accueil);
        pan_tech_accueil.setLayout(pan_tech_accueilLayout);
        pan_tech_accueilLayout.setHorizontalGroup(
            pan_tech_accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_accueilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tech_accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tech_accueilLayout.createSequentialGroup()
                        .addComponent(jLabel118)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addComponent(jLabel120)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel119)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tech_accueilLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel117)
                        .addGap(274, 274, 274))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tech_accueilLayout.createSequentialGroup()
                        .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pan_tech_accueilLayout.setVerticalGroup(
            pan_tech_accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_accueilLayout.createSequentialGroup()
                .addGroup(pan_tech_accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tech_accueilLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel117)
                        .addGap(18, 18, 18)
                        .addGroup(pan_tech_accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel118)
                            .addComponent(jLabel119)))
                    .addGroup(pan_tech_accueilLayout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jLabel120)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_tech_accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel74, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel75, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        pan_mouv_tech.add(pan_tech_accueil, "card7");

        pan_tech_enr.setBackground(new java.awt.Color(0, 0, 0));
        pan_tech_enr.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Type Materiel");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Caracteristique");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Emplacement");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txt_caracteristique.setColumns(20);
        txt_caracteristique.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_caracteristique.setRows(5);
        txt_caracteristique.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(txt_caracteristique);

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton6.setContentAreaFilled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton7.setContentAreaFilled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel189.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel189.setForeground(new java.awt.Color(255, 255, 255));
        jLabel189.setText("Enregistrer");

        jLabel190.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel190.setForeground(new java.awt.Color(255, 255, 255));
        jLabel190.setText("Initialiser");

        jLabel191.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel191.setForeground(new java.awt.Color(255, 255, 255));
        jLabel191.setText("Retour");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel189)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel190)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel191))
                    .addComponent(jButton7))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel189)
                    .addComponent(jLabel190)
                    .addComponent(jLabel191))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbo_emplacement.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cmbo_emplacement.setSelectedIndex(-1);
        cmbo_emplacement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbo_emplacementActionPerformed(evt);
            }
        });

        jLabel149.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(255, 255, 255));
        jLabel149.setText("Disponibilité au stock");

        cmbo_disponibilite.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oui", "Non" }));
        cmbo_disponibilite.setSelectedIndex(-1);
        cmbo_disponibilite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbo_disponibiliteActionPerformed(evt);
            }
        });

        jLabel179.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel179.setForeground(new java.awt.Color(255, 51, 0));
        jLabel179.setText("Enregistrement Materiel");

        javax.swing.GroupLayout pan_tech_enrLayout = new javax.swing.GroupLayout(pan_tech_enr);
        pan_tech_enr.setLayout(pan_tech_enrLayout);
        pan_tech_enrLayout.setHorizontalGroup(
            pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_enrLayout.createSequentialGroup()
                .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tech_enrLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pan_tech_enrLayout.createSequentialGroup()
                                .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pan_tech_enrLayout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(55, 55, 55))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tech_enrLayout.createSequentialGroup()
                                                .addComponent(jLabel149)
                                                .addGap(21, 21, 21)))
                                        .addGroup(pan_tech_enrLayout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addGap(62, 62, 62)))
                                    .addGroup(pan_tech_enrLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(61, 61, 61)))
                                .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbo_emplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbo_disponibilite, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_type_materiel, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel179)))
                    .addGroup(pan_tech_enrLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(575, Short.MAX_VALUE))
        );
        pan_tech_enrLayout.setVerticalGroup(
            pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_enrLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel179)
                .addGap(48, 48, 48)
                .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_type_materiel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel149)
                    .addComponent(cmbo_disponibilite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pan_tech_enrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbo_emplacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(60, 60, 60)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pan_mouv_tech.add(pan_tech_enr, "card2");

        pan_tech_es.setBackground(new java.awt.Color(0, 0, 0));
        pan_tech_es.setPreferredSize(new java.awt.Dimension(884, 500));

        jTabbedPane1.setBackground(new java.awt.Color(51, 153, 255));
        jTabbedPane1.setForeground(new java.awt.Color(51, 153, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Info Materiel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        cmbo_empl_ev.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbo_empl_evPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Emplacement");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Nom Materiel");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(cmbo_empl_ev, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(cmbo_mat_ev, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cmbo_empl_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cmbo_mat_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Info Agent", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Nom Agent");

        cbo_agent_ev.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_agent_evPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Postnom");

        jLabel150.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(255, 255, 255));
        jLabel150.setText("Prenom");

        txt_preagent_tech.setEnabled(false);

        cbo_Postagent_ev.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_Postagent_evPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel14)
                    .addComponent(jLabel150))
                .addGap(29, 29, 29)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbo_agent_ev, 0, 102, Short.MAX_VALUE)
                    .addComponent(txt_preagent_tech)
                    .addComponent(cbo_Postagent_ev, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_agent_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_Postagent_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel150)
                    .addComponent(txt_preagent_tech, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jour");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Quantité");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Heure");

        quantite_ev.setEditable(true);
        quantite_ev.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        quantite_ev.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        quantite_ev.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantite_evKeyReleased(evt);
            }
        });

        heure_ev.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        heure_ev.setForeground(new java.awt.Color(255, 255, 255));
        heure_ev.setText("Afficher l'heure");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Etat Materiel");

        cmboJour.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cmboJour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Semedi", "Dimanche" }));

        cmboEtatMateriel_ev.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tres Mauvais", "Mauvais", "Assez Bien", "Bien", "Tres Bien", "Excellent" }));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Date");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel10))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmboJour, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cdarDate_ev, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                .addComponent(heure_ev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(quantite_ev, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmboEtatMateriel_ev, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmboJour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(cdarDate_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(heure_ev))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cmboEtatMateriel_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(quantite_ev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel40.setBackground(new java.awt.Color(0, 0, 0));

        jButton56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton56.setContentAreaFilled(false);

        jButton54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton54.setContentAreaFilled(false);
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        jButton55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton55.setContentAreaFilled(false);
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        jLabel192.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel192.setForeground(new java.awt.Color(255, 255, 255));
        jLabel192.setText("Enregistrer");

        jLabel193.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel193.setForeground(new java.awt.Color(255, 255, 255));
        jLabel193.setText("Initialiser");

        jLabel194.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel194.setForeground(new java.awt.Color(255, 255, 255));
        jLabel194.setText("Retour");

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton54)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel192)))
                .addGap(21, 21, 21)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton55)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel193)))
                .addGap(18, 18, 18)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel194))
                    .addComponent(jButton56))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton54)
                    .addComponent(jButton55)
                    .addComponent(jButton56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel192)
                    .addComponent(jLabel193)
                    .addComponent(jLabel194))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel181.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        jLabel181.setForeground(new java.awt.Color(255, 51, 0));
        jLabel181.setText("Entrée du materiel");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel181))
                .addContainerGap(390, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel181)
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jTabbedPane1.addTab("Entrée", jPanel6);

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Jour");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Quantité");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Heure");

        cmbo_quantite_sorti.setEditable(true);
        cmbo_quantite_sorti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        cmbo_quantite_sorti.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        heureSortie.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        heureSortie.setForeground(new java.awt.Color(255, 255, 255));
        heureSortie.setText("Afficher l'heure");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Etat Materiel");

        jour_sorti.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jour_sorti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Semedi", "Dimanche" }));

        etat_mat_sortie.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tres Mauvais", "Mauvais", "Assez Bien", "Bien", "Tres Bien", "Excellent" }));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Date");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel23)
                            .addComponent(jLabel21))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jour_sorti, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(date_sortie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(heureSortie, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etat_mat_sortie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbo_quantite_sorti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jour_sorti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(date_sortie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(heureSortie))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(etat_mat_sortie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cmbo_quantite_sorti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Info Materiel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        cmbo_empl_sor.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbo_empl_sorPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Emplacement");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Nom Materiel");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(cmbo_empl_sor, 0, 111, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(cmbo_mat_evSor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(cmbo_empl_sor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cmbo_mat_evSor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Agent", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Nom Agent");

        cmbo_agent_Sorti.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbo_agent_SortiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("PostNom");

        jLabel151.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(255, 255, 255));
        jLabel151.setText("Prenom");

        txt_prenomSorti.setEnabled(false);

        cmbo_Postagent_Sorti.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbo_Postagent_SortiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel24)
                    .addComponent(jLabel151))
                .addGap(38, 38, 38)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbo_agent_Sorti, 0, 102, Short.MAX_VALUE)
                    .addComponent(txt_prenomSorti)
                    .addComponent(cmbo_Postagent_Sorti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbo_agent_Sorti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(cmbo_Postagent_Sorti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel151)
                    .addComponent(txt_prenomSorti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton8.setContentAreaFilled(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton9.setContentAreaFilled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton10.setContentAreaFilled(false);

        jLabel195.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel195.setForeground(new java.awt.Color(255, 255, 255));
        jLabel195.setText("Retour");

        jLabel196.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel196.setForeground(new java.awt.Color(255, 255, 255));
        jLabel196.setText("Initialiser");

        jLabel197.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel197.setForeground(new java.awt.Color(255, 255, 255));
        jLabel197.setText("Enregistrer");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel197)))
                .addGap(26, 26, 26)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel196)))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel195)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addComponent(jButton10)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel197)
                    .addComponent(jLabel196)
                    .addComponent(jLabel195))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel182.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N
        jLabel182.setForeground(new java.awt.Color(255, 51, 0));
        jLabel182.setText("Sortie du materiel");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel182)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(353, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel182)
                .addGap(29, 29, 29)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sortie", jPanel7);

        jTabbedPane1.setSelectedIndex(-1);

        jLabel180.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel180.setForeground(new java.awt.Color(255, 51, 0));
        jLabel180.setText("Evenement Materiel");
        jLabel180.setToolTipText("Evenement Materiel");

        javax.swing.GroupLayout pan_tech_esLayout = new javax.swing.GroupLayout(pan_tech_es);
        pan_tech_es.setLayout(pan_tech_esLayout);
        pan_tech_esLayout.setHorizontalGroup(
            pan_tech_esLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_esLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tech_esLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(pan_tech_esLayout.createSequentialGroup()
                        .addComponent(jLabel180)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pan_tech_esLayout.setVerticalGroup(
            pan_tech_esLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_esLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel180)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pan_mouv_tech.add(pan_tech_es, "card3");

        pan_tech_stock.setBackground(new java.awt.Color(0, 0, 0));
        pan_tech_stock.setPreferredSize(new java.awt.Dimension(884, 500));

        tble_stockMat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tble_stockMat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tble_stockMatMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tble_stockMat);

        jPanel76.setBackground(new java.awt.Color(0, 0, 0));

        Rech_Stock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        Rech_Stock.setToolTipText("Rechercher");
        Rech_Stock.setContentAreaFilled(false);
        Rech_Stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rech_StockActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/voir tous.png"))); // NOI18N
        jButton12.setToolTipText("Voir tous les enregistrements");
        jButton12.setContentAreaFilled(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        txt_stockRecherche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_stockRechercheKeyPressed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Materiel recherche");

        jLabel186.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel186.setForeground(new java.awt.Color(255, 255, 255));
        jLabel186.setText("Recherche");

        jLabel187.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel187.setForeground(new java.awt.Color(255, 255, 255));
        jLabel187.setText("Voir tous les enregistrements");

        jButton111.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Print.png"))); // NOI18N
        jButton111.setToolTipText("Imprimer");
        jButton111.setContentAreaFilled(false);
        jButton111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton111ActionPerformed(evt);
            }
        });

        jLabel259.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel259.setForeground(new java.awt.Color(255, 255, 255));
        jLabel259.setText("Imprimer");

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(txt_stockRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Rech_Stock)
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel186)))
                .addGap(39, 39, 39)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addComponent(jButton12)
                        .addGap(52, 52, 52))
                    .addComponent(jLabel187))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton111)
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel259)))
                .addGap(45, 45, 45))
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel76Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txt_stockRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton12)
                    .addComponent(Rech_Stock)
                    .addComponent(jButton111))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel187)
                        .addComponent(jLabel259))
                    .addComponent(jLabel186))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel188.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel188.setForeground(new java.awt.Color(255, 51, 0));
        jLabel188.setText("Verification Stock");

        javax.swing.GroupLayout pan_tech_stockLayout = new javax.swing.GroupLayout(pan_tech_stock);
        pan_tech_stock.setLayout(pan_tech_stockLayout);
        pan_tech_stockLayout.setHorizontalGroup(
            pan_tech_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_stockLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tech_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                    .addGroup(pan_tech_stockLayout.createSequentialGroup()
                        .addGroup(pan_tech_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel188)
                            .addComponent(jPanel76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pan_tech_stockLayout.setVerticalGroup(
            pan_tech_stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_stockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel188)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel76, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pan_mouv_tech.add(pan_tech_stock, "card4");

        pan_tech_gestion.setBackground(new java.awt.Color(0, 0, 0));
        pan_tech_gestion.setPreferredSize(new java.awt.Dimension(884, 500));

        tble_gestionMat.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tble_gestionMat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tble_gestionMat);

        jPanel49.setBackground(new java.awt.Color(0, 0, 0));

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton19.setToolTipText("Modifier");
        jButton19.setContentAreaFilled(false);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton28.setToolTipText("Supprimer");
        jButton28.setContentAreaFilled(false);
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        jButton66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton66.setToolTipText("Accueil");
        jButton66.setContentAreaFilled(false);
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });

        jLabel183.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel183.setForeground(new java.awt.Color(255, 255, 255));
        jLabel183.setText("Modifier");

        jLabel184.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel184.setForeground(new java.awt.Color(255, 255, 255));
        jLabel184.setText("Supprimer");

        jLabel185.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel185.setForeground(new java.awt.Color(255, 255, 255));
        jLabel185.setText("Retour");

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton19)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel183)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton28)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel184)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton66)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel185)))
                .addContainerGap())
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel49Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton28)
                    .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton19)
                        .addComponent(jButton66)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel183)
                    .addComponent(jLabel184)
                    .addComponent(jLabel185))
                .addGap(28, 28, 28))
        );

        jLabel123.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(255, 51, 0));
        jLabel123.setText("Gestion de Stock");

        javax.swing.GroupLayout pan_tech_gestionLayout = new javax.swing.GroupLayout(pan_tech_gestion);
        pan_tech_gestion.setLayout(pan_tech_gestionLayout);
        pan_tech_gestionLayout.setHorizontalGroup(
            pan_tech_gestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_gestionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tech_gestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tech_gestionLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(pan_tech_gestionLayout.createSequentialGroup()
                        .addGroup(pan_tech_gestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel123)
                            .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pan_tech_gestionLayout.setVerticalGroup(
            pan_tech_gestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_gestionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel123)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pan_mouv_tech.add(pan_tech_gestion, "card4");

        pan_tech_emplacement.setBackground(new java.awt.Color(0, 0, 0));

        tble_emplacement.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane10.setViewportView(tble_emplacement);

        jLabel140.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(255, 51, 0));
        jLabel140.setText("Liste des emplacements");

        jPanel35.setBackground(new java.awt.Color(0, 0, 0));

        jButton53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton53.setToolTipText("Retour");
        jButton53.setContentAreaFilled(false);
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });

        jButton52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton52.setToolTipText("Supprimer");
        jButton52.setContentAreaFilled(false);
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });

        jButton51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton51.setToolTipText("Modifier");
        jButton51.setContentAreaFilled(false);
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });

        jLabel200.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel200.setForeground(new java.awt.Color(255, 255, 255));
        jLabel200.setText("Modifier");

        jLabel201.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel201.setForeground(new java.awt.Color(255, 255, 255));
        jLabel201.setText("Supprimer");

        jLabel202.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel202.setForeground(new java.awt.Color(255, 255, 255));
        jLabel202.setText("Retour");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton51)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel200)))
                .addGap(18, 18, 18)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel201)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel202))
                    .addGroup(jPanel35Layout.createSequentialGroup()
                        .addComponent(jButton52)
                        .addGap(18, 18, 18)
                        .addComponent(jButton53)))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton51)
                    .addComponent(jButton52)
                    .addComponent(jButton53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel202)
                        .addComponent(jLabel201))
                    .addComponent(jLabel200))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel198.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel198.setForeground(new java.awt.Color(255, 51, 0));
        jLabel198.setText("Enregistrement des emplacements");

        jPanel77.setBackground(new java.awt.Color(0, 0, 0));

        jLabel139.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(255, 255, 255));
        jLabel139.setText("Emplacement");

        txt_emplacement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emplacementActionPerformed(evt);
            }
        });
        txt_emplacement.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_emplacementKeyPressed(evt);
            }
        });

        jButton50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton50.setToolTipText("Enregistrer");
        jButton50.setContentAreaFilled(false);
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });

        jLabel199.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel199.setForeground(new java.awt.Color(255, 255, 255));
        jLabel199.setText("Enregistrer");

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel77Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel139)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_emplacement, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel77Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel199))
                    .addComponent(jButton50))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel77Layout.createSequentialGroup()
                .addGroup(jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel77Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel139)
                            .addComponent(txt_emplacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel77Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton50)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel199)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pan_tech_emplacementLayout = new javax.swing.GroupLayout(pan_tech_emplacement);
        pan_tech_emplacement.setLayout(pan_tech_emplacementLayout);
        pan_tech_emplacementLayout.setHorizontalGroup(
            pan_tech_emplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_emplacementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tech_emplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tech_emplacementLayout.createSequentialGroup()
                        .addGroup(pan_tech_emplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                            .addGroup(pan_tech_emplacementLayout.createSequentialGroup()
                                .addComponent(jLabel198)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(pan_tech_emplacementLayout.createSequentialGroup()
                        .addGroup(pan_tech_emplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel140))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );
        pan_tech_emplacementLayout.setVerticalGroup(
            pan_tech_emplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tech_emplacementLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel198)
                .addGap(32, 32, 32)
                .addGroup(pan_tech_emplacementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pan_tech_emplacementLayout.createSequentialGroup()
                        .addComponent(jPanel77, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel140))
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pan_mouv_tech.add(pan_tech_emplacement, "card6");

        javax.swing.GroupLayout pan_techLayout = new javax.swing.GroupLayout(pan_tech);
        pan_tech.setLayout(pan_techLayout);
        pan_techLayout.setHorizontalGroup(
            pan_techLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_techLayout.createSequentialGroup()
                .addComponent(pan_tech_bouton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(pan_mouv_tech, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pan_techLayout.setVerticalGroup(
            pan_techLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_techLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_techLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan_mouv_tech, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pan_tech_bouton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pan_mouv.add(pan_tech, "card3");

        pan_presence.setBackground(new java.awt.Color(0, 0, 0));
        pan_presence.setMaximumSize(new java.awt.Dimension(1200, 620));
        pan_presence.setMinimumSize(new java.awt.Dimension(1200, 620));

        jLabel34.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 51, 0));
        jLabel34.setText("Presence Journalière");

        jTabbedPane2.setForeground(new java.awt.Color(51, 153, 255));
        jTabbedPane2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        jLabel35.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 51, 0));
        jLabel35.setText("Presence employée");

        jTabbedPane4.setBackground(new java.awt.Color(51, 153, 255));
        jTabbedPane4.setForeground(new java.awt.Color(51, 153, 255));
        jTabbedPane4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane4MouseClicked(evt);
            }
        });

        jPanel37.setBackground(new java.awt.Color(0, 0, 0));

        jPanel66.setBackground(new java.awt.Color(0, 0, 0));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Nom agent");

        jLabel108.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(255, 255, 255));
        jLabel108.setText("PostNom agent");

        jLabel109.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(255, 255, 255));
        jLabel109.setText("Prenom agent");

        cbo_agent_presence.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_agent_presencePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        cbo_post_presence.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_post_presencePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txt_prenomAg_pres.setEnabled(false);

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel108)
                    .addComponent(jLabel109))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbo_agent_presence, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbo_post_presence, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_prenomAg_pres, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(cbo_agent_presence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel108)
                    .addComponent(cbo_post_presence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel109)
                    .addComponent(txt_prenomAg_pres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel67.setBackground(new java.awt.Color(0, 0, 0));

        jButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton32.setToolTipText("Enregistrer");
        jButton32.setContentAreaFilled(false);
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton33.setToolTipText("Initialiser");
        jButton33.setContentAreaFilled(false);

        jLabel208.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel208.setForeground(new java.awt.Color(255, 255, 255));
        jLabel208.setText("Initialiser");

        jLabel207.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel207.setForeground(new java.awt.Color(255, 255, 255));
        jLabel207.setText("Enregistrer");

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton32)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel207)))
                .addGap(18, 18, 18)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel208))
                    .addComponent(jButton33))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton32)
                    .addComponent(jButton33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel207)
                    .addComponent(jLabel208))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel209.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel209.setForeground(new java.awt.Color(255, 51, 0));
        jLabel209.setText("Presence journalière : Entrée");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel209))
                .addContainerGap(854, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel209)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Entrée de l'Agent", jPanel37);

        jPanel63.setBackground(new java.awt.Color(0, 0, 0));

        jPanel69.setBackground(new java.awt.Color(0, 0, 0));

        jButton34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton34.setToolTipText("Enregistrer");
        jButton34.setContentAreaFilled(false);
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jButton35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton35.setToolTipText("Initialiser");
        jButton35.setContentAreaFilled(false);
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jLabel210.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel210.setForeground(new java.awt.Color(255, 255, 255));
        jLabel210.setText("Enregistrer");

        jLabel211.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel211.setForeground(new java.awt.Color(255, 255, 255));
        jLabel211.setText("Initialiser");

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel69Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton34)
                    .addGroup(jPanel69Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel210)))
                .addGap(18, 18, 18)
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel69Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel211))
                    .addComponent(jButton35))
                .addContainerGap())
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel69Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton34)
                    .addComponent(jButton35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel210)
                    .addComponent(jLabel211))
                .addContainerGap())
        );

        jPanel70.setBackground(new java.awt.Color(0, 0, 0));

        jLabel113.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(255, 255, 255));
        jLabel113.setText("Nom agent");

        jLabel114.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(255, 255, 255));
        jLabel114.setText("PostNom agent");

        jLabel115.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(255, 255, 255));
        jLabel115.setText("Prenom agent");

        cbo_agent_presence1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_agent_presence1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        cbo_post_presence1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_post_presence1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txt_prenomAg_pres1.setEnabled(false);

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel113)
                    .addComponent(jLabel114)
                    .addComponent(jLabel115))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbo_post_presence1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 130, Short.MAX_VALUE)
                    .addComponent(cbo_agent_presence1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_prenomAg_pres1))
                .addContainerGap())
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel113)
                    .addComponent(cbo_agent_presence1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114)
                    .addComponent(cbo_post_presence1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel115)
                    .addComponent(txt_prenomAg_pres1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel212.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel212.setForeground(new java.awt.Color(255, 51, 0));
        jLabel212.setText("Presence journalière : Sortie");

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel212))
                .addContainerGap(861, Short.MAX_VALUE))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel212)
                .addGap(26, 26, 26)
                .addComponent(jPanel70, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jPanel69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Sortie de l'Agent", jPanel63);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane4))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Employé", jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Identité", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Post Nom");

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Nom Stagaire");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Prenom");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40)
                            .addComponent(jLabel42))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField5)
                            .addComponent(jComboBox14, 0, 131, Short.MAX_VALUE)
                            .addComponent(jTextField6)))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel44.setFont(new java.awt.Font("Segoe UI Light", 0, 21)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 51, 0));
        jLabel44.setText("Presence stageaire");

        jPanel79.setBackground(new java.awt.Color(0, 0, 0));

        jButton107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton107.setToolTipText("Supprimer");
        jButton107.setContentAreaFilled(false);

        jButton106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton106.setContentAreaFilled(false);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Enregistrer");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Initialiser");

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel79Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton106)
                    .addGroup(jPanel79Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel18)))
                .addGap(35, 35, 35)
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel79Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel27))
                    .addComponent(jButton107))
                .addContainerGap())
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel79Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton106)
                    .addComponent(jButton107))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel27))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addGap(399, 1001, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel44)
                .addGap(43, 43, 43)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(jPanel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Stagiaire", jPanel5);

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));

        jLabel49.setFont(new java.awt.Font("Segoe UI Light", 0, 21)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 51, 0));
        jLabel49.setText("Presence visiteur");

        jLabel50.setText("Heure");

        jLabel51.setText("Afficher Date");

        jPanel19.setBackground(new java.awt.Color(0, 0, 0));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Nom");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Sexe");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Prenom");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M", "F" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addComponent(jTextField7))
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel78.setBackground(new java.awt.Color(0, 0, 0));

        jButton104.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton104.setToolTipText("Enregistrer");
        jButton104.setContentAreaFilled(false);

        jButton105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton105.setToolTipText("Supprimer");
        jButton105.setContentAreaFilled(false);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Enregistrer");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Supprimer");

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton104)
                    .addGroup(jPanel78Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton105)
                    .addGroup(jPanel78Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9)))
                .addGap(30, 30, 30))
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton105, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton104))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 786, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(jLabel50))
                        .addGap(169, 169, 169))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel49))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel50)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel51)))
                .addGap(51, 51, 51)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(jPanel78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Visiteur", jPanel16);

        jPanel20.setBackground(new java.awt.Color(0, 0, 0));

        tble_presence.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tble_presence);

        jLabel52.setFont(new java.awt.Font("Segoe UI Light", 0, 21)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 51, 0));
        jLabel52.setText("Modification");

        jPanel64.setBackground(new java.awt.Color(0, 0, 0));

        jButton36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton36.setToolTipText("Supprimer");
        jButton36.setContentAreaFilled(false);
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton17.setToolTipText("Modifier");
        jButton17.setContentAreaFilled(false);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel214.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel214.setForeground(new java.awt.Color(255, 255, 255));
        jLabel214.setText("Supprimer");

        jLabel213.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel213.setForeground(new java.awt.Color(255, 255, 255));
        jLabel213.setText("Modifier");

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel213)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel214))
                    .addGroup(jPanel64Layout.createSequentialGroup()
                        .addComponent(jButton17)
                        .addGap(38, 38, 38)
                        .addComponent(jButton36)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel213)
                    .addComponent(jLabel214))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1155, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(0, 1047, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(367, 367, 367)
                .addComponent(jPanel64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Modification", jPanel20);

        date_presence.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        date_presence.setForeground(new java.awt.Color(255, 255, 255));
        date_presence.setText("Afficher Date");

        heure_presence.setBackground(new java.awt.Color(255, 255, 255));
        heure_presence.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        heure_presence.setForeground(new java.awt.Color(255, 255, 255));
        heure_presence.setText("Heure");

        javax.swing.GroupLayout pan_presenceLayout = new javax.swing.GroupLayout(pan_presence);
        pan_presence.setLayout(pan_presenceLayout);
        pan_presenceLayout.setHorizontalGroup(
            pan_presenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_presenceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_presenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pan_presenceLayout.createSequentialGroup()
                        .addComponent(jTabbedPane2)
                        .addContainerGap())
                    .addGroup(pan_presenceLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(345, 345, 345)
                        .addComponent(date_presence)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(heure_presence)
                        .addGap(222, 222, 222))))
        );
        pan_presenceLayout.setVerticalGroup(
            pan_presenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_presenceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_presenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_presenceLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_presenceLayout.createSequentialGroup()
                        .addGroup(pan_presenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(heure_presence)
                            .addComponent(date_presence))
                        .addGap(18, 18, 18)))
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        pan_mouv.add(pan_presence, "card4");

        pan_personnel.setMaximumSize(new java.awt.Dimension(1200, 620));
        pan_personnel.setMinimumSize(new java.awt.Dimension(1200, 620));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel53.setText("Enregistrement du personnel");

        jTabbedPane3.setBackground(new java.awt.Color(37, 37, 38));
        jTabbedPane3.setForeground(new java.awt.Color(51, 153, 255));
        jTabbedPane3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane3MouseClicked(evt);
            }
        });

        jPanel21.setBackground(new java.awt.Color(0, 0, 0));

        jPanel23.setBackground(new java.awt.Color(0, 0, 0));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Post Nom");

        jLabel65.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("Nationalité");

        cdar_datenaissance_ag.setDateFormatString("d-MM-yyyy");

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Etat civil");

        txt_marié_ag.setEnabled(false);

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Lieu de naissance");

        cbo_etat_civil.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        cbo_etat_civil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Celibataire", "Marié", "Divorcé" }));
        cbo_etat_civil.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_etat_civilPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Prenom");

        txt_matricule_ag.setEnabled(false);

        jLabel54.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Matricule");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Nom");

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Nom du pere");

        txt_nmbreenfant_ag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nmbreenfant_agActionPerformed(evt);
            }
        });
        txt_nmbreenfant_ag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nmbreenfant_agKeyReleased(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("Date de naissance");

        jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Marié a");

        jLabel64.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Nombre d'enfant");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setText("Nom de la mère");

        Sexe.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        Sexe.setForeground(new java.awt.Color(255, 255, 255));
        Sexe.setText("Sexe");

        cbo_sexe.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M", "F" }));
        cbo_sexe.setSelectedIndex(-1);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addComponent(jLabel55))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_matricule_ag, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nom_ag, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61)
                            .addComponent(jLabel62)
                            .addComponent(jLabel63))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_etat_civil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_nommere_ag)
                            .addComponent(txt_marié_ag, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(jLabel65)
                            .addComponent(Sexe))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nationalite_ag, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nmbreenfant_ag)
                            .addComponent(cbo_sexe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel58)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59)
                            .addComponent(jLabel60)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_postnom_ag)
                            .addComponent(txt_lieunaiss_ag, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_prenom_ag, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cdar_datenaissance_ag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_nompere_ag, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel23Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbo_etat_civil, cbo_sexe, cdar_datenaissance_ag, txt_lieunaiss_ag, txt_marié_ag, txt_matricule_ag, txt_nationalite_ag, txt_nmbreenfant_ag, txt_nom_ag, txt_nommere_ag, txt_nompere_ag, txt_postnom_ag, txt_prenom_ag});

        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txt_matricule_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nom_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_postnom_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_prenom_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58)
                    .addComponent(txt_lieunaiss_ag, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(cdar_datenaissance_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nompere_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nommere_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel61)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_etat_civil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_marié_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nmbreenfant_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nationalite_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65))
                .addGap(9, 9, 9)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_sexe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Sexe)))
        );

        jPanel23Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbo_etat_civil, cbo_sexe, cdar_datenaissance_ag, txt_lieunaiss_ag, txt_marié_ag, txt_matricule_ag, txt_nationalite_ag, txt_nmbreenfant_ag, txt_nom_ag, txt_nommere_ag, txt_nompere_ag, txt_postnom_ag, txt_prenom_ag});

        jPanel24.setBackground(new java.awt.Color(0, 0, 0));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Email");

        jLabel67.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("District d'origine");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("N°");

        jLabel68.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Territoire d'origine");

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Telephone");

        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("Commune");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Avenue");

        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Province d'Origine");

        txt_commune_ag.setEditable(true);
        txt_commune_ag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bandal", "Barumbu", "Bumbu", "Gombe", "Kalamu", "Kasa vubu", "Kimbaseke", "Kinsenso", "Kinshasa", "Kintambo", "Lemba", "Limete", "Lingwala", "Makala", "Maluku", "Masina", "Matete", "Mont Ngafula", "Ndjili", "Ngaba", "Ngaliema", "Nsele", "Selembao", "" }));

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Quartier");

        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Secteur");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel74)
                            .addComponent(jLabel73)
                            .addComponent(jLabel72)
                            .addComponent(jLabel71)
                            .addComponent(jLabel66))
                        .addGap(4, 4, Short.MAX_VALUE)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_quartier_ag)
                            .addComponent(txt_commune_ag, 0, 116, Short.MAX_VALUE)
                            .addComponent(txt_avenue_ag)
                            .addComponent(txt_numadr_ag)
                            .addComponent(txt_province_ag)))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68)
                            .addComponent(jLabel69)
                            .addComponent(jLabel70)
                            .addComponent(jLabel78)
                            .addComponent(jLabel67))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_district_ag)
                            .addComponent(txt_teritoire_ag)
                            .addComponent(txt_tel_ag)
                            .addComponent(txt_email_ag)
                            .addComponent(txt_secteur_ag))))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(txt_commune_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(txt_quartier_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel72)
                    .addComponent(txt_avenue_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(txt_numadr_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addComponent(txt_province_ag, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67)
                    .addComponent(txt_district_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(txt_teritoire_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel69)
                    .addComponent(txt_tel_ag, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel70)
                    .addComponent(txt_email_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78)
                    .addComponent(txt_secteur_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel25.setBackground(new java.awt.Color(0, 0, 0));

        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Num Tel du Parrain");

        jLabel76.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(255, 255, 255));
        jLabel76.setText("Prenom Parrain");

        txt_numtelparrain_ag.setEnabled(false);

        jLabel75.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Nom Parrain");

        txt_prenomparrain_ag.setEnabled(false);

        cb_nomparrain_ag.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cb_nomparrain_agPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cb_nomparrain_ag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_nomparrain_agActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel75)
                    .addComponent(jLabel76)
                    .addComponent(jLabel77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_numtelparrain_ag)
                    .addComponent(txt_prenomparrain_ag, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(cb_nomparrain_ag, 0, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(cb_nomparrain_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(txt_prenomparrain_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(txt_numtelparrain_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel26.setBackground(new java.awt.Color(0, 0, 0));
        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Photo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Camera.png"))); // NOI18N
        jButton18.setToolTipText("Capture");
        jButton18.setContentAreaFilled(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        tbCamera.setBorderPainted(false);
        tbCamera.setContentAreaFilled(false);
        tbCamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbCameraActionPerformed(evt);
            }
        });

        jButton65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/repeat.png"))); // NOI18N
        jButton65.setToolTipText("Recommencer");
        jButton65.setContentAreaFilled(false);
        jButton65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton65ActionPerformed(evt);
            }
        });

        jButton98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Open.png"))); // NOI18N
        jButton98.setToolTipText("Ouvrir");
        jButton98.setContentAreaFilled(false);
        jButton98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton98ActionPerformed(evt);
            }
        });

        jLabel221.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel221.setForeground(new java.awt.Color(255, 255, 255));
        jLabel221.setText("Capture");

        jLabel222.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel222.setForeground(new java.awt.Color(255, 255, 255));
        jLabel222.setText("Recommencer");

        jLabel223.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel223.setForeground(new java.awt.Color(255, 255, 255));
        jLabel223.setText("Ouvrir");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tbCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton18)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel221)))
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jLabel222)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel223)
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addComponent(jButton65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton98)))
                .addGap(24, 24, 24))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton65)
                    .addComponent(jButton98)
                    .addComponent(jButton18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel221)
                    .addComponent(jLabel222)
                    .addComponent(jLabel223))
                .addContainerGap())
        );

        jPanel28.setBackground(new java.awt.Color(0, 0, 0));

        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("En cas d'un fait, contacter :");

        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Telephone");

        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Commune");

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Quartier");

        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Avenue");

        jLabel84.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("N°");

        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Degre parental");

        txt_commune_urgent.setEditable(true);
        txt_commune_urgent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bandal", "Barumbu", "Bumbu", "Gombe", "Kalamu", "Kasa vubu", "Kimbaseke", "Kinsenso", "Kinshasa", "Kintambo", "Lemba", "Limete", "Lingwala", "Makala", "Maluku", "Masina", "Matete", "Mont Ngafula", "Ndjili", "Ngaba", "Ngaliema", "Nsele", "Selembao", " " }));

        cbo_degre_urgent.setEditable(true);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel80)
                        .addGap(36, 36, 36)
                        .addComponent(txt_tel_urgent))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel83)
                        .addGap(49, 49, 49)
                        .addComponent(txt_avenue_urgent))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel82)
                            .addComponent(jLabel81))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_commune_urgent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_quartier_urgent)))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel79)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel84)
                            .addComponent(jLabel85))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbo_degre_urgent, 0, 118, Short.MAX_VALUE)
                            .addComponent(txt_num_urgent))))
                .addGap(26, 26, 26))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel80)
                    .addComponent(txt_tel_urgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81)
                    .addComponent(txt_commune_urgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel82)
                    .addComponent(txt_quartier_urgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83)
                    .addComponent(txt_avenue_urgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel84)
                    .addComponent(txt_num_urgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(cbo_degre_urgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel33.setBackground(new java.awt.Color(0, 0, 0));

        jLabel106.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(255, 255, 255));
        jLabel106.setText("Fonction");

        jLabel107.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(255, 255, 255));
        jLabel107.setText("Date du debut");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel106)
                    .addComponent(jLabel107))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbo_fonction_ag, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cdar_datedebut_ag, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel106)
                    .addComponent(cbo_fonction_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cdar_datedebut_ag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel107))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel33.setFont(new java.awt.Font("Segoe UI Light", 0, 21)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 51, 0));
        jLabel33.setText("Enregistrement des agents");

        jPanel80.setBackground(new java.awt.Color(0, 0, 0));

        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton23.setToolTipText("Initialiser");
        jButton23.setContentAreaFilled(false);
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton22.setToolTipText("Enregistrer");
        jButton22.setContentAreaFilled(false);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Enregistrer");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Initialiser");

        javax.swing.GroupLayout jPanel80Layout = new javax.swing.GroupLayout(jPanel80);
        jPanel80.setLayout(jPanel80Layout);
        jPanel80Layout.setHorizontalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton22)
                    .addGroup(jPanel80Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel43)))
                .addGap(18, 18, 18)
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel80Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel45))
                    .addComponent(jButton23))
                .addContainerGap())
        );
        jPanel80Layout.setVerticalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton23)
                    .addComponent(jButton22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel45))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33))
                .addGap(7, 7, 7)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jPanel80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPanel80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(172, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Pour agent", jPanel21);

        jPanel32.setBackground(new java.awt.Color(0, 0, 0));

        jPanel27.setBackground(new java.awt.Color(0, 0, 0));

        jLabel103.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(255, 255, 255));
        jLabel103.setText("Prenom enfant");

        jLabel101.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(255, 255, 255));
        jLabel101.setText("Nom enfant");

        jLabel104.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(255, 255, 255));
        jLabel104.setText("Nom parent");

        jLabel105.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(255, 255, 255));
        jLabel105.setText("Prenom parent");

        cbo_agent_Enfant.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_agent_EnfantPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel102.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(255, 255, 255));
        jLabel102.setText("Post nom enfant");

        txt_prenomag_enfant.setEnabled(false);

        jLabel148.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(255, 255, 255));
        jLabel148.setText("Post-Nom");

        txt_postNom_enfant.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_postNom_enfantMouseClicked(evt);
            }
        });
        txt_postNom_enfant.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                txt_postNom_enfantPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel101)
                        .addGap(34, 34, 34)
                        .addComponent(txt_nomEnfant, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
                    .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                            .addComponent(jLabel102)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_postNomEnfant))
                        .addGroup(jPanel27Layout.createSequentialGroup()
                            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel103)
                                .addComponent(jLabel104)
                                .addComponent(jLabel105))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_prenomEnfant, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_prenomag_enfant)
                                    .addComponent(txt_postNom_enfant, 0, 147, Short.MAX_VALUE)
                                    .addComponent(cbo_agent_Enfant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jLabel148))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel101)
                            .addComponent(txt_nomEnfant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel102)
                            .addComponent(txt_postNomEnfant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel103))
                    .addComponent(txt_prenomEnfant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel104)
                    .addComponent(cbo_agent_Enfant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel148)
                    .addComponent(txt_postNom_enfant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_prenomag_enfant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel48.setBackground(new java.awt.Color(0, 0, 0));

        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton24.setToolTipText("Enregistrer");
        jButton24.setContentAreaFilled(false);
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton25.setToolTipText("Initialiser");
        jButton25.setContentAreaFilled(false);
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jLabel215.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel215.setForeground(new java.awt.Color(255, 255, 255));
        jLabel215.setText("Enregistrer");

        jLabel216.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel216.setForeground(new java.awt.Color(255, 255, 255));
        jLabel216.setText("Initialiser");

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton24)
                .addGap(18, 18, 18)
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel48Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel216))
                    .addComponent(jButton25))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel48Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jLabel215)
                    .addContainerGap(94, Short.MAX_VALUE)))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton24)
                    .addComponent(jButton25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel216)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel48Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(jLabel215)
                    .addContainerGap(13, Short.MAX_VALUE)))
        );

        tble_enfant.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane19.setViewportView(tble_enfant);

        jPanel65.setBackground(new java.awt.Color(0, 0, 0));

        jButton101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton101.setToolTipText("Retour");
        jButton101.setContentAreaFilled(false);
        jButton101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton101ActionPerformed(evt);
            }
        });

        jButton99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton99.setToolTipText("Modifier");
        jButton99.setContentAreaFilled(false);
        jButton99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton99ActionPerformed(evt);
            }
        });

        jButton100.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton100.setToolTipText("Supprimer");
        jButton100.setContentAreaFilled(false);
        jButton100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton100ActionPerformed(evt);
            }
        });

        jLabel218.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel218.setForeground(new java.awt.Color(255, 255, 255));
        jLabel218.setText("Modifier");

        jLabel217.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel217.setForeground(new java.awt.Color(255, 255, 255));
        jLabel217.setText("Supprimer");

        jLabel219.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel219.setForeground(new java.awt.Color(255, 255, 255));
        jLabel219.setText("Accueil");

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton99)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel218)))
                .addGap(18, 18, 18)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton100)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel217)))
                .addGap(18, 18, 18)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton101)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel219))))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton100)
                    .addComponent(jButton101, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton99))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel218)
                    .addComponent(jLabel217)
                    .addComponent(jLabel219))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        titre1.setFont(new java.awt.Font("Segoe UI Light", 0, 22)); // NOI18N
        titre1.setForeground(new java.awt.Color(255, 51, 0));
        titre1.setText("Enregistrement des enfants");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titre1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 835, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titre1)
                .addGap(42, 42, 42)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(150, Short.MAX_VALUE))
                    .addGroup(jPanel32Layout.createSequentialGroup()
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(83, 83, 83)
                        .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jTabbedPane3.addTab("Enfant", jPanel32);

        jPanel22.setBackground(new java.awt.Color(0, 0, 0));

        jPanel29.setBackground(new java.awt.Color(0, 0, 0));

        jLabel86.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setText("Post Nom");

        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setText("Nationalité");

        jLabel88.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(255, 255, 255));
        jLabel88.setText("Etat civil");

        jLabel89.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Lieu de naissance");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel90.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Prenom");

        jTextField42.setEnabled(false);

        jLabel91.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Matricule");

        jLabel92.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setText("Nom");

        jLabel93.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 255, 255));
        jLabel93.setText("Nom du pere");

        jTextField44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField44ActionPerformed(evt);
            }
        });

        jLabel94.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 255, 255));
        jLabel94.setText("Date de naissance");

        jLabel95.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("Marié a");

        jLabel96.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("Nombre d'enfant");

        jLabel97.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Nom de la mère");

        jLabel98.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 255, 255));
        jLabel98.setText("Durée");

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", " " }));

        jLabel99.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(255, 255, 255));
        jLabel99.setText("Mois");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel29Layout.createSequentialGroup()
                            .addComponent(jLabel93)
                            .addGap(64, 64, 64)
                            .addComponent(jTextField40))
                        .addGroup(jPanel29Layout.createSequentialGroup()
                            .addComponent(jLabel94)
                            .addGap(37, 37, 37)
                            .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel29Layout.createSequentialGroup()
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel91)
                                .addComponent(jLabel92))
                            .addGap(82, 82, 82)
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel29Layout.createSequentialGroup()
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel86)
                                .addComponent(jLabel90))
                            .addGap(80, 80, 80)
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField36)
                                .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel29Layout.createSequentialGroup()
                            .addComponent(jLabel89)
                            .addGap(41, 41, 41)
                            .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel97)
                            .addComponent(jLabel88)
                            .addComponent(jLabel95))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField43)
                            .addComponent(jComboBox17, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel96)
                            .addComponent(jLabel87)
                            .addComponent(jLabel98))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel29Layout.createSequentialGroup()
                                .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel99))
                            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField44)
                                .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92)
                    .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86)
                    .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel90)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel89)
                    .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel94)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel97)
                    .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel88)
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95)
                    .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel99))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel30.setBackground(new java.awt.Color(0, 0, 0));
        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Photo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jPanel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel100.setText("Capture");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel100)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel100)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel81.setBackground(new java.awt.Color(0, 0, 0));

        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Open.png"))); // NOI18N
        jButton21.setToolTipText("Parcourir");
        jButton21.setContentAreaFilled(false);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Camera.png"))); // NOI18N
        jButton20.setToolTipText("Capture");
        jButton20.setContentAreaFilled(false);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jLabel224.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel224.setForeground(new java.awt.Color(255, 255, 255));
        jLabel224.setText("Parcourir");

        jLabel143.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(255, 255, 255));
        jLabel143.setText("Capture");

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel81Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel143))
                    .addComponent(jButton20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton21)
                    .addGroup(jPanel81Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel224)))
                .addContainerGap())
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel81Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton20)
                    .addComponent(jButton21))
                .addGap(18, 18, 18)
                .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel143)
                    .addComponent(jLabel224))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel81, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton26.setText("Enregistrer");

        jButton27.setText("Initialiser");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jButton26)
                        .addGap(35, 35, 35)
                        .addComponent(jButton27))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(427, 427, 427))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114))
                    .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton26)
                    .addComponent(jButton27))
                .addGap(67, 67, 67))
        );

        jTabbedPane3.addTab("Pour stagiaire", jPanel22);

        jPanel34.setBackground(new java.awt.Color(0, 0, 0));

        tble_personnel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tble_personnel.setUpdateSelectionOnSort(false);
        tble_personnel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tble_personnelMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tble_personnel);

        jPanel47.setBackground(new java.awt.Color(0, 0, 0));

        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton29.setToolTipText("Retour");
        jButton29.setContentAreaFilled(false);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton11.setToolTipText("Supprimer");
        jButton11.setContentAreaFilled(false);

        jLabel227.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel227.setForeground(new java.awt.Color(255, 255, 255));
        jLabel227.setText("Initialiser");

        jLabel228.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel228.setForeground(new java.awt.Color(255, 255, 255));
        jLabel228.setText("Supprimer");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton11)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel227)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel47Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel228)))
                .addContainerGap())
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel227)
                    .addComponent(jLabel228)))
        );

        jLabel203.setFont(new java.awt.Font("Segoe UI Light", 0, 22)); // NOI18N
        jLabel203.setForeground(new java.awt.Color(255, 51, 0));
        jLabel203.setText("Gestion de employés");

        jPanel86.setBackground(new java.awt.Color(0, 0, 0));

        Rech_Stock1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        Rech_Stock1.setToolTipText("Rechercher");
        Rech_Stock1.setContentAreaFilled(false);
        Rech_Stock1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rech_Stock1ActionPerformed(evt);
            }
        });

        jButton30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/voir tous.png"))); // NOI18N
        jButton30.setToolTipText("Voir tous les enregistrements");
        jButton30.setContentAreaFilled(false);
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        txt_stockRecherche1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_stockRecherche1KeyPressed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Agent recherche");

        jLabel260.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel260.setForeground(new java.awt.Color(255, 255, 255));
        jLabel260.setText("Recherche");

        jLabel261.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel261.setForeground(new java.awt.Color(255, 255, 255));
        jLabel261.setText("Voir tous les enregistrements");

        javax.swing.GroupLayout jPanel86Layout = new javax.swing.GroupLayout(jPanel86);
        jPanel86.setLayout(jPanel86Layout);
        jPanel86Layout.setHorizontalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel86Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(txt_stockRecherche1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Rech_Stock1)
                    .addGroup(jPanel86Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel260)))
                .addGap(39, 39, 39)
                .addGroup(jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel86Layout.createSequentialGroup()
                        .addComponent(jButton30)
                        .addGap(52, 52, 52))
                    .addComponent(jLabel261))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel86Layout.setVerticalGroup(
            jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel86Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel86Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(txt_stockRecherche1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton30)
                    .addComponent(Rech_Stock1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel86Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel261)
                    .addComponent(jLabel260))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel203)
                        .addGap(63, 63, 63)
                        .addComponent(jPanel86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGap(486, 486, 486)
                        .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(391, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel203)
                    .addComponent(jPanel86, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Gestion", jPanel34);

        jPanel41.setBackground(new java.awt.Color(0, 0, 0));

        tble_fonction.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(tble_fonction);

        jPanel42.setBackground(new java.awt.Color(0, 0, 0));

        jButton59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton59.setToolTipText("Supprimer");
        jButton59.setContentAreaFilled(false);
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        jButton58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton58.setToolTipText("Modifier");
        jButton58.setContentAreaFilled(false);
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });

        jButton60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton60.setToolTipText("Retour");
        jButton60.setContentAreaFilled(false);

        jLabel230.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel230.setForeground(new java.awt.Color(255, 255, 255));
        jLabel230.setText("Modifier");

        jLabel231.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel231.setForeground(new java.awt.Color(255, 255, 255));
        jLabel231.setText("Supprimer");

        jLabel232.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel232.setForeground(new java.awt.Color(255, 255, 255));
        jLabel232.setText("Accueil");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton58)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel230)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addComponent(jButton59)
                        .addGap(18, 18, 18)
                        .addComponent(jButton60)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel42Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel231)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel232)))
                .addContainerGap())
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton58)
                    .addComponent(jButton59)
                    .addComponent(jButton60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel230)
                    .addComponent(jLabel231)
                    .addComponent(jLabel232))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel46.setBackground(new java.awt.Color(0, 0, 0));

        txt_fonction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fonctionKeyPressed(evt);
            }
        });

        jButton57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton57.setToolTipText("Enregistrer");
        jButton57.setContentAreaFilled(false);
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        jLabel142.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(255, 255, 255));
        jLabel142.setText("Fonction");

        jLabel229.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel229.setForeground(new java.awt.Color(255, 255, 255));
        jLabel229.setText("Enregistrer");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel142)
                .addGap(18, 18, 18)
                .addComponent(txt_fonction, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel229)
                    .addComponent(jButton57))
                .addContainerGap())
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton57))
                    .addGroup(jPanel46Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel142)
                            .addComponent(txt_fonction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel229)
                .addContainerGap())
        );

        jLabel141.setFont(new java.awt.Font("Segoe UI Light", 0, 21)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(255, 51, 0));
        jLabel141.setText("Enregistrement des fonctions existantes a l'entreprise");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel141)
                    .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(282, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jLabel141)
                        .addGap(31, 31, 31)
                        .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(175, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Poste", jPanel41);

        jPanel43.setBackground(new java.awt.Color(0, 0, 0));

        jPanel44.setBackground(new java.awt.Color(0, 0, 0));

        jLabel146.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(255, 255, 255));
        jLabel146.setText("Numero tél parrain");

        txt_numtel_parrain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numtel_parrainKeyReleased(evt);
            }
        });

        jLabel145.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(255, 255, 255));
        jLabel145.setText("Prenom parrain");

        jLabel144.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(255, 255, 255));
        jLabel144.setText("Nom parrain");

        code.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "243", "33" }));

        plus.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        plus.setForeground(new java.awt.Color(255, 255, 255));
        plus.setText("+");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel144)
                    .addComponent(jLabel145)
                    .addComponent(jLabel146))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(plus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_prenom_parrain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(txt_numtel_parrain, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_nom_parrain))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nom_parrain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel144))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel145)
                    .addComponent(txt_prenom_parrain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel146)
                    .addComponent(txt_numtel_parrain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(plus))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tble_parrain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(tble_parrain);

        jPanel45.setBackground(new java.awt.Color(0, 0, 0));

        jButton63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton63.setToolTipText("Supprimer");
        jButton63.setContentAreaFilled(false);
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });

        jButton62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton62.setToolTipText("Modifier");
        jButton62.setContentAreaFilled(false);
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });

        jButton61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton61.setToolTipText("Enregistrer");
        jButton61.setContentAreaFilled(false);
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        jButton64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton64.setToolTipText("Initialiser");
        jButton64.setContentAreaFilled(false);
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });

        jLabel235.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel235.setForeground(new java.awt.Color(255, 255, 255));
        jLabel235.setText("Initialiser");

        jLabel234.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel234.setForeground(new java.awt.Color(255, 255, 255));
        jLabel234.setText("Supprimer");

        jLabel233.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel233.setForeground(new java.awt.Color(255, 255, 255));
        jLabel233.setText("Modifier");

        jLabel236.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel236.setForeground(new java.awt.Color(255, 255, 255));
        jLabel236.setText("Enregistrer");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton61)
                .addGap(18, 18, 18)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jButton62)
                        .addGap(18, 18, 18)
                        .addComponent(jButton63))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel233)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel234)))
                .addGap(18, 18, 18)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel235))
                    .addComponent(jButton64))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jLabel236)
                    .addContainerGap(260, Short.MAX_VALUE)))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton61)
                    .addComponent(jButton62)
                    .addComponent(jButton63)
                    .addComponent(jButton64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel235)
                    .addComponent(jLabel234)
                    .addComponent(jLabel233))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jLabel236)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel147.setFont(new java.awt.Font("Segoe UI Light", 0, 22)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(255, 51, 0));
        jLabel147.setText("Enregistrer les parrains des agents");

        jLabel204.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel204.setForeground(new java.awt.Color(255, 51, 0));
        jLabel204.setText("Liste des parrains");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel147, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(151, 151, 151)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel204)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel147)
                .addGap(30, 30, 30)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addComponent(jLabel204)
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Parrain de l'agent", jPanel43);

        javax.swing.GroupLayout pan_personnelLayout = new javax.swing.GroupLayout(pan_personnel);
        pan_personnel.setLayout(pan_personnelLayout);
        pan_personnelLayout.setHorizontalGroup(
            pan_personnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pan_personnelLayout.setVerticalGroup(
            pan_personnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_personnelLayout.createSequentialGroup()
                .addGroup(pan_personnelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(95, 95, 95))
        );

        pan_mouv.add(pan_personnel, "card5");

        pan_tele.setBackground(new java.awt.Color(37, 37, 38));
        pan_tele.setMaximumSize(new java.awt.Dimension(1200, 620));
        pan_tele.setMinimumSize(new java.awt.Dimension(1200, 620));

        pan_tele_bouton.setBackground(new java.awt.Color(0, 0, 0));

        jButton40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/activite.png"))); // NOI18N
        jButton40.setToolTipText("Enregistrer les activité");
        jButton40.setContentAreaFilled(false);
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/attribué.png"))); // NOI18N
        jButton41.setToolTipText("Attribution des activité");
        jButton41.setContentAreaFilled(false);
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        jButton42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/aff_pro.png"))); // NOI18N
        jButton42.setToolTipText("Afficher programme");
        jButton42.setContentAreaFilled(false);
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/programme.png"))); // NOI18N
        jButton43.setToolTipText("Programme");
        jButton43.setContentAreaFilled(false);
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });

        jButton74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/gestion.png"))); // NOI18N
        jButton74.setToolTipText("Gestion");
        jButton74.setContentAreaFilled(false);
        jButton74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton74ActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 51, 0));
        jLabel32.setText("Television");

        javax.swing.GroupLayout pan_tele_boutonLayout = new javax.swing.GroupLayout(pan_tele_bouton);
        pan_tele_bouton.setLayout(pan_tele_boutonLayout);
        pan_tele_boutonLayout.setHorizontalGroup(
            pan_tele_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_boutonLayout.createSequentialGroup()
                .addGroup(pan_tele_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tele_boutonLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel32))
                    .addGroup(pan_tele_boutonLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(pan_tele_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton42)
                            .addComponent(jButton74)
                            .addComponent(jButton40)
                            .addComponent(jButton41)
                            .addComponent(jButton43))))
                .addGap(22, 70, Short.MAX_VALUE))
        );
        pan_tele_boutonLayout.setVerticalGroup(
            pan_tele_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_boutonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(jButton41)
                .addGap(39, 39, 39)
                .addComponent(jButton40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jButton43)
                .addGap(34, 34, 34)
                .addComponent(jButton42)
                .addGap(46, 46, 46)
                .addComponent(jButton74)
                .addGap(28, 28, 28))
        );

        pan_mouv_tele.setLayout(new java.awt.CardLayout());

        pan_tele_accueil.setLayout(new java.awt.CardLayout());

        pan_tele_accueil1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel250.setBackground(new java.awt.Color(51, 153, 255));
        jLabel250.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        jLabel250.setForeground(new java.awt.Color(255, 51, 0));
        jLabel250.setText("Coordination Télévision");

        jLabel251.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/tele.png"))); // NOI18N

        jLabel252.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/radio.png"))); // NOI18N

        jLabel253.setBackground(new java.awt.Color(51, 255, 51));
        jLabel253.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel253.setForeground(new java.awt.Color(255, 255, 255));
        jLabel253.setText("Brt Africa");

        jPanel83.setBackground(new java.awt.Color(0, 0, 0));

        jLabel254.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel254.setForeground(new java.awt.Color(255, 255, 255));
        jLabel254.setText("La voix de l'Afrique");

        jLabel255.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel255.setForeground(new java.awt.Color(255, 51, 0));
        jLabel255.setText("Brt Africa la radio: 98.6 Mhz");

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel83Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel83Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel254)
                        .addGap(57, 57, 57))
                    .addComponent(jLabel255, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel83Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel255)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel254)
                .addContainerGap())
        );

        jPanel84.setBackground(new java.awt.Color(0, 0, 0));

        jLabel256.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel256.setForeground(new java.awt.Color(255, 51, 0));
        jLabel256.setText("Brt Africa la télé: 679.20 Mhz");

        jLabel257.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel257.setForeground(new java.awt.Color(255, 255, 255));
        jLabel257.setText("Télé business");

        javax.swing.GroupLayout jPanel84Layout = new javax.swing.GroupLayout(jPanel84);
        jPanel84.setLayout(jPanel84Layout);
        jPanel84Layout.setHorizontalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel84Layout.createSequentialGroup()
                        .addComponent(jLabel257)
                        .addGap(91, 91, 91))
                    .addComponent(jLabel256, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel84Layout.setVerticalGroup(
            jPanel84Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel84Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel256)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel257)
                .addContainerGap())
        );

        javax.swing.GroupLayout pan_tele_accueil1Layout = new javax.swing.GroupLayout(pan_tele_accueil1);
        pan_tele_accueil1.setLayout(pan_tele_accueil1Layout);
        pan_tele_accueil1Layout.setHorizontalGroup(
            pan_tele_accueil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_accueil1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tele_accueil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tele_accueil1Layout.createSequentialGroup()
                        .addComponent(jLabel251)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(jLabel253)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel252)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tele_accueil1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel250)
                        .addGap(274, 274, 274))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tele_accueil1Layout.createSequentialGroup()
                        .addComponent(jPanel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pan_tele_accueil1Layout.setVerticalGroup(
            pan_tele_accueil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_accueil1Layout.createSequentialGroup()
                .addGroup(pan_tele_accueil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tele_accueil1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel250)
                        .addGap(18, 18, 18)
                        .addGroup(pan_tele_accueil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel251)
                            .addComponent(jLabel252)))
                    .addGroup(pan_tele_accueil1Layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jLabel253)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_tele_accueil1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel83, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel84, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(640, Short.MAX_VALUE))
        );

        pan_tele_accueil.add(pan_tele_accueil1, "card2");

        pan_mouv_tele.add(pan_tele_accueil, "card5");

        pan_tele_act.setBackground(new java.awt.Color(0, 0, 0));
        pan_tele_act.setPreferredSize(new java.awt.Dimension(999, 597));

        jLabel134.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(255, 51, 0));
        jLabel134.setText("Enregistrement des activités");

        jPanel52.setBackground(new java.awt.Color(0, 0, 0));

        jButton67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton67.setToolTipText("Enregistrer");
        jButton67.setContentAreaFilled(false);
        jButton67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton67ActionPerformed(evt);
            }
        });

        jButton68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton68.setToolTipText("Initailiser");
        jButton68.setContentAreaFilled(false);
        jButton68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton68ActionPerformed(evt);
            }
        });

        jLabel237.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel237.setForeground(new java.awt.Color(255, 255, 255));
        jLabel237.setText("Enregistrer");

        jLabel238.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel238.setForeground(new java.awt.Color(255, 255, 255));
        jLabel238.setText("Initialiser");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton67)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel237)))
                .addGap(18, 18, 18)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel238))
                    .addComponent(jButton68))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton67)
                    .addComponent(jButton68))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel237)
                    .addComponent(jLabel238)))
        );

        tble_activite.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(tble_activite);

        jPanel53.setBackground(new java.awt.Color(0, 0, 0));

        jButton69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton69.setToolTipText("Modifier");
        jButton69.setContentAreaFilled(false);
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });

        jButton70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton70.setToolTipText("Supprimer");
        jButton70.setContentAreaFilled(false);
        jButton70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton70ActionPerformed(evt);
            }
        });

        jButton71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton71.setToolTipText("Retour");
        jButton71.setContentAreaFilled(false);
        jButton71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton71ActionPerformed(evt);
            }
        });

        jLabel239.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel239.setForeground(new java.awt.Color(255, 255, 255));
        jLabel239.setText("Modifier");

        jLabel240.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel240.setForeground(new java.awt.Color(255, 255, 255));
        jLabel240.setText("Supprimer");

        jLabel241.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel241.setForeground(new java.awt.Color(255, 255, 255));
        jLabel241.setText("Accueil");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton69)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel239)))
                .addGap(18, 18, 18)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton70)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel240)))
                .addGap(18, 18, 18)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel53Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel241))
                    .addComponent(jButton71))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton69)
                    .addComponent(jButton70)
                    .addComponent(jButton71))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel239)
                    .addComponent(jLabel240)
                    .addComponent(jLabel241))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel85.setBackground(new java.awt.Color(0, 0, 0));

        cbo_activite_type.setEditable(true);
        cbo_activite_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Emission", "Journal", "Tranche d'animation", "Detente", " " }));
        cbo_activite_type.setSelectedIndex(-1);

        jLabel135.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(255, 255, 255));
        jLabel135.setText("Nom activité");

        txt_activite_description.setColumns(20);
        txt_activite_description.setRows(5);
        jScrollPane8.setViewportView(txt_activite_description);

        jLabel136.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(255, 255, 255));
        jLabel136.setText("Type d'activité");

        jLabel137.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(255, 255, 255));
        jLabel137.setText("Description");

        javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
        jPanel85.setLayout(jPanel85Layout);
        jPanel85Layout.setHorizontalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel137)
                    .addComponent(jLabel135)
                    .addComponent(jLabel136))
                .addGap(12, 12, 12)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8)
                    .addComponent(txt_activite_nom)
                    .addComponent(cbo_activite_type, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel85Layout.setVerticalGroup(
            jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel85Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel135)
                    .addComponent(txt_activite_nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel136)
                    .addComponent(cbo_activite_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel137)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pan_tele_actLayout = new javax.swing.GroupLayout(pan_tele_act);
        pan_tele_act.setLayout(pan_tele_actLayout);
        pan_tele_actLayout.setHorizontalGroup(
            pan_tele_actLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_actLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tele_actLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tele_actLayout.createSequentialGroup()
                        .addComponent(jLabel134)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pan_tele_actLayout.createSequentialGroup()
                        .addComponent(jPanel85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(pan_tele_actLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 67, Short.MAX_VALUE))
        );
        pan_tele_actLayout.setVerticalGroup(
            pan_tele_actLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_actLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tele_actLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pan_tele_actLayout.createSequentialGroup()
                        .addComponent(jLabel134)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel85, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(581, Short.MAX_VALUE))
        );

        pan_mouv_tele.add(pan_tele_act, "card3");

        pan_tele_affi.setBackground(new java.awt.Color(0, 0, 0));
        pan_tele_affi.setPreferredSize(new java.awt.Dimension(999, 597));

        tble_tele.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tble_tele);

        jLabel138.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(255, 51, 0));
        jLabel138.setText("Affichage du programme");

        jButton44.setText("Supprimer");

        jButton45.setText("Modification");

        jButton46.setText("Retour");

        jPanel68.setBackground(new java.awt.Color(0, 0, 0));

        jButton72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jButton72.setToolTipText("Recherche");
        jButton72.setContentAreaFilled(false);
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });

        cdar_dateRecherche.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cdar_dateRechercheKeyPressed(evt);
            }
        });

        jButton73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/voir tous.png"))); // NOI18N
        jButton73.setToolTipText("Voir tous les enregistrement");
        jButton73.setContentAreaFilled(false);
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });

        jLabel154.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel154.setForeground(new java.awt.Color(255, 255, 255));
        jLabel154.setText("Date");

        jLabel242.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel242.setForeground(new java.awt.Color(255, 255, 255));
        jLabel242.setText("Rechercher");

        jLabel243.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel243.setForeground(new java.awt.Color(255, 255, 255));
        jLabel243.setText("Voir tous les enregistrement");

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel154)
                .addGap(18, 18, 18)
                .addComponent(cdar_dateRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton72)
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel242)))
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jButton73))
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel243)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton73)
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel154)
                            .addComponent(cdar_dateRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton72))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel242)
                    .addComponent(jLabel243))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pan_tele_affiLayout = new javax.swing.GroupLayout(pan_tele_affi);
        pan_tele_affi.setLayout(pan_tele_affiLayout);
        pan_tele_affiLayout.setHorizontalGroup(
            pan_tele_affiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_affiLayout.createSequentialGroup()
                .addGroup(pan_tele_affiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tele_affiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane9))
                    .addGroup(pan_tele_affiLayout.createSequentialGroup()
                        .addGroup(pan_tele_affiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pan_tele_affiLayout.createSequentialGroup()
                                .addGap(192, 192, 192)
                                .addComponent(jButton44)
                                .addGap(18, 18, 18)
                                .addComponent(jButton45)
                                .addGap(18, 18, 18)
                                .addComponent(jButton46))
                            .addGroup(pan_tele_affiLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel138)))
                        .addGap(0, 504, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pan_tele_affiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pan_tele_affiLayout.setVerticalGroup(
            pan_tele_affiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tele_affiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel138)
                .addGap(26, 26, 26)
                .addComponent(jPanel68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addGroup(pan_tele_affiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton44)
                    .addComponent(jButton45)
                    .addComponent(jButton46))
                .addContainerGap(473, Short.MAX_VALUE))
        );

        pan_mouv_tele.add(pan_tele_affi, "card5");

        pan_tele_prog.setBackground(new java.awt.Color(0, 0, 0));

        jLabel124.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(255, 51, 0));
        jLabel124.setText("Ce qui est programmé");

        jPanel38.setBackground(new java.awt.Color(0, 0, 0));
        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Activites", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        jLabel130.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(255, 255, 255));
        jLabel130.setText("Type");

        jLabel131.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(255, 255, 255));
        jLabel131.setText("Nom activité");

        nomActi_pro.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                nomActi_proPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        typeAgent_pro.setEnabled(false);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel131)
                    .addComponent(jLabel130))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomActi_pro, 0, 126, Short.MAX_VALUE)
                    .addComponent(typeAgent_pro))
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel131)
                    .addComponent(nomActi_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel130)
                    .addComponent(typeAgent_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel39.setBackground(new java.awt.Color(0, 0, 0));
        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Agent", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), java.awt.Color.white)); // NOI18N

        txt_prenomAgent_pro.setEnabled(false);

        jLabel132.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(255, 255, 255));
        jLabel132.setText("Prenom Agent");

        jLabel133.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(255, 255, 255));
        jLabel133.setText("Nom Agent");

        cbo_nomAgent_pro.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_nomAgent_proPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PostNom");

        cbo_postNom_pro.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_postNom_proPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addComponent(jLabel133)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbo_nomAgent_pro, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel39Layout.createSequentialGroup()
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel132)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_prenomAgent_pro)
                            .addComponent(cbo_postNom_pro, 0, 126, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel133)
                    .addComponent(cbo_nomAgent_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbo_postNom_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_prenomAgent_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel132))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel51.setBackground(new java.awt.Color(0, 0, 0));

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton13.setToolTipText("Enregistrer");
        jButton13.setContentAreaFilled(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton14.setToolTipText("Initialiser");
        jButton14.setContentAreaFilled(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel244.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel244.setForeground(new java.awt.Color(255, 255, 255));
        jLabel244.setText("Enregistrer");

        jLabel245.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel245.setForeground(new java.awt.Color(255, 255, 255));
        jLabel245.setText("Initialiser");

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel244)))
                .addGap(18, 18, 18)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel51Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel245))
                    .addComponent(jButton14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel244)
                    .addComponent(jLabel245))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel54.setBackground(new java.awt.Color(0, 0, 0));

        jLabel128.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(255, 255, 255));
        jLabel128.setText("Rediffusion");

        cbo_redif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Oui", "Non" }));
        cbo_redif.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbo_redif.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbo_redifPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel129.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(255, 255, 255));
        jLabel129.setText("Date rediffusion");

        jLabel127.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(255, 255, 255));
        jLabel127.setText("Heure fin");

        jLabel126.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(255, 255, 255));
        jLabel126.setText("Heure debut");

        jLabel125.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(255, 255, 255));
        jLabel125.setText("Date");

        cdar_dateredif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cdar_dateredifMouseClicked(evt);
            }
        });

        hd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        jLabel152.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(255, 255, 255));
        jLabel152.setText(":");

        md.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        jLabel153.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel153.setForeground(new java.awt.Color(255, 255, 255));
        jLabel153.setText(":");

        mf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));

        hf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel126))
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel127))
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel128)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(hd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel152)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(md, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(hf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel153)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbo_redif, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jLabel125)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cdar_date_pro, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jLabel129)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cdar_dateredif, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel125)
                    .addComponent(cdar_date_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel126)
                    .addComponent(hd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel152)
                    .addComponent(md, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel127)
                    .addComponent(hf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel153)
                    .addComponent(mf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel128)
                    .addComponent(cbo_redif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel129)
                    .addComponent(cdar_dateredif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pan_tele_progLayout = new javax.swing.GroupLayout(pan_tele_prog);
        pan_tele_prog.setLayout(pan_tele_progLayout);
        pan_tele_progLayout.setHorizontalGroup(
            pan_tele_progLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_progLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tele_progLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel124, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(pan_tele_progLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(427, Short.MAX_VALUE))
        );
        pan_tele_progLayout.setVerticalGroup(
            pan_tele_progLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tele_progLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel124)
                .addGap(72, 72, 72)
                .addGroup(pan_tele_progLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pan_tele_progLayout.createSequentialGroup()
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pan_tele_progLayout.createSequentialGroup()
                        .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(560, 560, 560))
        );

        pan_mouv_tele.add(pan_tele_prog, "card4");

        pan_tele_gestion.setBackground(new java.awt.Color(0, 0, 0));

        tble_tele_gestion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(tble_tele_gestion);

        jPanel55.setBackground(new java.awt.Color(0, 0, 0));

        jButton77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton77.setToolTipText("Accueil");
        jButton77.setContentAreaFilled(false);
        jButton77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton77ActionPerformed(evt);
            }
        });

        jButton76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton76.setToolTipText("Supprimer");
        jButton76.setContentAreaFilled(false);
        jButton76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton76ActionPerformed(evt);
            }
        });

        jButton75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton75.setToolTipText("Modifier");
        jButton75.setContentAreaFilled(false);
        jButton75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton75ActionPerformed(evt);
            }
        });

        jLabel246.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel246.setForeground(new java.awt.Color(255, 255, 255));
        jLabel246.setText("Modifier");

        jLabel247.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel247.setForeground(new java.awt.Color(255, 255, 255));
        jLabel247.setText("Supprimer");

        jLabel248.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel248.setForeground(new java.awt.Color(255, 255, 255));
        jLabel248.setText("Accueil");

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton75)
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel246)))
                .addGap(10, 10, 10)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton76)
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel247)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel248))
                    .addComponent(jButton77))
                .addContainerGap())
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton75)
                    .addComponent(jButton76)
                    .addComponent(jButton77))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel246)
                    .addComponent(jLabel247)
                    .addComponent(jLabel248))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel155.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(255, 21, 0));
        jLabel155.setText("Table Gestion Tele");

        javax.swing.GroupLayout pan_tele_gestionLayout = new javax.swing.GroupLayout(pan_tele_gestion);
        pan_tele_gestion.setLayout(pan_tele_gestionLayout);
        pan_tele_gestionLayout.setHorizontalGroup(
            pan_tele_gestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_gestionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tele_gestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
                    .addGroup(pan_tele_gestionLayout.createSequentialGroup()
                        .addComponent(jLabel155)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tele_gestionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pan_tele_gestionLayout.setVerticalGroup(
            pan_tele_gestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_gestionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel155)
                .addGap(1, 1, 1)
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(599, Short.MAX_VALUE))
        );

        pan_mouv_tele.add(pan_tele_gestion, "card6");

        pan_tele_horaire.setBackground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 0));
        jLabel5.setText("Horaire d'agent");

        tble_tele_horaire.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tble_tele_horaire);

        jPanel17.setBackground(new java.awt.Color(0, 0, 0));

        rb_date.setBackground(new java.awt.Color(0, 0, 0));
        rb_date.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rb_date.setForeground(new java.awt.Color(255, 255, 255));
        rb_date.setText("Date");
        rb_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_dateActionPerformed(evt);
            }
        });

        rb_nom.setBackground(new java.awt.Color(0, 0, 0));
        rb_nom.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rb_nom.setForeground(new java.awt.Color(255, 255, 255));
        rb_nom.setText("Nom Agent");
        rb_nom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_nomActionPerformed(evt);
            }
        });

        cdar_date_horaire.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cdar_date_horaireKeyPressed(evt);
            }
        });

        txt_nom_horaire.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nom_horaireKeyPressed(evt);
            }
        });

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/search.png"))); // NOI18N
        jButton15.setToolTipText("Recherche");
        jButton15.setContentAreaFilled(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/voir tous.png"))); // NOI18N
        jButton16.setToolTipText("Afficher tous les enregistrements");
        jButton16.setContentAreaFilled(false);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel249.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel249.setForeground(new java.awt.Color(255, 255, 255));
        jLabel249.setText("Rechercher");

        jLabel258.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel258.setForeground(new java.awt.Color(255, 255, 255));
        jLabel258.setText("Afficher tous les enregistrements");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rb_date)
                    .addComponent(rb_nom))
                .addGap(23, 23, 23)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cdar_date_horaire, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nom_horaire, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel249)))
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jLabel258)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jButton16)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(rb_date))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cdar_date_horaire, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nom_horaire, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rb_nom)))
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jButton16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel249)
                    .addComponent(jLabel258)))
        );

        javax.swing.GroupLayout pan_tele_horaireLayout = new javax.swing.GroupLayout(pan_tele_horaire);
        pan_tele_horaire.setLayout(pan_tele_horaireLayout);
        pan_tele_horaireLayout.setHorizontalGroup(
            pan_tele_horaireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_horaireLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tele_horaireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tele_horaireLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(195, 195, 195)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE))
                .addContainerGap())
        );
        pan_tele_horaireLayout.setVerticalGroup(
            pan_tele_horaireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_tele_horaireLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_tele_horaireLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_tele_horaireLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(116, 116, 116))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_tele_horaireLayout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(573, Short.MAX_VALUE))
        );

        pan_mouv_tele.add(pan_tele_horaire, "card7");

        javax.swing.GroupLayout pan_teleLayout = new javax.swing.GroupLayout(pan_tele);
        pan_tele.setLayout(pan_teleLayout);
        pan_teleLayout.setHorizontalGroup(
            pan_teleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_teleLayout.createSequentialGroup()
                .addComponent(pan_tele_bouton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pan_mouv_tele, javax.swing.GroupLayout.PREFERRED_SIZE, 977, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pan_teleLayout.setVerticalGroup(
            pan_teleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan_mouv_tele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pan_tele_bouton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pan_mouv.add(pan_tele, "card6");

        pan_admi.setBackground(new java.awt.Color(37, 37, 38));

        pan_admi_bouton.setBackground(new java.awt.Color(37, 37, 38));

        jButton37.setText("Technique");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton38.setText("Presence");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jButton39.setText("Personnel");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton47.setText("Radio");
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });

        jButton48.setText("Télé");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_admi_boutonLayout = new javax.swing.GroupLayout(pan_admi_bouton);
        pan_admi_bouton.setLayout(pan_admi_boutonLayout);
        pan_admi_boutonLayout.setHorizontalGroup(
            pan_admi_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_admi_boutonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_admi_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton47, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pan_admi_boutonLayout.setVerticalGroup(
            pan_admi_boutonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_admi_boutonLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jButton37)
                .addGap(47, 47, 47)
                .addComponent(jButton38)
                .addGap(18, 18, 18)
                .addComponent(jButton39)
                .addGap(33, 33, 33)
                .addComponent(jButton47)
                .addGap(27, 27, 27)
                .addComponent(jButton48)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pan_mouv_admi.setLayout(new java.awt.CardLayout());

        pan_dmin.setBackground(new java.awt.Color(0, 0, 0));

        jLabel37.setFont(new java.awt.Font("Segoe UI Light", 0, 21)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 51, 0));
        jLabel37.setText("Administrateur");

        jPanel72.setBackground(new java.awt.Color(0, 0, 0));
        jPanel72.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)), "Attribution de droit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Light", 0, 14), java.awt.Color.white)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Departement");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Administracteur", "Direction technique", "Direction du personnel", "Coordination télé", "Coordination radio", "Authentification" }));
        jComboBox1.setSelectedIndex(-1);

        jLabel110.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(255, 255, 255));
        jLabel110.setText("Mot de passe");

        jLabel111.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(255, 255, 255));
        jLabel111.setText("Nom de l'Agent");

        nomag_adm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                nomag_admPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel112.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(255, 255, 255));
        jLabel112.setText("PostNom");

        jLabel116.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(255, 255, 255));
        jLabel116.setText("Prenom");

        jPanel73.setBackground(new java.awt.Color(0, 0, 0));

        jButton103.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/init.png"))); // NOI18N
        jButton103.setToolTipText("Initialiser");
        jButton103.setContentAreaFilled(false);

        jButton102.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/add.png"))); // NOI18N
        jButton102.setToolTipText("Enregistrer");
        jButton102.setContentAreaFilled(false);

        jLabel205.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel205.setForeground(new java.awt.Color(255, 255, 255));
        jLabel205.setText("Enregistrer");

        jLabel206.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel206.setForeground(new java.awt.Color(255, 255, 255));
        jLabel206.setText("Initialiser");

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton102)
                    .addGroup(jPanel73Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel205)))
                .addGap(33, 33, 33)
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel73Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel206))
                    .addComponent(jButton103))
                .addContainerGap())
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton102)
                    .addComponent(jButton103))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel205)
                    .addComponent(jLabel206))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel72Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel72Layout.createSequentialGroup()
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel110))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, 125, Short.MAX_VALUE)
                                    .addComponent(jTextField1)))
                            .addGroup(jPanel72Layout.createSequentialGroup()
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel111)
                                    .addComponent(jLabel112)
                                    .addComponent(jLabel116))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pag_adm, 0, 123, Short.MAX_VALUE)
                                    .addComponent(nomag_adm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(preag_adm)))))
                    .addGroup(jPanel72Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel110))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomag_adm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel111))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pag_adm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel112))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(preag_adm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel116))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tble_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane20.setViewportView(tble_user);

        jPanel82.setBackground(new java.awt.Color(0, 0, 0));

        jButton108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/Edit.png"))); // NOI18N
        jButton108.setToolTipText("Modifier");
        jButton108.setContentAreaFilled(false);

        jButton109.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/delete.png"))); // NOI18N
        jButton109.setToolTipText("Supprimer");
        jButton109.setContentAreaFilled(false);

        jButton110.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/accueil.png"))); // NOI18N
        jButton110.setContentAreaFilled(false);

        jLabel220.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel220.setForeground(new java.awt.Color(255, 255, 255));
        jLabel220.setText("Modifier");

        jLabel225.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel225.setForeground(new java.awt.Color(255, 255, 255));
        jLabel225.setText("Supprimer");

        jLabel226.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel226.setForeground(new java.awt.Color(255, 255, 255));
        jLabel226.setText("Accueil");

        javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
        jPanel82.setLayout(jPanel82Layout);
        jPanel82Layout.setHorizontalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton108)
                    .addGroup(jPanel82Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel220)))
                .addGap(18, 18, 18)
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton109)
                    .addGroup(jPanel82Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel225)))
                .addGap(18, 18, 18)
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel82Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel226))
                    .addComponent(jButton110))
                .addContainerGap())
        );
        jPanel82Layout.setVerticalGroup(
            jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel82Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton109)
                    .addComponent(jButton110, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton108))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel220)
                    .addComponent(jLabel225)
                    .addComponent(jLabel226))
                .addContainerGap())
        );

        javax.swing.GroupLayout pan_dminLayout = new javax.swing.GroupLayout(pan_dmin);
        pan_dmin.setLayout(pan_dminLayout);
        pan_dminLayout.setHorizontalGroup(
            pan_dminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_dminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_dminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pan_dminLayout.createSequentialGroup()
                        .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pan_dminLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_dminLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162))
        );
        pan_dminLayout.setVerticalGroup(
            pan_dminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_dminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addGroup(pan_dminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel82, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pan_mouv_admi.add(pan_dmin, "card2");

        javax.swing.GroupLayout pan_admiLayout = new javax.swing.GroupLayout(pan_admi);
        pan_admi.setLayout(pan_admiLayout);
        pan_admiLayout.setHorizontalGroup(
            pan_admiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_admiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_admi_bouton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pan_mouv_admi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pan_admiLayout.setVerticalGroup(
            pan_admiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_admiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_admi_bouton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pan_mouv_admi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pan_mouv.add(pan_admi, "card7");

        javax.swing.GroupLayout pan_dirLayout = new javax.swing.GroupLayout(pan_dir);
        pan_dir.setLayout(pan_dirLayout);
        pan_dirLayout.setHorizontalGroup(
            pan_dirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_dirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_titre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pan_dirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pan_dirLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pan_mouv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pan_dirLayout.setVerticalGroup(
            pan_dirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_dirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_titre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(542, Short.MAX_VALUE))
            .addGroup(pan_dirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_dirLayout.createSequentialGroup()
                    .addContainerGap(84, Short.MAX_VALUE)
                    .addComponent(pan_mouv, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pan_base.add(pan_dir, "card3");

        pan_aut.setBackground(new java.awt.Color(37, 37, 38));
        pan_aut.setToolTipText("");
        pan_aut.setMaximumSize(new java.awt.Dimension(1200, 620));
        pan_aut.setMinimumSize(new java.awt.Dimension(1200, 620));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/User-Login.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        mot_de_passe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mot_de_passe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mot_de_passeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mot_de_passeKeyReleased(evt);
            }
        });

        ok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/ok.png"))); // NOI18N
        ok.setBorderPainted(false);
        ok.setContentAreaFilled(false);
        ok.setPreferredSize(new java.awt.Dimension(35, 30));
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel1.setText("Brt Africa");

        m.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        m.setForeground(new java.awt.Color(255, 0, 0));
        m.setText("Mot de passe incorrect");

        yes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/yes.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(mot_de_passe, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(m))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(mot_de_passe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(yes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m)))
                .addContainerGap())
        );

        load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/301 (3).GIF"))); // NOI18N
        load.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(load, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(load, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pan_autLayout = new javax.swing.GroupLayout(pan_aut);
        pan_aut.setLayout(pan_autLayout);
        pan_autLayout.setHorizontalGroup(
            pan_autLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_autLayout.createSequentialGroup()
                .addContainerGap(743, Short.MAX_VALUE)
                .addGroup(pan_autLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_autLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(263, 263, 263))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_autLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))))
        );
        pan_autLayout.setVerticalGroup(
            pan_autLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_autLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(280, Short.MAX_VALUE))
        );

        pan_base.add(pan_aut, "card2");

        javax.swing.GroupLayout panLayout = new javax.swing.GroupLayout(pan);
        pan.setLayout(panLayout);
        panLayout.setHorizontalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panLayout.setVerticalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_base, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        MenuBar.setBackground(java.awt.SystemColor.textHighlight);
        MenuBar.setBorderPainted(false);
        MenuBar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenu1.setText("Fichier");
        jMenu1.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        jMenu1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jMenu1ComponentShown(evt);
            }
        });
        MenuBar.add(jMenu1);

        jMenu2.setText("Edition"); // NOI18N
        jMenu2.setFont(new java.awt.Font("Segoe UI Light", 0, 13)); // NOI18N
        MenuBar.add(jMenu2);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1256, 701));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jMenu1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ComponentShown

    private void mot_de_passeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mot_de_passeKeyPressed
        m.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_mot_de_passeKeyPressed

    private void mot_de_passeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mot_de_passeKeyReleased
 if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            okActionPerformed(null);
        }
    }//GEN-LAST:event_mot_de_passeKeyReleased

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        verify();
    }//GEN-LAST:event_okActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Repaindre(pan_mouv_tech, pan_tech_stock);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_nmbreenfant_agActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nmbreenfant_agActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nmbreenfant_agActionPerformed

    private void jTextField44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField44ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField44ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        Repaindre(pan_mouv_tele, pan_tele_prog);
        
        // Pour les combo Agent
        cbo_nomAgent_pro.removeAllItems();
        cbo_postNom_pro.removeAllItems();
        cbo_agent();
        cbo_agent_affiche(cbo_nomAgent_pro.getSelectedItem().toString(), txt_prenomAgent_pro, cbo_postNom_pro);
        
        // Pour le combo activité
        nomActi_pro.removeAllItems();
        cbo_activite("Television");
        cbo_activite_affiche(nomActi_pro.getSelectedItem().toString(), typeAgent_pro, "Television");
        
        InitProgrammeTele();
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Repaindre(pan_mouv_tech, pan_tech_enr);
        
        cmbo_emplacement.removeAllItems();
        cbo_emplacement();
        InitMateriel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       Repaindre(pan_mouv_tech, pan_tech_es);
        
        // Pour les combo Agent Entree
        cbo_agent_ev.removeAllItems();
        cbo_Postagent_ev.removeAllItems();
        cbo_agent();
        cbo_agent_affiche(cbo_agent_ev.getSelectedItem().toString(), txt_preagent_tech, cbo_Postagent_ev);
        
        // Pour les combo Agent Sorti
        cmbo_agent_Sorti.removeAllItems();
        cmbo_Postagent_Sorti.removeAllItems();
        cbo_agent();
        cbo_agent_affiche(cmbo_agent_Sorti.getSelectedItem().toString(), txt_preagent_tech, cmbo_Postagent_Sorti);

        
        cmbo_empl_ev.removeAllItems();
        cmbo_emplacement.removeAllItems();
        cmbo_empl_sor.removeAllItems();
        cbo_emplacement();
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Repaindre(pan_mouv_tech, pan_tech_gestion);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
       Repaindre(pan_mouv_tele, pan_tele_horaire);
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
       Repaindre(pan_mouv_tele, pan_tele_act);
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        Repaindre(pan_mouv_tele, pan_tele_affi);
        table_tele();
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
       Repaindre(pan_mouv_admi, pan_tech);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        Repaindre(pan_mouv_admi, pan_presence);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        Repaindre(pan_mouv_admi, pan_personnel);
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
//        Repaindre(pan_mouv_admi, pan_radio);
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        Repaindre(pan_mouv_admi, pan_tele);
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    if(txt_type_materiel.getText().equals("") || txt_caracteristique.getText().equals("") || cmbo_disponibilite.getSelectedIndex() == -1 || cmbo_emplacement.getSelectedIndex() == -1){
    JOptionPane.showMessageDialog(null, "Aucun champ vide ne doit être vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    Materiel materiel = new Materiel();
    materiel.setType_mat(txt_type_materiel.getText());
    materiel.setCaracteristique(txt_caracteristique.getText());
    materiel.setDisponibilite(cmbo_disponibilite.getSelectedItem().toString());
    Emplacement emplacement = new Emplacement();
    emplacement.setId_emp(new EmplacementDAO().VerifierId(cmbo_emplacement.getSelectedItem().toString()));
    materiel.setEmplacement(emplacement);
    materiel = materielDao.create(materiel);
    InitMateriel();
    }
    }//GEN-LAST:event_jButton5ActionPerformed
public void InitMateriel(){
txt_type_materiel.setText("");
txt_type_materiel.setText("");
txt_caracteristique.setText("");
cmbo_disponibilite.setSelectedIndex(-1);
cmbo_emplacement.setSelectedIndex(-1);}
    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
    if (txt_emplacement.getText().equals("")){
    JOptionPane.showMessageDialog(null, "Champ vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);}
    else{
    Emplacement emplacement = new Emplacement();
    emplacement.setNom_emp(txt_emplacement.getText());
    emplacement = emplacementDao.create(emplacement);
    table_emplacement();
    txt_emplacement.setText("");
    }
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
    
    if(tble_emplacement.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir l'emplacement a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_emplacement.getSelectedRow();
    int recupc = tble_emplacement.getSelectedColumn();
    Emplacement emplacement = new Emplacement();
    emplacement.setId_emp(Integer.valueOf(tble_emplacement.getValueAt(recupl, 0).toString()).intValue());
    emplacement.setNom_emp(tble_emplacement.getValueAt(recupl, 1).toString());
    emplacement = emplacementDao.update(emplacement);
    table_emplacement();}
    }//GEN-LAST:event_jButton51ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
    Repaindre(pan_mouv_tech, pan_tech_emplacement);
    cmbo_emplacement.removeAllItems();
    cbo_emplacement();
    
    txt_emplacement.setText(""); // Pour initialiser Emplacement
    }//GEN-LAST:event_jButton49ActionPerformed

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
    if(tble_emplacement.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir l'emplacement a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);}
    else{
    int recupl = tble_emplacement.getSelectedRow();
    int recupc = tble_emplacement.getSelectedColumn();
    Emplacement emplacement = new Emplacement();
    emplacement.setId_emp(Integer.valueOf(tble_emplacement.getValueAt(recupl, 0).toString()).intValue());
    emplacement.setNom_emp(tble_emplacement.getValueAt(recupl, 1).toString());
    emplacementDao.delete(emplacement);
    table_emplacement();}
    }//GEN-LAST:event_jButton52ActionPerformed

    private void cmbo_emplacementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbo_emplacementActionPerformed

    }//GEN-LAST:event_cmbo_emplacementActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        InitMateriel();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Repaindre(pan_mouv_tech, pan_tech_accueil);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        if(txt_nom_parrain.getText().equals("") || txt_prenom_parrain.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Aucun champ vide ne doit être vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
        else{
            Parrain parrain = new Parrain();
            parrain.setNom_parrain(txt_nom_parrain.getText());
            parrain.setPrenom_parrain(txt_prenom_parrain.getText());
            String numero = plus.getText() + code.getSelectedItem().toString() + txt_numtel_parrain.getText();
            parrain.setNum_tel_parrain(numero);
            parrain = parrainDao.create(parrain);
            table_parrain();
            initParrain();
        }
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
    if(tble_parrain.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir le parrain a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_parrain.getSelectedRow();
    int recupc = tble_parrain.getSelectedColumn();
    Parrain parrain = new Parrain();
    parrain.setId_parrain(Integer.valueOf(tble_parrain.getValueAt(recupl, 0).toString()).intValue());
    parrain.setNom_parrain(tble_parrain.getValueAt(recupl, 1).toString());
    parrain.setPrenom_parrain(tble_parrain.getValueAt(recupl, 2).toString());
    parrain.setNum_tel_parrain(tble_parrain.getValueAt(recupl, 3).toString());
    parrain = parrainDao.update(parrain);
    table_parrain();
}
    }//GEN-LAST:event_jButton62ActionPerformed
    public void initParrain(){
    txt_nom_parrain.setText("");
    txt_prenom_parrain.setText("");
    txt_numtel_parrain.setText("");
    code.setSelectedIndex(-1);
    }
    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
        if(tble_parrain.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir le parrain a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);}
    else{
    int recupl = tble_parrain.getSelectedRow();
    int recupc = tble_parrain.getSelectedColumn();
    Parrain parrain = new Parrain();
    parrain.setId_parrain(Integer.valueOf(tble_parrain.getValueAt(recupl, 0).toString()).intValue());
    parrainDao.delete(parrain);
    table_parrain();
        }
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
    initParrain();
    }//GEN-LAST:event_jButton64ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        if(txt_fonction.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Aucun champ vide ne doit être vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
        else{
    Fonction fonction = new Fonction();
    fonction.setFonction(txt_fonction.getText());
    fonction = fonctionDao.create(fonction);
    txt_fonction.setText("");
    table_fonction();
        }
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
    if(tble_fonction.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir la fonction a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_fonction.getSelectedRow();
    int recupc = tble_fonction.getSelectedColumn();
    Fonction fonction = new Fonction();
    fonction.setId_fonction(Integer.valueOf(tble_fonction.getValueAt(recupl, 0).toString()).intValue());
    fonction.setFonction(tble_fonction.getValueAt(recupl, 1).toString());
    fonction = fonctionDao.update(fonction);
    table_fonction();
}
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        if(tble_fonction.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir la fonction a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);}
    else{
    int recupl = tble_fonction.getSelectedRow();
    int recupc = tble_fonction.getSelectedColumn();
    Fonction fonction = new Fonction();
    fonction.setId_fonction(Integer.valueOf(tble_fonction.getValueAt(recupl, 0).toString()).intValue());
    fonctionDao.delete(fonction);
    table_fonction();
        }   
    }//GEN-LAST:event_jButton59ActionPerformed

    private void cb_nomparrain_agActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_nomparrain_agActionPerformed
//        t = new Thread(new sevine());//Thread 
//       t.start();//Thread
    }//GEN-LAST:event_cb_nomparrain_agActionPerformed
public void cbo_parrain_affiche(){
try {
            ResultSet r = new ParrainDAO().AutreInfo(cb_nomparrain_ag.getSelectedItem().toString());
            while(r.next()){
                txt_prenomparrain_ag.setText(r.getString("prenom_parrain"));
                txt_numtelparrain_ag.setText(r.getString("num_tel_parrain"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }}
public void cbo_emplacement_affiche(JComboBox comboBox,JComboBox combobox1, String etat){
    try {
        ResultSet r = new EmplacementDAO().AutreInfoImpo(comboBox.getSelectedItem().toString(), etat);
        while(r.next()){
        combobox1.addItem(r.getString("type_mat"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
public void cbo_agent_affiche2(String nom, JTextField textfield1, JComboBox comboBox){
try {
                ResultSet r2 = new AgentDAO().AutreInfo2(nom, comboBox.getSelectedItem().toString());
                while(r2.next()){
                textfield1.setText(r2.getString("prenom_agent"));
                }
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(null, e);
             }
}
public void cbo_agent_affiche( String nom, JTextField textfield1, JComboBox comboBox){
    try {
        ResultSet r = new AgentDAO().AutreInfo(nom);
        while(r.next()){
        comboBox.addItem(r.getString("post_nom_agent"));
        }
        if(comboBox.getItemCount() == 1){
        comboBox.setEnabled(false);
        r.last();
        textfield1.setText(r.getString("prenom_agent"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e+ " : ICI");
    }
}
private void cbo_activite_affiche(String nom_activite, JTextField textField, String departement){
    try {
        ResultSet r = new ActiviteDAO().AutreInfo(nom_activite, departement);
        while(r.next()){
            textField.setText(r.getString("type_act"));
        }
    } catch (Exception e) {
    }
}

    private void jTabbedPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane3MouseClicked
        // POUR AGENT
        cb_nomparrain_ag.removeAllItems();
        cbo_parrain();
        cbo_fonction_ag.removeAllItems();
        cbo_fonction(); 
        cbo_parrain_affiche();
        
        // POUR ENFANT
        cbo_agent_Enfant.removeAllItems();
        txt_postNom_enfant.removeAllItems();
        cbo_agent();
        cbo_agent_affiche(cbo_agent_Enfant.getSelectedItem().toString(), txt_prenomag_enfant, txt_postNom_enfant);
    }//GEN-LAST:event_jTabbedPane3MouseClicked

    private void tbCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbCameraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbCameraActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        try {
            FrameGrabbingControl fgc = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");
Buffer buffer = fgc.grabFrame();
BufferToImage bti = new BufferToImage ((VideoFormat) buffer.getFormat());
Image image = bti.createImage(buffer);
JIResizeImage resize = new JIResizeImage();
bi = (BufferedImage) image;
Image imageresize = resize.rescale(bi, 145,140);
tbCamera.removeAll();
this.setImageButton(this.tbCamera, imageresize);
//

     try {
         String num_tof = new AgentDAO().Matricule();
         nfile = "D:\\Brt Africa\\"+ "00" + num_tof+".jpg";
         File make = new File(nfile);
         
         try {
             ImageIO.write(bi, "jpg", make);
             
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }
     } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e.getMessage());
     }
//
        try {
            File image_chemin = new File (nfile);
            FileInputStream fis = new FileInputStream(image_chemin);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum; (readNum = fis.read(buf)) != -1;){
            bos.write(buf,0,readNum);
            }
             person_image = bos.toByteArray();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Ouvrez le Webcam");
        }

 
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton65ActionPerformed
        tbCamera.removeAll();
        this.tbCamera.add(player.getVisualComponent()).setSize(tbCamera.getWidth(), tbCamera.getHeight());
        
    }//GEN-LAST:event_jButton65ActionPerformed

    private void cb_nomparrain_agPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cb_nomparrain_agPopupMenuWillBecomeInvisible
            cbo_parrain_affiche();
    }//GEN-LAST:event_cb_nomparrain_agPopupMenuWillBecomeInvisible

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        try {
            Agent agent = new Agent();
      agent.setMatricule_agent(txt_matricule_ag.getText());
      agent.setNom_agent(txt_nom_ag.getText());
      agent.setPost_nom_agent(txt_postnom_ag.getText());
      agent.setPrenom_agent(txt_prenom_ag.getText());
      agent.setLieu_de_naissance_agent(txt_lieunaiss_ag.getText());
      agent.setDate_de_naissance_agent(((JTextField)cdar_datenaissance_ag.getDateEditor().getUiComponent()).getText());
////    pst.setString(2, ((JTextField)txt_date.getDateEditor().getUiComponent()).getText());
      agent.setNom_pere(txt_nompere_ag.getText());
      agent.setNom_mere(txt_nommere_ag.getText());
      agent.setEtat_civil_agent(cbo_etat_civil.getSelectedItem().toString());
      agent.setNom_conj(txt_marié_ag.getText());
      agent.setNombre_enfant(Integer.valueOf(txt_nmbreenfant_ag.getText()).intValue());
      agent.setNationalite_agent(txt_nationalite_ag.getText());
      agent.setProvince_agent(txt_province_ag.getText());
      agent.setDistrict_agent(txt_district_ag.getText());
      agent.setTerritoire_agent(txt_teritoire_ag.getText());
      agent.setSecteur_agent(txt_secteur_ag.getText());
      agent.setNum_tel_agent(txt_tel_ag.getText());
      agent.setEmail_agent(txt_email_ag.getText());
      agent.setCommune_agent(txt_commune_ag.getSelectedItem().toString());
      agent.setQuartier_agent(txt_quartier_ag.getText());
      agent.setAvenue_agent(txt_avenue_ag.getText());
      agent.setSexe(cbo_sexe.getSelectedItem().toString().charAt(0));
      agent.setNum(txt_numadr_ag.getText());
      agent.setTel_urgent(txt_tel_urgent.getText());
      agent.setDegre_parental(cbo_degre_urgent.getSelectedItem().toString());
      agent.setCommune_urgent(txt_commune_urgent.getSelectedItem().toString());
      agent.setQuartier_urgent(txt_quartier_urgent.getText());
      agent.setAvenue_urgent(txt_avenue_urgent.getText());
      agent.setNum_urgent(txt_num_urgent.getText());
      agent.setPhoto_agent(person_image);
//       POUR PARRAIN
      Parrain parrain = new Parrain();
      parrain.setId_parrain(new ParrainDAO().VerifierId(cb_nomparrain_ag.getSelectedItem().toString(), txt_prenomparrain_ag.getText()));
      agent.setParrain(parrain);
      agent = agentDao.create(agent); // FIN AGENT
      
      
      Assumer assumer = new Assumer();
      Agent agent_2 = new Agent(); // POUR AGENT
      agent_2.setId_agent(new AgentDAO().dernier_id());
      assumer.setAgent(agent_2);
      
      Fonction fonction = new Fonction(); // POUR FONCTION
      fonction.setId_fonction(new FonctionDAO().VerifierId(cbo_fonction_ag.getSelectedItem().toString()));
      assumer.setFonction(fonction);
      
      assumer.setDate_debut(dt.format(cdar_datedebut_ag.getDate()));
      assumer = assumerDao.create(assumer);
      JOptionPane.showMessageDialog(null, "Enregistrement de l'agent effectué avec reussi","Brt Appli", JOptionPane.INFORMATION_MESSAGE);
     InitPersonnel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
      Matricule();
      table_personnel();
      
    
    }//GEN-LAST:event_jButton22ActionPerformed
private void InitPersonnel(){
    txt_matricule_ag.setText("");
    txt_nom_ag.setText("");
    txt_postnom_ag.setText("");
    txt_prenom_ag.setText("");
    txt_lieunaiss_ag.setText("");
    cdar_datenaissance_ag.setDate(null);
    txt_nompere_ag.setText("");
    txt_nommere_ag.setText("");
    cbo_etat_civil.setSelectedIndex(-1);
    txt_marié_ag.setText("");
    txt_nmbreenfant_ag.setText("");
    txt_nationalite_ag.setText("");
    txt_province_ag.setText("");
    txt_district_ag.setText("");
    txt_teritoire_ag.setText("");
    txt_secteur_ag.setText("");
    txt_tel_ag.setText("");
    txt_email_ag.setText("");
    txt_commune_ag.setSelectedIndex(-1);
    txt_quartier_ag.setText("");
    txt_avenue_ag.setText("");
    cbo_sexe.setSelectedIndex(-1);
    txt_numadr_ag.setText("");
    txt_tel_urgent.setText("");
    cbo_degre_urgent.setSelectedIndex(-1);
    txt_commune_urgent.setSelectedIndex(-1);
    txt_quartier_urgent.setText("");
    txt_avenue_urgent.setText("");
    txt_num_urgent.setText("");
    tbCamera.removeAll();
    this.tbCamera.add(player.getVisualComponent()).setSize(tbCamera.getWidth(), tbCamera.getHeight());
      
}
    private void cbo_etat_civilPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_etat_civilPopupMenuWillBecomeInvisible
if (cbo_etat_civil.getSelectedIndex() == 0){
txt_marié_ag.setEnabled(false);
txt_marié_ag.setText("");
}
if(cbo_etat_civil.getSelectedIndex() == 1){
txt_marié_ag.setEnabled(true);
}

if(cbo_etat_civil.getSelectedIndex() == 2){
txt_marié_ag.setEnabled(false);
txt_marié_ag.setText("");}
    }//GEN-LAST:event_cbo_etat_civilPopupMenuWillBecomeInvisible

    private void txt_nmbreenfant_agKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nmbreenfant_agKeyReleased
if (!isNumeric(evt.getKeyChar())){
    txt_nmbreenfant_ag.setText(txt_nmbreenfant_ag.getText().replace(String.valueOf(evt.getKeyChar()), ""));}
    }//GEN-LAST:event_txt_nmbreenfant_agKeyReleased

    private void cbo_agent_EnfantPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_agent_EnfantPopupMenuWillBecomeInvisible
         txt_postNom_enfant.removeAllItems();
         txt_postNom_enfant.enable(true);
        cbo_agent_affiche(cbo_agent_Enfant.getSelectedItem().toString(),txt_prenomag_enfant, txt_postNom_enfant);
        
    }//GEN-LAST:event_cbo_agent_EnfantPopupMenuWillBecomeInvisible

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        if(txt_nomEnfant.getText().equals("")|| txt_postNomEnfant.getText().equals("") || txt_prenomEnfant.getText().equals("")|| cbo_agent_Enfant.getSelectedIndex() == -1||txt_postNom_enfant.getSelectedItem().toString().equals("")){
        JOptionPane.showMessageDialog(null, "Aucun champ vide ne doit être vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
        else{
      Enfant enfant = new Enfant();
      enfant.setNom_enfant(txt_nomEnfant.getText());
      enfant.setPost_nom_enfant(txt_postNomEnfant.getText());
      enfant.setPrenom(txt_prenomEnfant.getText());
      Agent agent = new Agent();
      agent.setId_agent(new AgentDAO().VerifierId(cbo_agent_Enfant.getSelectedItem().toString(), txt_postNom_enfant.getSelectedItem().toString()));
      enfant.setAgent(agent);
      enfant = enfantDao.create(enfant);
      table_enfant();
      InitEnfant();
        }
    }//GEN-LAST:event_jButton24ActionPerformed
private void InitEnfant(){
txt_nomEnfant.setText("");
txt_postNomEnfant.setText("");
txt_prenomEnfant.setText("");
cbo_agent_Enfant.setSelectedIndex(-1);
txt_postNom_enfant.setSelectedIndex(-1);
txt_prenomag_enfant.setText("");

}
    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
InitPersonnel();
    }//GEN-LAST:event_jButton23ActionPerformed
public String matricule(){
int ligne = tble_personnel.getSelectedRow();
String matricule = tble_personnel.getValueAt(ligne, 0).toString();
return matricule;
}
    private void tble_personnelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tble_personnelMouseClicked
    try{
        System.out.println(matricule());
        
        Modif_agent md = new Modif_agent(null, true);
        md.setVisible(true);
        
    }catch(Exception e){
//    JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_tble_personnelMouseClicked
 
    private void cmbo_empl_evPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbo_empl_evPopupMenuWillBecomeInvisible
       cmbo_mat_ev.removeAllItems();
        cbo_emplacement_affiche(cmbo_empl_ev, cmbo_mat_ev, "Non");        
    }//GEN-LAST:event_cmbo_empl_evPopupMenuWillBecomeInvisible
private void InitEvenementEntree(){
    
//        java.util.Date jour = cdarDate_ev.getDate();
//    SimpleDateFormat sjour = new SimpleDateFormat("dd/mm/yyyy");
//  JOptionPane.showMessageDialog(null, sjour.format(jour));
    
cmboJour.setSelectedIndex(-1);
cdarDate_ev.setDate(null);
cmboEtatMateriel_ev.setSelectedIndex(-1);
quantite_ev.setSelectedIndex(-1);
cmbo_empl_ev.setSelectedIndex(-1);
cmbo_mat_ev.setSelectedIndex(-1);
cbo_agent_ev.setSelectedIndex(-1);
cbo_Postagent_ev.setSelectedIndex(-1);
txt_preagent_tech.setText("");

}
    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
//    cdarDate_ev.getDate().toString().equals("")|| 
            
    if(cmboEtatMateriel_ev.getSelectedIndex() == -1 || cmboEtatMateriel_ev.getSelectedIndex() == -1 || cmbo_mat_ev.getSelectedIndex() == -1 || cmbo_empl_ev.getSelectedIndex() == -1 || cbo_agent_ev.getSelectedIndex() == -1 ||
       txt_preagent_tech.getText().equals("") || cbo_Postagent_ev.getSelectedIndex() == -1 || ((JTextField)cdarDate_ev.getDateEditor().getUiComponent()).getText().equals("")){
        
     JOptionPane.showMessageDialog(null, "Aucun champ vide ne doit être vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{    
    Evenement_mat evenement_mat = new Evenement_mat();
    evenement_mat.setType_ev("Entrée");
    evenement_mat.setJour(cmboJour.getSelectedItem().toString());
    evenement_mat.setDate_ev(((JTextField)cdarDate_ev.getDateEditor().getUiComponent()).getText());
    evenement_mat.setHeure(heure_ev.getText());
    evenement_mat.setEtat_mat(cmboEtatMateriel_ev.getSelectedItem().toString());
    evenement_mat.setQuantite(Integer.valueOf(quantite_ev.getSelectedItem().toString()).intValue());
    // Pour recuperer l'id du MATERIEL
    Materiel materiel = new Materiel();
    int id = new EmplacementDAO().VerifierId(cmbo_empl_ev.getSelectedItem().toString());
    int id_Mat = new MaterielDAO().VerifierId(cmbo_mat_ev.getSelectedItem().toString(), id);
    materiel.setId_mat(id_Mat);
    evenement_mat.setMateriel(materiel);
    // Pour recuperer l'id du AGENT
    Agent agent = new Agent();
    agent.setId_agent(new AgentDAO().VerifierId(cbo_agent_ev.getSelectedItem().toString(), cbo_Postagent_ev.getSelectedItem().toString()));
    evenement_mat.setAgent(agent);
    
    evenement_mat = evenement_matDao.create(evenement_mat);
    
    // Modifié la disponibilite du materiel
    Materiel materielUpdate = new Materiel();
    materielUpdate.setId_mat(id_Mat);
    materielUpdate.setDisponibilite("Oui");
    materielUpdate = new MaterielDAO().updateDisponibilite(materielUpdate);
    
    InitEvenementEntree();
    }
    }//GEN-LAST:event_jButton54ActionPerformed

    private void cbo_agent_evPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_agent_evPopupMenuWillBecomeInvisible
//        cbo_agent_affiche(cbo_agent_ev.getSelectedItem().toString(), txt_preagent_tech, txt_postagent_tech);
     try {
            cbo_Postagent_ev.removeAllItems();
        cbo_Postagent_ev.setEnabled(true);
        cbo_agent_affiche(cbo_agent_ev.getSelectedItem().toString(),txt_preagent_tech, cbo_Postagent_ev);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbo_agent_evPopupMenuWillBecomeInvisible

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
    InitEvenementEntree();
    }//GEN-LAST:event_jButton55ActionPerformed

    private void cmbo_empl_sorPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbo_empl_sorPopupMenuWillBecomeInvisible
        try {
            cmbo_mat_evSor.removeAllItems();
            cbo_emplacement_affiche(cmbo_empl_sor, cmbo_mat_evSor, "Oui");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veuillez faire un choix"+ e);
        }
        
    }//GEN-LAST:event_cmbo_empl_sorPopupMenuWillBecomeInvisible

    private void cmbo_agent_SortiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbo_agent_SortiPopupMenuWillBecomeInvisible
         try {
            cmbo_Postagent_Sorti.removeAllItems();
        cmbo_Postagent_Sorti.setEnabled(true);
        cbo_agent_affiche(cmbo_agent_Sorti.getSelectedItem().toString(),txt_prenomSorti, cmbo_Postagent_Sorti);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmbo_agent_SortiPopupMenuWillBecomeInvisible
    private void InitEvenementSortie(){
        jour_sorti.setSelectedIndex(-1);
        date_sortie.setDate(null);
        etat_mat_sortie.setSelectedIndex(-1);
        cmbo_quantite_sorti.setSelectedIndex(-1);
        cmbo_empl_sor.setSelectedIndex(-1);
        cmbo_mat_evSor.setSelectedIndex(-1);
        cmbo_agent_Sorti.setSelectedIndex(-1);
        cmbo_Postagent_Sorti.setSelectedIndex(-1);
        txt_prenomSorti.setText("");
    }
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

    if(((JTextField)date_sortie.getDateEditor().getUiComponent()).getText().equals("")|| etat_mat_sortie.getSelectedIndex() == -1 || cmbo_quantite_sorti.getSelectedItem().toString().equals("")||
            cmbo_empl_sor.getSelectedIndex() == -1 || cmbo_mat_evSor.getSelectedIndex() == -1 || cmbo_agent_Sorti.getSelectedIndex() == -1 || cmbo_Postagent_Sorti.getSelectedIndex() == -1 ||
            txt_prenomSorti.getText().equals("") || jour_sorti.getSelectedIndex() == -1){
    JOptionPane.showMessageDialog(null, "Aucun champ vide ne doit être vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
    else{
        
    Evenement_mat evenement_mat = new Evenement_mat();
    evenement_mat.setType_ev("Sortie");
    evenement_mat.setJour(jour_sorti.getSelectedItem().toString());
    evenement_mat.setDate_ev(((JTextField)date_sortie.getDateEditor().getUiComponent()).getText());
    evenement_mat.setHeure(heureSortie.getText());
    evenement_mat.setEtat_mat(etat_mat_sortie.getSelectedItem().toString());
    evenement_mat.setQuantite(Integer.valueOf(cmbo_quantite_sorti.getSelectedItem().toString()).intValue());
    // Pour recuperer l'id du MATERIEL
    Materiel materiel = new Materiel();
    int id = new EmplacementDAO().VerifierId(cmbo_empl_sor.getSelectedItem().toString()); // Id de l'Emplacement
    int id_Mat = new MaterielDAO().VerifierId(cmbo_mat_evSor.getSelectedItem().toString(), id); // Id du Materiel
    materiel.setId_mat(id_Mat);
    evenement_mat.setMateriel(materiel);
    // Pour recuperer l'id du AGENT
    Agent agent = new Agent();
    agent.setId_agent(new AgentDAO().VerifierId(cmbo_agent_Sorti.getSelectedItem().toString(), cmbo_Postagent_Sorti.getSelectedItem().toString()));
    evenement_mat.setAgent(agent);
    
    evenement_mat = evenement_matDao.create(evenement_mat);
    
    // Modifié la disponibilite du materiel
    Materiel materielUpdate = new Materiel();
    materielUpdate.setId_mat(id_Mat);
    materielUpdate.setDisponibilite("Non");
    materielUpdate = new MaterielDAO().updateDisponibilite(materielUpdate);
    
    InitEvenementSortie();
        try {
            JasperDesign jd = JRXmlLoader.load("C:\\Users\\Gauss\\Documents\\NetBeansProjects\\Tfc_calendar\\Materiel_Bon.jrxml");
            String sql = "SELECT * FROM t_materiel INNER JOIN t_emplacement ON t_materiel.id_emp = t_emplacement.id_emp INNER JOIN t_evenement_mat ON t_materiel.id_mat = t_evenement_mat.id_mat INNER JOIN t_agent ON t_evenement_mat.id_agent = t_agent.id_agent INNER JOIN t_assumer ON t_agent.id_agent = t_assumer.id_agent INNER JOIN t_fonction ON t_assumer.id_fonction = t_fonction.id_fonction WHERE t_agent.nom_agent LIKE '"+cmbo_agent_Sorti.getSelectedItem().toString()+"%' and t_agent.post_nom_agent LIKE '"+cmbo_Postagent_Sorti.getSelectedItem().toString()+"%'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
        }
    }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        InitEvenementSortie();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
//        InitEvenementSortie();
//        InitEvenementEntree();
//        cbo_agent_Enfant.removeAllItems();
//        cbo_agent_ev.removeAllItems();
//        cmbo_agent_Sorti.removeAllItems();
//        cbo_agent();
        
        cmbo_empl_ev.removeAllItems();
        cmbo_emplacement.removeAllItems();
        cmbo_empl_sor.removeAllItems();
        cbo_emplacement();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void Rech_StockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rech_StockActionPerformed
        try {
        tble_stockMat.setModel(DbUtils.resultSetToTableModel(new MaterielDAO().Recherche(txt_stockRecherche.getText())));
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    }//GEN-LAST:event_Rech_StockActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        table_materiel();
        txt_stockRecherche.setText("");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void txt_stockRechercheKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stockRechercheKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ENTER){
          Rech_StockActionPerformed(null);
      }
    }//GEN-LAST:event_txt_stockRechercheKeyPressed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        
       if(tble_gestionMat.getSelectedRow() == -1){
       JOptionPane.showMessageDialog(null, "Veuillez choisir le materiel a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
       }
       else{
       int recupl = tble_gestionMat.getSelectedRow();
       int recupc = tble_gestionMat.getSelectedColumn();
       Materiel materiel = new Materiel();
       materiel.setId_mat(Integer.valueOf(tble_gestionMat.getValueAt(recupl, 0).toString()).intValue());
       materiel.setType_mat(tble_gestionMat.getValueAt(recupl, 1).toString());
       materiel.setCaracteristique(tble_gestionMat.getValueAt(recupl, 2).toString());
       materiel.setDisponibilite(tble_gestionMat.getValueAt(recupl, 3).toString());
       Emplacement emplacement = new Emplacement();
       emplacement.setId_emp(Integer.valueOf(tble_gestionMat.getValueAt(recupl, 4).toString()));
       materiel.setEmplacement(emplacement);
       materiel = materielDao.update(materiel);
       table_materiel();
       }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed

    if(tble_gestionMat.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir le materiel a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_gestionMat.getSelectedRow();
    int recupc = tble_gestionMat.getSelectedColumn();
    Materiel materiel = new Materiel();
    materiel.setId_mat(Integer.valueOf(tble_gestionMat.getValueAt(recupl, 0).toString()).intValue());
    materielDao.delete(materiel);
    table_materiel();
    }
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
        Repaindre(pan_mouv_tech, pan_tech_accueil);
    }//GEN-LAST:event_jButton53ActionPerformed

    private void quantite_evKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantite_evKeyReleased
if (!isNumeric(evt.getKeyChar())){
    quantite_ev.setSelectedItem(quantite_ev.getSelectedItem().toString().replace(String.valueOf(evt.getKeyChar()), ""));}        // TODO add your handling code here:
    }//GEN-LAST:event_quantite_evKeyReleased

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
        Repaindre(pan_mouv_tech, pan_tech_accueil);
    }//GEN-LAST:event_jButton66ActionPerformed

    private void txt_postNom_enfantMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_postNom_enfantMouseClicked
  
    }//GEN-LAST:event_txt_postNom_enfantMouseClicked

    private void txt_postNom_enfantPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_txt_postNom_enfantPopupMenuWillBecomeInvisible
      cbo_agent_affiche2(cbo_agent_Enfant.getSelectedItem().toString(), txt_prenomag_enfant, txt_postNom_enfant);
    }//GEN-LAST:event_txt_postNom_enfantPopupMenuWillBecomeInvisible

    private void jButton67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton67ActionPerformed
        try {
            if(txt_activite_nom.getText().equals("") || cbo_activite_type.getSelectedItem().toString().equals("") || txt_activite_description.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Champ vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);}
        else{
           
        Activite activite = new Activite();
        activite.setNom_act(txt_activite_nom.getText());
        activite.setType_act(cbo_activite_type.getSelectedItem().toString());
        activite.setDescription_act(txt_activite_description.getText());
        activite.setDepartement("Television");
        activite = activiteDao.create(activite);
        InitActivité();
        
        }  
        table_activite();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Champ vide : "+e, "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton67ActionPerformed

    private void jButton68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton68ActionPerformed
        InitActivité();      
    }//GEN-LAST:event_jButton68ActionPerformed

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed
      if(tble_activite.getSelectedRow() == -1){
          JOptionPane.showMessageDialog(null, "Veuillez choisir l'activité a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
      }
      else{
      int recupl = tble_activite.getSelectedRow();
      int recupc = tble_activite.getSelectedColumn();
      Activite activite = new Activite();
      activite.setId_act(Integer.valueOf(tble_activite.getValueAt(recupl, 0).toString()).intValue());
      activite.setNom_act(tble_activite.getValueAt(recupl, 1).toString());
      activite.setType_act(tble_activite.getValueAt(recupl, 2).toString());
      activite.setDescription_act(tble_activite.getValueAt(recupl, 3).toString());
      activite.setDepartement("Television");
      activite = activiteDao.update(activite);
      }
      table_activite();
        
    }//GEN-LAST:event_jButton69ActionPerformed

    private void jButton70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton70ActionPerformed
          
    if(tble_activite.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir de l'activité a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_activite.getSelectedRow();
    int recupc = tble_activite.getSelectedColumn();
    Activite activite = new Activite();
    activite.setId_act(Integer.valueOf(tble_activite.getValueAt(recupl, 0).toString()).intValue());
    activiteDao.delete(activite);
    }
    table_activite();
        
    }//GEN-LAST:event_jButton70ActionPerformed

    private void jButton71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton71ActionPerformed
         Repaindre(pan_mouv_tele, pan_tele_accueil);
    }//GEN-LAST:event_jButton71ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
//        if(cdar_date_pro.getDate().after(cdar_dateredif.getDate())){
//            JOptionPane.showMessageDialog(null, "La date de rediffusion doit etre apres la date de diffusion");
//        }
//        else{
        if(cbo_redif.getSelectedIndex() == 0){
        if(cdar_date_pro.getDate().after(cdar_dateredif.getDate())){
            JOptionPane.showMessageDialog(null, "La date de rediffusion doit etre apres la date de diffusion", "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
        else{
        CreateProgrammeTele();
        }
        }
        else{
        CreateProgrammeTele();}
      
    }//GEN-LAST:event_jButton13ActionPerformed
    private void CreateProgrammeTele(){
    String heure_debut =  hd.getSelectedItem().toString() +":"+md.getSelectedItem().toString();
               
        String heure_fin = hf.getSelectedItem().toString()+":"+mf.getSelectedItem().toString();
        Programme programme = new Programme();
        programme.setDate_prog(((JTextField)cdar_date_pro.getDateEditor().getUiComponent()).getText());
        programme.setHeure_debut(heure_debut);
        programme.setHeure_fin(heure_fin);
        programme.setRediffusion(cbo_redif.getSelectedItem().toString());
        programme.setDate_redif(((JTextField)cdar_dateredif.getDateEditor().getUiComponent()).getText());
        programme.setDepartement("Television");
        
        Activite activite = new Activite();
        activite.setId_act(new ActiviteDAO().VerifierId(nomActi_pro.getSelectedItem().toString()));
        programme.setActivite(activite);
        programme = programmeDao.create(programme);
     
        if(cbo_nomAgent_pro.getSelectedIndex() != -1 || cbo_postNom_pro.getSelectedIndex() != -1){
        // Pour la table Animateur
        // Programme
        Animateur animateur = new Animateur();
        Programme programme_ani = new Programme();
        programme_ani.setId_prog(new ProgrammeDAO().dernier_id());
        animateur.setProgramme(programme_ani);
        
        Agent agent = new Agent();
        agent.setId_agent(new AgentDAO().VerifierId(cbo_nomAgent_pro.getSelectedItem().toString(), cbo_postNom_pro.getSelectedItem().toString()));
        animateur.setAgent(agent);
        
        animateur = animateurDao.create(animateur);
        
        // Affecter l'horaire de l'agent
        
        }
        else{
        JOptionPane.showMessageDialog(null, "Rappel : Cette activité n'a pas d'animateur humain", "Brt Appli", JOptionPane.PLAIN_MESSAGE);
        }
        
        
        
        InitProgrammeTele();
        table_tele();
    }
    private void InitProgrammeTele(){
hd.setSelectedIndex(-1);
md.setSelectedIndex(-1);
hf.setSelectedIndex(-1);
mf.setSelectedIndex(-1);
cdar_date_pro.setDate(null);
cbo_redif.setSelectedIndex(-1);
cdar_dateredif.setDate(null);
cbo_nomAgent_pro.setSelectedIndex(-1);
cbo_postNom_pro.setSelectedIndex(-1);
txt_prenomAgent_pro.setText("");

}
    private void cbo_nomAgent_proPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_nomAgent_proPopupMenuWillBecomeInvisible
        try {
            cbo_postNom_pro.removeAllItems();
        cbo_postNom_pro.enable(true);
        cbo_agent_affiche(cbo_nomAgent_pro.getSelectedItem().toString(),txt_prenomAgent_pro, cbo_postNom_pro);
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_cbo_nomAgent_proPopupMenuWillBecomeInvisible

    private void cbo_postNom_proPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_postNom_proPopupMenuWillBecomeInvisible
        try {
             cbo_agent_affiche2(cbo_nomAgent_pro.getSelectedItem().toString(), txt_prenomAgent_pro, cbo_postNom_pro);
        } catch (Exception e) {
        }
       
    }//GEN-LAST:event_cbo_postNom_proPopupMenuWillBecomeInvisible

    private void nomActi_proPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_nomActi_proPopupMenuWillBecomeInvisible
        try {
            cbo_activite_affiche(nomActi_pro.getSelectedItem().toString(), typeAgent_pro, "Television");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_nomActi_proPopupMenuWillBecomeInvisible

    private void cbo_redifPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_redifPopupMenuWillBecomeInvisible
        if(cbo_redif.getSelectedIndex() == 0){
        cdar_dateredif.setEnabled(true);       
        }
        else{
    cdar_dateredif.setEnabled(false);
    }
    }//GEN-LAST:event_cbo_redifPopupMenuWillBecomeInvisible

    private void cdar_dateredifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cdar_dateredifMouseClicked
       
    }//GEN-LAST:event_cdar_dateredifMouseClicked

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed
     if(cdar_dateRecherche.getDate().toString().equals(""))
     {
       JOptionPane.showMessageDialog(null, "Veuillez indiquer la date", "Brt Appli", JOptionPane.ERROR_MESSAGE);
     }
     else{
        try {
//            System.out.println(cdar_dateRecherche.getDate().toString());
        tble_tele.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().Recherche(((JTextField)cdar_dateRecherche.getDateEditor().getUiComponent()).getText(),"Television")));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
     }
    }//GEN-LAST:event_jButton72ActionPerformed

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed
        table_tele();
        cdar_dateRecherche.setDate(null);
    }//GEN-LAST:event_jButton73ActionPerformed

    private void jButton74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton74ActionPerformed
        table_tele();
        Repaindre(pan_mouv_tele, pan_tele_gestion);
    }//GEN-LAST:event_jButton74ActionPerformed

    private void jButton75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton75ActionPerformed

        if(tble_tele_gestion.getSelectedRow() == -1){
       JOptionPane.showMessageDialog(null, "Veuillez choisir le programme a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
       }
        else{
        int recupl = tble_tele_gestion.getSelectedRow();
        Programme programme = new Programme();
        programme.setId_prog(Integer.valueOf(tble_tele_gestion.getValueAt(recupl, 0).toString()).intValue());
        programme.setDate_prog(tble_tele_gestion.getValueAt(recupl, 1).toString());
        programme.setRediffusion(tble_tele_gestion.getValueAt(recupl, 2).toString());
        programme.setDate_redif(tble_tele_gestion.getValueAt(recupl, 3).toString());
        programme.setHeure_debut(tble_tele_gestion.getValueAt(recupl, 4).toString());
        programme.setHeure_fin(tble_tele_gestion.getValueAt(recupl, 5).toString());
        programme.setDepartement("Television");
        
        Activite activite = new Activite();
        activite.setId_act(Integer.valueOf(tble_tele_gestion.getValueAt(recupl, 6).toString()).intValue());
        programme.setActivite(activite);
        programme = programmeDao.update(programme);
        table_tele();
        }
    }//GEN-LAST:event_jButton75ActionPerformed

    private void jButton76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton76ActionPerformed
    if(tble_tele_gestion.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir le programme a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_tele_gestion.getSelectedRow();
    int recupc = tble_tele_gestion.getSelectedColumn();
    Programme programme = new Programme();
    programme.setId_prog(Integer.valueOf(tble_tele_gestion.getValueAt(recupl, 0).toString()).intValue());
    programmeDao.delete(programme);
    table_tele();
    }
    }//GEN-LAST:event_jButton76ActionPerformed

    private void jButton77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton77ActionPerformed
        Repaindre(pan_mouv_tele, pan_tele_accueil);
    }//GEN-LAST:event_jButton77ActionPerformed
public void InitActivitéRadio(){
//    txt_activite_nomr.setText("");
//    cbo_activite_typer.setSelectedIndex(-1);
//    txt_activite_descriptionr.setText("");
}private void CreateProgrammeRadio(){
//        String heure_debut =  hd1.getSelectedItem().toString() +":"+md1.getSelectedItem().toString();
//        String heure_fin = hf1.getSelectedItem().toString()+":"+mf1.getSelectedItem().toString();
//        Programme programme = new Programme();
//        programme.setDate_prog(((JTextField)cdar_date_pro1.getDateEditor().getUiComponent()).getText());
//        programme.setHeure_debut(heure_debut);
//        programme.setHeure_fin(heure_fin);
//        programme.setRediffusion(cbo_redif1.getSelectedItem().toString());
//        programme.setDate_redif(((JTextField)cdar_dateredif1.getDateEditor().getUiComponent()).getText());
//        programme.setDepartement("Radio");
//        
//        Activite activite = new Activite();
//        activite.setId_act(new ActiviteDAO().VerifierId(nomActi_pro1.getSelectedItem().toString()));
//        programme.setActivite(activite);
//        programme = programmeDao.create(programme);
//     
//        if(cbo_nomAgent_pro1.getSelectedIndex() != -1 || cbo_postNom_pro1.getSelectedIndex() != -1){
//        // Pour la table Animateur
//        // Programme
//        Animateur animateur = new Animateur();
//        Programme programme_ani = new Programme();
//        programme_ani.setId_prog(new ProgrammeDAO().dernier_id());
//        animateur.setProgramme(programme_ani);
//        
//        Agent agent = new Agent();
//        agent.setId_agent(new AgentDAO().VerifierId(cbo_nomAgent_pro1.getSelectedItem().toString(), cbo_postNom_pro1.getSelectedItem().toString()));
//        animateur.setAgent(agent);
//        
//        animateur = animateurDao.create(animateur);
//        }
//        else{
//        JOptionPane.showMessageDialog(null, "Rappel : Cette activité n'a pas d'animateur humain", "Brt Appli", JOptionPane.PLAIN_MESSAGE);
//        }
//        
//        InitProgrammeRadio();
        table_tele();
    }
    private void InitProgrammeRadio(){
//hd1.setSelectedIndex(-1);
//md1.setSelectedIndex(-1);
//hf1.setSelectedIndex(-1);
//mf1.setSelectedIndex(-1);
//cdar_date_pro1.setDate(null);
//cbo_redif1.setSelectedIndex(-1);
//cdar_dateredif1.setDate(null);
//cbo_nomAgent_pro1.setSelectedIndex(-1);
//cbo_postNom_pro1.setSelectedIndex(-1);
//txt_prenomAgent_pro1.setText("");

}
    private void cbo_agent_presencePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_agent_presencePopupMenuWillBecomeInvisible
        try {
            cbo_post_presence.removeAllItems();
        cbo_post_presence.setEnabled(true);
        cbo_agent_affiche(cbo_agent_presence.getSelectedItem().toString(),txt_prenomAg_pres, cbo_post_presence);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbo_agent_presencePopupMenuWillBecomeInvisible

    private void cbo_post_presencePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_post_presencePopupMenuWillBecomeInvisible
        try {
            cbo_agent_affiche2(cbo_agent_presence.getSelectedItem().toString(), txt_prenomAg_pres, cbo_post_presence);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbo_post_presencePopupMenuWillBecomeInvisible

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        if(cbo_agent_presence.getSelectedIndex() == -1 || cbo_post_presence.getSelectedIndex() == -1){
        JOptionPane.showMessageDialog(null, "Champ vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
        else{
        Presence presence = new Presence();
        Agent agent = new Agent();
        agent.setId_agent(new AgentDAO().VerifierId(cbo_agent_presence.getSelectedItem().toString(), cbo_post_presence.getSelectedItem().toString()));
        presence.setAgent(agent);
        presence.setDate_presence(date_presence.getText());
        presence.setJour(null);
        presence.setHeure_arrive(heure_presence.getText());
        presence.setHeure_depart(null);
        presence.setDisponibilite("Oui"); 
        presence = presenceDao.create(presence);
        
        InitPresenceEntree();
        table_presence();
        }
    }//GEN-LAST:event_jButton32ActionPerformed
    private void InitPresenceEntree(){
    cbo_agent_presence.setSelectedIndex(-1);
    cbo_post_presence.setSelectedIndex(-1);
    txt_prenomAg_pres.setText("");
    }
    private void rb_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_dateActionPerformed
        try {
            if(rb_date.isSelected() == true){
            cdar_date_horaire.setEnabled(true);
            txt_nom_horaire.setText("");
            txt_nom_horaire.setEnabled(false);
            }
            else{
            cdar_date_horaire.setDate(null);
            cdar_date_horaire.setEnabled(false);
            
            
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_rb_dateActionPerformed

    private void rb_nomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_nomActionPerformed
        try {
        if(rb_nom.isSelected() == true) {
        txt_nom_horaire.setEnabled(true);
        cdar_date_horaire.setDate(null);
        cdar_date_horaire.setEnabled(false);
        }
        else{
            txt_nom_horaire.setText("");
            txt_nom_horaire.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_rb_nomActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
//        rb_date.setSelected(false);
//        rb_nom.setSelected(false);
        table_tele();
        cdar_date_horaire.setDate(null);
        txt_nom_horaire.setText("");
        
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
       if (rb_date.isSelected() == false && rb_nom.isSelected() == false){
        JOptionPane.showMessageDialog(null, "Veuillez indiquer votre choix", "Brt Appli", JOptionPane.ERROR_MESSAGE);
       }
       else if(rb_date.isSelected() == true){
           if(cdar_date_horaire.getDate().toString().equals("")){
           JOptionPane.showMessageDialog(null, "Veuillez indiquer la date recherché", "Brt Appli", JOptionPane.ERROR_MESSAGE);
           }
           else{
               try {
                  tble_tele_horaire.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().PourTable_HoraireRechercheDate("Television", ((JTextField)cdar_date_horaire.getDateEditor().getUiComponent()).getText()))); 
               } catch (Exception e) {
                   JOptionPane.showMessageDialog(null, e, "Brt Aplli", JOptionPane.ERROR_MESSAGE);
               }
           
           }
       }
       else if (rb_nom.isSelected() == true){
           if(txt_nom_horaire.getText().equals("")){
           JOptionPane.showMessageDialog(null, "Veuillez indiquer le nom de l'agent recherché", "Brt Appli", JOptionPane.ERROR_MESSAGE);
           }
           else{
               try {
                   tble_tele_horaire.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().PourTable_HoraireRechercheNom("Television", txt_nom_horaire.getText()))); 
               } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Brt Aplli", JOptionPane.ERROR_MESSAGE);
               }
           
           }
       }
           
//  tble_tele.setModel(DbUtils.resultSetToTableModel(new ProgrammeDAO().Recherche(((JTextField)cdar_dateRecherche.getDateEditor().getUiComponent()).getText(),"Television")));
    }//GEN-LAST:event_jButton15ActionPerformed

    private void cbo_agent_presence1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_agent_presence1PopupMenuWillBecomeInvisible
        try {
            cbo_post_presence1.removeAllItems();
        cbo_post_presence1.setEnabled(true);
        cbo_agent_affiche(cbo_agent_presence1.getSelectedItem().toString(),txt_prenomAg_pres1, cbo_post_presence1);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbo_agent_presence1PopupMenuWillBecomeInvisible

    private void cbo_post_presence1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_post_presence1PopupMenuWillBecomeInvisible
        try {
            cbo_agent_affiche2(cbo_agent_presence1.getSelectedItem().toString(), txt_prenomAg_pres1, cbo_post_presence1);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbo_post_presence1PopupMenuWillBecomeInvisible

    private void jTabbedPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane4MouseClicked
        try {
            InitPresenceEntree();
            cbo_agent_presence.removeAllItems();
            cbo_agent_presence1.removeAllItems();
            cbo_post_presence1.removeAllItems();
            cbo_presence();
            cbo_presence2();
            
            InitPresenceSortie();
        } catch (Exception e) {
        }
        
    }//GEN-LAST:event_jTabbedPane4MouseClicked

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        if(cbo_agent_presence1.getSelectedIndex() == -1 || cbo_post_presence1.getSelectedIndex() == -1){
        JOptionPane.showMessageDialog(null, "Champ vide", "Brt Appli", JOptionPane.ERROR_MESSAGE);
        }
        else{
        Presence presence = new Presence();
        presence.setId_presence(new PresenceDAO().VerifierId(cbo_agent_presence1.getSelectedItem().toString(), cbo_post_presence1.getSelectedItem().toString(), date_presence.getText()));
        presence.setHeure_depart(heure_presence.getText());
        presence.setDisponibilite("Non");
        presence = new PresenceDAO().updateSortie(presence);
        
        cbo_agent_presence1.removeAllItems();
        cbo_post_presence1.removeAllItems();
        cbo_presence();
        InitPresenceSortie();
        table_presence();
        }
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed

       if(tble_presence.getSelectedRow() == -1){
       JOptionPane.showMessageDialog(null, "Veuillez choisir le presence a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
       }
        else{
        int recupl = tble_presence.getSelectedRow();
        Presence presence = new Presence();
        presence.setId_presence(Integer.valueOf(tble_presence.getValueAt(recupl, 0).toString()).intValue());
        presence.setHeure_arrive(tble_presence.getValueAt(recupl, 4).toString());
        presence.setHeure_depart(tble_presence.getValueAt(recupl, 5).toString());
        presence = new PresenceDAO().updateGestion(presence);
//        
        table_presence();
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        if(tble_presence.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir le presence a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_presence.getSelectedRow();
    int recupc = tble_presence.getSelectedColumn();
    Presence presence = new Presence();
    presence.setId_presence(Integer.valueOf(tble_presence.getValueAt(recupl, 0).toString()).intValue());
    presenceDao.delete(presence);
    table_presence();
    }
    }//GEN-LAST:event_jButton36ActionPerformed

    private void txt_emplacementKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emplacementKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER){
             jButton50ActionPerformed(null);
         }
    }//GEN-LAST:event_txt_emplacementKeyPressed

    private void cbo_Postagent_evPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbo_Postagent_evPopupMenuWillBecomeInvisible
       try {
             cbo_agent_affiche2(cbo_agent_ev.getSelectedItem().toString(), txt_preagent_tech, cbo_Postagent_ev);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbo_Postagent_evPopupMenuWillBecomeInvisible

    private void cmbo_Postagent_SortiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbo_Postagent_SortiPopupMenuWillBecomeInvisible
        try {
             cbo_agent_affiche2(cmbo_agent_Sorti.getSelectedItem().toString(), txt_prenomSorti, cmbo_Postagent_Sorti);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmbo_Postagent_SortiPopupMenuWillBecomeInvisible

    private void jButton98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton98ActionPerformed
        t = new Thread(new sevine());//Thread 
        t.start();//Thread 
        Repaindre(pan_mouv, pan_personnel);
    }//GEN-LAST:event_jButton98ActionPerformed

    private void txt_numtel_parrainKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numtel_parrainKeyReleased
        if (!isNumeric(evt.getKeyChar())){
    txt_numtel_parrain.setText(txt_numtel_parrain.getText().replace(String.valueOf(evt.getKeyChar()), ""));
        }
    }//GEN-LAST:event_txt_numtel_parrainKeyReleased

    private void txt_fonctionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fonctionKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER){
           jButton57ActionPerformed(null);
       }
    }//GEN-LAST:event_txt_fonctionKeyPressed

    private void jButton99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton99ActionPerformed
        if(tble_enfant.getSelectedRow() == -1){
       JOptionPane.showMessageDialog(null, "Veuillez choisir l'enfant a modifier", "Brt Appli", JOptionPane.ERROR_MESSAGE);
       }
        else{
        int recupl = tble_enfant.getSelectedRow();
        Enfant enfant= new Enfant();
        enfant.setId_enfant(Integer.valueOf(tble_enfant.getValueAt(recupl, 0).toString()).intValue());
        enfant.setNom_enfant(tble_enfant.getValueAt(recupl, 1).toString());
        enfant.setPost_nom_enfant(tble_enfant.getValueAt(recupl, 2).toString());
        enfant.setPrenom(tble_enfant.getValueAt(recupl, 3).toString());
        
        Agent agent = new Agent();
        agent.setId_agent(Integer.valueOf(tble_enfant.getValueAt(recupl, 4).toString()).intValue());
        enfant.setAgent(agent);
        
        enfant = new EnfantDAO().update(enfant);
   
        table_enfant();
        }
    }//GEN-LAST:event_jButton99ActionPerformed

    private void jButton100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton100ActionPerformed
        if(tble_enfant.getSelectedRow() == -1){
    JOptionPane.showMessageDialog(null, "Veuillez choisir l'enfant a supprimer", "Brt Appli", JOptionPane.ERROR_MESSAGE);
    }
    else{
    int recupl = tble_enfant.getSelectedRow();
    int recupc = tble_enfant.getSelectedColumn();
    Enfant enfant = new Enfant();
    enfant.setId_enfant(Integer.valueOf(tble_enfant.getValueAt(recupl, 0).toString()).intValue());
    enfantDao.delete(enfant);
    
    table_enfant();
    }
    }//GEN-LAST:event_jButton100ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        InitEnfant();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void cdar_date_horaireKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cdar_date_horaireKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            jButton15ActionPerformed(null);
        }
    }//GEN-LAST:event_cdar_date_horaireKeyPressed

    private void txt_nom_horaireKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nom_horaireKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            jButton15ActionPerformed(null);
        }
    }//GEN-LAST:event_txt_nom_horaireKeyPressed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
       InitProgrammeTele();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void cdar_dateRechercheKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cdar_dateRechercheKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            jButton72ActionPerformed(null);
        }
    }//GEN-LAST:event_cdar_dateRechercheKeyPressed

    private void cmbo_disponibiliteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbo_disponibiliteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbo_disponibiliteActionPerformed

    private void nom_user1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nom_user1MouseClicked
int r = JOptionPane.showConfirmDialog(null, "Voulez-vous vous deconnecter?", "Brt Appli", JOptionPane.YES_OPTION);
if(r == 0){
        Repaindre(pan_base, pan_aut);
        mot_de_passe.setText("");
        yes.setVisible(false);
        
}// TODO add your handling code here:
    }//GEN-LAST:event_nom_user1MouseClicked

    private void txt_emplacementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emplacementActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emplacementActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton101ActionPerformed
jTabbedPane3.setSelectedIndex(-1);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton101ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton111ActionPerformed
        try {
            // Pour afficher Un etat predefini
//            String report = "C:\\Users\\Gauss\\Documents\\NetBeansProjects\\Tfc_calendar\\Materiel_Stock.jrxml";
            String report = "Materiel_Stock.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(report);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);      
        }
    }//GEN-LAST:event_jButton111ActionPerformed

    private void tble_stockMatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tble_stockMatMouseClicked
        
    }//GEN-LAST:event_tble_stockMatMouseClicked

    private void Rech_Stock1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rech_Stock1ActionPerformed
try {
        tble_personnel.setModel(DbUtils.resultSetToTableModel(new AgentDAO().Recherche(txt_stockRecherche1.getText())));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_Rech_Stock1ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
table_personnel();
        txt_stockRecherche1.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton30ActionPerformed

    private void txt_stockRecherche1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_stockRecherche1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stockRecherche1KeyPressed

    private void nomag_admPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_nomag_admPopupMenuWillBecomeInvisible
        nomag_adm.removeAllItems();
        
        cbo_agent();
        cbo_agent_affiche(nomag_adm.getSelectedItem().toString(), preag_adm, pag_adm);
    }//GEN-LAST:event_nomag_admPopupMenuWillBecomeInvisible
    
    private void InitPresenceSortie(){
    cbo_agent_presence1.setSelectedIndex(-1);
    cbo_post_presence1.setSelectedIndex(-1);
    txt_prenomAg_pres1.setText("");
    }
    private void InitActivité(){
    txt_activite_nom.setText("");
    cbo_activite_type.setSelectedIndex(-1);
    txt_activite_description.setText("");
    }
    
    public void table(){
       JComboBox combo = new JComboBox(new String[] {"Gauss", "Kiaku", "Slogger"});
       DefaultTableModel jTable = (DefaultTableModel) tble_gestionMat.getModel();
       jTable.addColumn("Combo");
       Object[] objets = new Object[5];
       objets[4] = combo;
//       jTable.addRow(objets);
       this.tble_gestionMat.getColumn("Combo").setCellRenderer(new ComboRenderer());
       this.tble_gestionMat.getColumn("Combo").setCellEditor(new DefaultCellEditor(combo));
//       this.tble_gestionMat.setDefaultRenderer(JComboBox.class, new TableComponant());
    }
    public void recuper(){
    FileWriter id = null;
    BufferedWriter tampon = null;
     String [] don = new String [2];
     don[0] = tble_personnel.getValueAt(tble_personnel.getSelectedRow(), 0).toString() + "\t";
     don[1] = "\n";
     try {
        id = new FileWriter("D:\\Brt Africa\\matricule.txt", true);
  tampon = new BufferedWriter(id);
  for (int i = 0; i < don.length; i++) {
tampon.write(don [i]);
System.out.println("Ecriture de : " +don[i]);
}
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }finally {
try {
tampon.flush();
tampon.close();
id.close();
} catch (IOException e1) {
JOptionPane.showMessageDialog(null, e1);
}
}
}
    private boolean isNumeric (char car)
{
    try {
        Integer.parseInt(String.valueOf(car));
    } catch (Exception e) {
        return false;
    }
    return true;
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fenetre().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JButton Rech_Stock;
    private javax.swing.JButton Rech_Stock1;
    private javax.swing.JLabel Sexe;
    private javax.swing.ButtonGroup bg;
    private javax.swing.ButtonGroup bg_Radio;
    private javax.swing.JComboBox cb_nomparrain_ag;
    private javax.swing.JComboBox cbo_Postagent_ev;
    private javax.swing.JComboBox cbo_activite_type;
    private javax.swing.JComboBox cbo_agent_Enfant;
    private javax.swing.JComboBox cbo_agent_ev;
    private javax.swing.JComboBox cbo_agent_presence;
    private javax.swing.JComboBox cbo_agent_presence1;
    private javax.swing.JComboBox cbo_degre_urgent;
    private javax.swing.JComboBox cbo_etat_civil;
    private javax.swing.JComboBox cbo_fonction_ag;
    private javax.swing.JComboBox cbo_nomAgent_pro;
    private javax.swing.JComboBox cbo_postNom_pro;
    private javax.swing.JComboBox cbo_post_presence;
    private javax.swing.JComboBox cbo_post_presence1;
    private javax.swing.JComboBox cbo_redif;
    private javax.swing.JComboBox cbo_sexe;
    private com.toedter.calendar.JDateChooser cdarDate_ev;
    private com.toedter.calendar.JDateChooser cdar_dateRecherche;
    private com.toedter.calendar.JDateChooser cdar_date_horaire;
    private com.toedter.calendar.JDateChooser cdar_date_pro;
    private com.toedter.calendar.JDateChooser cdar_datedebut_ag;
    private com.toedter.calendar.JDateChooser cdar_datenaissance_ag;
    private com.toedter.calendar.JDateChooser cdar_dateredif;
    private javax.swing.JComboBox cmboEtatMateriel_ev;
    private javax.swing.JComboBox cmboJour;
    private javax.swing.JComboBox cmbo_Postagent_Sorti;
    private javax.swing.JComboBox cmbo_agent_Sorti;
    private javax.swing.JComboBox cmbo_disponibilite;
    private javax.swing.JComboBox cmbo_empl_ev;
    private javax.swing.JComboBox cmbo_empl_sor;
    private javax.swing.JComboBox cmbo_emplacement;
    private javax.swing.JComboBox cmbo_mat_ev;
    private javax.swing.JComboBox cmbo_mat_evSor;
    private javax.swing.JComboBox cmbo_quantite_sorti;
    private javax.swing.JComboBox code;
    private javax.swing.JLabel date_presence;
    private com.toedter.calendar.JDateChooser date_sortie;
    private javax.swing.JComboBox etat_mat_sortie;
    private javax.swing.JComboBox hd;
    private javax.swing.JLabel heureSortie;
    private javax.swing.JLabel heure_ev;
    private javax.swing.JLabel heure_presence;
    private javax.swing.JComboBox hf;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton100;
    private javax.swing.JButton jButton101;
    private javax.swing.JButton jButton102;
    private javax.swing.JButton jButton103;
    private javax.swing.JButton jButton104;
    private javax.swing.JButton jButton105;
    private javax.swing.JButton jButton106;
    private javax.swing.JButton jButton107;
    private javax.swing.JButton jButton108;
    private javax.swing.JButton jButton109;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton110;
    private javax.swing.JButton jButton111;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton74;
    private javax.swing.JButton jButton75;
    private javax.swing.JButton jButton76;
    private javax.swing.JButton jButton77;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton98;
    private javax.swing.JButton jButton99;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox20;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel238;
    private javax.swing.JLabel jLabel239;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel243;
    private javax.swing.JLabel jLabel244;
    private javax.swing.JLabel jLabel245;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel247;
    private javax.swing.JLabel jLabel248;
    private javax.swing.JLabel jLabel249;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel250;
    private javax.swing.JLabel jLabel251;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JLabel jLabel253;
    private javax.swing.JLabel jLabel254;
    private javax.swing.JLabel jLabel255;
    private javax.swing.JLabel jLabel256;
    private javax.swing.JLabel jLabel257;
    private javax.swing.JLabel jLabel258;
    private javax.swing.JLabel jLabel259;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel260;
    private javax.swing.JLabel jLabel261;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JComboBox jour_sorti;
    private javax.swing.JLabel load;
    private javax.swing.JLabel m;
    private javax.swing.JComboBox md;
    private javax.swing.JComboBox mf;
    private javax.swing.JPasswordField mot_de_passe;
    private javax.swing.JComboBox nomActi_pro;
    private javax.swing.JLabel nom_user;
    private javax.swing.JLabel nom_user1;
    private javax.swing.JComboBox nomag_adm;
    private javax.swing.JButton ok;
    private javax.swing.JComboBox pag_adm;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel pan_admi;
    private javax.swing.JPanel pan_admi_bouton;
    private javax.swing.JPanel pan_aut;
    private javax.swing.JPanel pan_base;
    private javax.swing.JPanel pan_dir;
    private javax.swing.JPanel pan_dmin;
    private javax.swing.JPanel pan_mouv;
    private javax.swing.JPanel pan_mouv_admi;
    private javax.swing.JPanel pan_mouv_tech;
    private javax.swing.JPanel pan_mouv_tele;
    private javax.swing.JPanel pan_personnel;
    private javax.swing.JPanel pan_presence;
    private javax.swing.JPanel pan_tech;
    private javax.swing.JPanel pan_tech_accueil;
    private javax.swing.JPanel pan_tech_bouton;
    private javax.swing.JPanel pan_tech_emplacement;
    private javax.swing.JPanel pan_tech_enr;
    private javax.swing.JPanel pan_tech_es;
    private javax.swing.JPanel pan_tech_gestion;
    private javax.swing.JPanel pan_tech_stock;
    private javax.swing.JPanel pan_tele;
    private javax.swing.JPanel pan_tele_accueil;
    private javax.swing.JPanel pan_tele_accueil1;
    private javax.swing.JPanel pan_tele_act;
    private javax.swing.JPanel pan_tele_affi;
    private javax.swing.JPanel pan_tele_bouton;
    private javax.swing.JPanel pan_tele_gestion;
    private javax.swing.JPanel pan_tele_horaire;
    private javax.swing.JPanel pan_tele_prog;
    private javax.swing.JPanel pan_titre;
    private javax.swing.JLabel plus;
    private javax.swing.JTextField preag_adm;
    private javax.swing.JComboBox quantite_ev;
    private javax.swing.JRadioButton rb_date;
    private javax.swing.JRadioButton rb_nom;
    private javax.swing.JToggleButton tbCamera;
    private javax.swing.JTable tble_activite;
    private javax.swing.JTable tble_emplacement;
    private javax.swing.JTable tble_enfant;
    private javax.swing.JTable tble_fonction;
    private javax.swing.JTable tble_gestionMat;
    private javax.swing.JTable tble_parrain;
    private javax.swing.JTable tble_personnel;
    private javax.swing.JTable tble_presence;
    private javax.swing.JTable tble_stockMat;
    private javax.swing.JTable tble_tele;
    private javax.swing.JTable tble_tele_gestion;
    private javax.swing.JTable tble_tele_horaire;
    private javax.swing.JTable tble_user;
    private javax.swing.JLabel titre;
    private javax.swing.JLabel titre1;
    private javax.swing.JTextArea txt_activite_description;
    private javax.swing.JTextField txt_activite_nom;
    private javax.swing.JTextField txt_avenue_ag;
    private javax.swing.JTextField txt_avenue_urgent;
    private javax.swing.JTextArea txt_caracteristique;
    private javax.swing.JComboBox txt_commune_ag;
    private javax.swing.JComboBox txt_commune_urgent;
    private javax.swing.JTextField txt_district_ag;
    private javax.swing.JTextField txt_email_ag;
    private javax.swing.JTextField txt_emplacement;
    private javax.swing.JTextField txt_fonction;
    private javax.swing.JTextField txt_lieunaiss_ag;
    private javax.swing.JTextField txt_marié_ag;
    private javax.swing.JTextField txt_matricule_ag;
    private javax.swing.JTextField txt_nationalite_ag;
    private javax.swing.JTextField txt_nmbreenfant_ag;
    private javax.swing.JTextField txt_nomEnfant;
    private javax.swing.JTextField txt_nom_ag;
    private javax.swing.JTextField txt_nom_horaire;
    private javax.swing.JTextField txt_nom_parrain;
    private javax.swing.JTextField txt_nommere_ag;
    private javax.swing.JTextField txt_nompere_ag;
    private javax.swing.JTextField txt_num_urgent;
    private javax.swing.JTextField txt_numadr_ag;
    private javax.swing.JTextField txt_numtel_parrain;
    private javax.swing.JTextField txt_numtelparrain_ag;
    private javax.swing.JTextField txt_postNomEnfant;
    private javax.swing.JComboBox txt_postNom_enfant;
    private javax.swing.JTextField txt_postnom_ag;
    private javax.swing.JTextField txt_preagent_tech;
    private javax.swing.JTextField txt_prenomAg_pres;
    private javax.swing.JTextField txt_prenomAg_pres1;
    private javax.swing.JTextField txt_prenomAgent_pro;
    private javax.swing.JTextField txt_prenomEnfant;
    private javax.swing.JTextField txt_prenomSorti;
    private javax.swing.JTextField txt_prenom_ag;
    private javax.swing.JTextField txt_prenom_parrain;
    private javax.swing.JTextField txt_prenomag_enfant;
    private javax.swing.JTextField txt_prenomparrain_ag;
    private javax.swing.JTextField txt_province_ag;
    private javax.swing.JTextField txt_quartier_ag;
    private javax.swing.JTextField txt_quartier_urgent;
    private javax.swing.JTextField txt_secteur_ag;
    private javax.swing.JTextField txt_stockRecherche;
    private javax.swing.JTextField txt_stockRecherche1;
    private javax.swing.JTextField txt_tel_ag;
    private javax.swing.JTextField txt_tel_urgent;
    private javax.swing.JTextField txt_teritoire_ag;
    private javax.swing.JTextField txt_type_materiel;
    private javax.swing.JTextField typeAgent_pro;
    private javax.swing.JLabel yes;
    // End of variables declaration//GEN-END:variables
}
