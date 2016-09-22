/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import co.com.soinsoftware.accountability.controller.MenuController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.User;

/**
 *
 * @author Carlos Rodriguez
 * @since 21/09/2016
 * @version 1.0
 */
public class JFMain extends JFrame {

	private static final long serialVersionUID = 1911600408651182901L;

	private final Company company;

	private final JFCompany companyFrame;

	private final User loggedUser;

	public JFMain(final JFCompany companyFrame, final Company company,
			final User user) {
		this.company = company;
		this.companyFrame = companyFrame;
		this.loggedUser = user;
		this.initComponents();
		this.createMenuBar();
		this.refresh();
	}

	public final void refresh() {
		this.jlbTitle.setText(this.company.getName());
	}

	private void createMenuBar() {
		final MenuController controller = new MenuController(this.company,
				this.loggedUser);
		final JMenuBar menuBar = new JMBAppMenu(controller);
		this.setJMenuBar(menuBar);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		lbImage = new javax.swing.JLabel();
		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		jbtChangeCompany = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("CPMIPYMES");
		setExtendedState(6);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/accountability.png")));

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Nombre de la empresa");

		jbtChangeCompany.setBackground(new java.awt.Color(16, 135, 221));
		jbtChangeCompany.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtChangeCompany.setForeground(new java.awt.Color(255, 255, 255));
		jbtChangeCompany.setText("Seleccionar empresa");
		jbtChangeCompany.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtChangeCompany.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtChangeCompanyActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jpTitleLayout = new javax.swing.GroupLayout(
				jpTitle);
		jpTitle.setLayout(jpTitleLayout);
		jpTitleLayout
				.setHorizontalGroup(jpTitleLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpTitleLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbTitle)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												jbtChangeCompany,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												160,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		jpTitleLayout
				.setVerticalGroup(jpTitleLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpTitleLayout
										.createSequentialGroup()
										.addGap(32, 32, 32)
										.addGroup(
												jpTitleLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jlbTitle)
														.addComponent(
																jbtChangeCompany,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(34, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpTitle, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(0, 899, Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										388,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jpTitle,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										287, Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										35,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jbtChangeCompanyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtChangeCompanyActionPerformed
		this.companyFrame.refresh();
		this.companyFrame.setVisible(true);
		this.setVisible(false);
	}// GEN-LAST:event_jbtChangeCompanyActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtChangeCompany;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
