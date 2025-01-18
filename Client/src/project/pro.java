package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;

public class pro extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel jPanel;
	JFrame jFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("127.0.0.1",8983);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pro frame = new pro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public pro() {
		
		jFrame = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 990);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("员工管理");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("添加员工");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jPanel != null)
					jFrame.remove(jPanel);
				jPanel = new Panel();
				jFrame.getContentPane().add(jPanel);
				jFrame.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("修改员工");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jPanel != null)
					jFrame.remove(jPanel);
				jPanel = new ModifyEm();
				jFrame.getContentPane().add(jPanel);
				jFrame.setVisible(true);
			}
			
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("删除员工");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jPanel != null)
					jFrame.remove(jPanel);
				jPanel = new Delete();
				jFrame.getContentPane().add(jPanel);
				jFrame.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_1 = new JMenu("查询员工");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("单条件");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jPanel != null)
					jFrame.remove(jPanel);
				jPanel = new Search();
				jFrame.getContentPane().add(jPanel);
				jFrame.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);
	}

}
