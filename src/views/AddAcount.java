package views;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.mindrot.jbcrypt.BCrypt;

import model.DataAccess;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class AddAcount extends JDialog {
	private String token;

	public AddAcount() {
		setUndecorated(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setBounds(200, 200, 450, 300);
		getContentPane().setLayout(null);
		getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		JPanel pnlMode = new JPanel();
		pnlMode.setBounds(0, 0, 147, 300);
		getContentPane().add(pnlMode);
		
		JPanel pnlLogin = new JPanel();
		pnlLogin.setBackground(Color.WHITE);
		pnlLogin.setBounds(144, 0, 306, 300);
		pnlLogin.setLayout(null);
		getContentPane().add(pnlLogin);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(0, 0, 306, 113);
		ImageIcon logo = new ImageIcon("res/chaptalLogo1.png");
		lblLogo.setIcon(logo);
		pnlLogin.add(lblLogo);
		
		JPanel pnlIdentification = new JPanel();
		pnlIdentification.setBounds(25, 142, 260, 102);
		pnlIdentification.setLayout(null);
		pnlLogin.add(pnlIdentification);
		
		JTextField tfId = new JTextField();
		tfId.setText("Votre identifiant");
		tfId.setToolTipText("");
		tfId.setBounds(17, 28, 230, 20);
		pnlIdentification.add(tfId);
		tfId.setColumns(10);
		
		JTextField tfMdp = new JTextField();
		tfMdp.setText("Votre mot de passe");
		tfMdp.setToolTipText("");
		tfMdp.setBounds(17, 59, 230, 20);
		pnlIdentification.add(tfMdp);	
		
		JButton btnValider = new JButton("Valider");
		btnValider.setFont(new Font("Arial", Font.PLAIN, 11));
		btnValider.setBounds(161, 266, 124, 23);
		pnlLogin.add(btnValider);
		
		JLabel lblAddFail = new JLabel("");
		lblAddFail.setFont(new Font("Arial", Font.PLAIN, 11));
		lblAddFail.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddFail.setForeground(Color.RED);
		lblAddFail.setBounds(10, 113, 286, 23);
		pnlLogin.add(lblAddFail);
		
		JLabel lblAddSuccess = new JLabel("");
		lblAddSuccess.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddSuccess.setForeground(Color.GREEN);
		lblAddSuccess.setBounds(10, 113, 286, 23);
		pnlLogin.add(lblAddSuccess);
		
		JButton btnCancel = new JButton("Retour");
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 11));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginDesktopView loginView = new LoginDesktopView();
				loginView.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(25, 266, 126, 23);
		pnlLogin.add(btnCancel);
		
		// ##################  Logique �v�nementielle ###################
		
		/*
		 * Lorsque le focus est re�u sur le champ de saisie de l'identifiant, 
		 * on s�lectionne tout le texte qu'il contient
		 */
		tfId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				tfId.selectAll();
			}
		});

		/*
		 * Lorsque le focus est re�u sur le champ de saisie du mot de passe, 
		 * on s�lectionne tout le texte qu'il contient
		 */
		tfMdp.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfMdp.selectAll();
			}
		});

		/*
		 * Timer param�tre � 3 secondes pour le d�lai d'effacement d'un 
		 * message d'erreur
		 */
		Timer timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblAddFail.setText("");
			}
		});

		/*
		 * Traitement de la demande d'authentification (clic sur le 
		 * bouton "Se connecter") 
		 */
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// obtention de l'utilisateur et du mot de passe saisis
				
				
				
				String userId= tfId.getText();
				String hashed = BCrypt.hashpw(tfMdp.getText(), BCrypt.gensalt());				

				DataAccess dao ;
				String requeteSQL ;

				dao = new DataAccess() ;
				requeteSQL 	= "INSERT INTO users (user, pwhash) VALUES ('" + userId + "', '" + hashed + "')";
				boolean rs = dao.insertSQL(requeteSQL);
				

				if(rs == false) { 
					lblAddFail.setText("une érreur est survenue");
					timer.restart();
				}
				else {
					lblAddSuccess.setText("Compte Ajouté");
					timer.restart();
					
				}
			}
		} );

	}
	
	
	/*
	 * Suppression de la fen�tre lorsque l'utilisateur enfonce la touche "Echap"
	 */
	ActionListener escListener = new ActionListener(){
		public void actionPerformed(ActionEvent ae)
		{
			dispose();
		}
	};

	/*
	 * Affichage de la fen�tre et renvoi du token obtenu via l'authentification
	 */
	public String showDialog() {
		setVisible(true);
		return token;
	}
	
	public byte[] toBytes(char[] data) {
		byte[] toRet = new byte[data.length];
		for(int i = 0; i < toRet.length; i++) {
		toRet[i] = (byte) data[i];
		}
		Arrays.fill(data, '0'); 
		return toRet;
	}
}
