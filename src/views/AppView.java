package views;

import views.LoginDesktopView;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class AppView extends JFrame {

	/**
	 * Create the frame.
	 */
	public AppView()  {
		ImageIcon appIcon = new ImageIcon("res/pronoteIcon.png");
		Image appIconImage = appIcon.getImage();

		ImageIcon appImage = new ImageIcon("res/appView.png");

		setTitle("Prototype Client Pronote SIO");
		setIconImage(appIconImage); 
		setResizable(false);
		setBounds(100, 100, 615, 435);
		
		JLabel lblImage = new JLabel();
		lblImage.setIcon(appImage);
		getContentPane().add(lblImage, BorderLayout.CENTER);
		
		JLabel lblConnectedUser = new JLabel("");
		lblConnectedUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectedUser.setFont(new Font("Tahoma", Font.BOLD, 18));
		getContentPane().add(lblConnectedUser, BorderLayout.NORTH);

		// ##################  Logique �v�nementielle ###################
		
		/**
		 * Lorsque la AppView est rendue visible, on affiche dans la 
		 * foul�e la LoginDesktopView pour permettre l'authentification
		 */
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				LoginDesktopView loginDesktop = new LoginDesktopView();
				String token = loginDesktop.showDialog();
				if (token == null) {
					// Abandon de l'application sans �tre authentifi�
					System.exit(0);
				}
				else {
					// TODO �crire ici le code qui doit �tre ex�cut� lorsque l'authentification utilisateur est un succ�s
					lblConnectedUser.setText("Utilisateur connecté : " + token);
				}
			}
		});

	}
	
	public void showDialog() {
		setVisible(true);
	}

}