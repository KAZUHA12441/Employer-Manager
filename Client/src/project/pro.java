package project;

////原版

//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.JMenuBar;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.io.IOException;
//import java.net.Socket;
//
//public class pro extends JFrame {
//
//	private static final long serialVersionUID = 1L;
//	JPanel jPanel;
//	JFrame jFrame;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) throws IOException {
//
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					pro frame = new pro();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public pro() {
//
//		jFrame = this;
//
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 578, 990);
//
//		JMenuBar menuBar = new JMenuBar();
//		setJMenuBar(menuBar);
//
//		JMenu mnNewMenu = new JMenu("员工管理");
//		menuBar.add(mnNewMenu);
//
//		JMenuItem mntmNewMenuItem = new JMenuItem("添加员工");
//		mntmNewMenuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(jPanel != null)
//					jFrame.remove(jPanel);
//				jPanel = new Panel();
//				jFrame.getContentPane().add(jPanel);
//				jFrame.setVisible(true);
//			}
//		});
//		mnNewMenu.add(mntmNewMenuItem);
//
//		JMenuItem mntmNewMenuItem_1 = new JMenuItem("修改员工");
//		mntmNewMenuItem_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(jPanel != null)
//					jFrame.remove(jPanel);
//				jPanel = new ModifyEm();
//				jFrame.getContentPane().add(jPanel);
//				jFrame.setVisible(true);
//			}
//
//		});
//		mnNewMenu.add(mntmNewMenuItem_1);
//
//		JMenuItem mntmNewMenuItem_2 = new JMenuItem("删除员工");
//		mntmNewMenuItem_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(jPanel != null)
//					jFrame.remove(jPanel);
//				jPanel = new Delete();
//				jFrame.getContentPane().add(jPanel);
//				jFrame.setVisible(true);
//			}
//		});
//		mnNewMenu.add(mntmNewMenuItem_2);
//
//		JMenu mnNewMenu_1 = new JMenu("查询员工");
//		menuBar.add(mnNewMenu_1);
//
//		JMenuItem mntmNewMenuItem_3 = new JMenuItem("单条件");
//		mntmNewMenuItem_3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(jPanel != null)
//					jFrame.remove(jPanel);
//				jPanel = new Search();
//				jFrame.getContentPane().add(jPanel);
//				jFrame.setVisible(true);
//			}
//		});
//		mnNewMenu_1.add(mntmNewMenuItem_3);
//	}
//
//}
//
//
//
////import java.awt.EventQueue;
////import java.io.FileInputStream;
////import java.io.FileOutputStream;
////import java.io.IOException;
////import java.io.ObjectInputStream;
////import java.io.ObjectOutputStream;
////import javax.swing.JFrame;
////import javax.swing.JPanel;
////import javax.swing.JMenuBar;
////import javax.swing.JMenu;
////import javax.swing.JMenuItem;
////import java.awt.event.ActionListener;
////import java.awt.event.ActionEvent;
////import java.awt.event.WindowAdapter;
////import java.awt.event.WindowEvent;
////import java.util.ArrayList;
////import data.Result;
////import data.Employee;
////
////public class pro extends JFrame {
////
////	private static final long serialVersionUID = 1L;
////	private JPanel jPanel;
////	private JFrame jFrame;
////	private static final String RESULT_FILE_PATH = "result.ser";
////	private Result result; // 是一个成员变量
////
////	/**
////	 * Launch the application.
////	 */
////	public static void main(String[] args) {
////		EventQueue.invokeLater(new Runnable() {
////			public void run() {
////				try {
////					pro frame = new pro();
////					frame.setVisible(true);
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////			}
////		});
////	}
////
////	/**
////	 * Create the frame.
////	 */
////	public pro() {
////		jFrame = this;
////
////		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		setBounds(100, 100, 578, 990);
////
////		JMenuBar menuBar = new JMenuBar();
////		setJMenuBar(menuBar);
////
////		JMenu mnNewMenu = new JMenu("员工管理");
////		menuBar.add(mnNewMenu);
////
////		JMenuItem mntmNewMenuItem = new JMenuItem("添加员工");
////		mntmNewMenuItem.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				if (jPanel != null)
////					jFrame.remove(jPanel);
////				jPanel = new Panel();
////				((Panel) jPanel).setAddEmployeeListener(new Panel.AddEmployeeListener() {
////					@Override
////					public void onEmployeeAdded(Employee employee) {
////						addEmployeeToResult(employee);
////					}
////				});
////				((Panel) jPanel).setResult(result); // 设置Result对象
////				jFrame.getContentPane().add(jPanel);
////				jFrame.setVisible(true);
////			}
////		});
////		mnNewMenu.add(mntmNewMenuItem);
////
////		JMenuItem mntmNewMenuItem_1 = new JMenuItem("修改员工");
////		mntmNewMenuItem_1.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				if (jPanel != null)
////					jFrame.remove(jPanel);
////				jPanel = new ModifyEm(result); // 传递Result对象
////				jFrame.getContentPane().add(jPanel);
////				jFrame.setVisible(true);
////			}
////		});
////		mnNewMenu.add(mntmNewMenuItem_1);
////
////		JMenuItem mntmNewMenuItem_2 = new JMenuItem("删除员工");
////		mntmNewMenuItem_2.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				if (jPanel != null)
////					jFrame.remove(jPanel);
////				jPanel = new Delete(result); // 传递Result对象
////				jFrame.getContentPane().add(jPanel);
////				jFrame.setVisible(true);
////			}
////		});
////		mnNewMenu.add(mntmNewMenuItem_2);
////
////		JMenu mnNewMenu_1 = new JMenu("查询员工");
////		menuBar.add(mnNewMenu_1);
////
////		JMenuItem mntmNewMenuItem_3 = new JMenuItem("单条件");
////		mntmNewMenuItem_3.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				if (jPanel != null)
////					jFrame.remove(jPanel);
////				jPanel = new Search(result); // 传递Result对象
////				jFrame.getContentPane().add(jPanel);
////				jFrame.setVisible(true);
////			}
////		});
////		mnNewMenu_1.add(mntmNewMenuItem_3);
////
////		// 读取序列化文件
////		result = readResultFromFile();
////		if (result == null) {
////			System.out.println("Result 对象为空，初始化一个新的 Result 对象");
////			result = new Result(); // 初始化一个新的 Result 对象
////		}
////		if (result.getEmployees_list() == null) {
////			System.out.println("employees_list 为空，初始化一个新的 ArrayList");
////			result.setEmployees_list(new ArrayList<>());
////		}
////		saveResultToFile(); // 保存新的 Result 对象到文件
////
////		// 添加窗口关闭监听器
////		addWindowListener(new WindowAdapter() {
////			@Override
////			public void windowClosing(WindowEvent e) {
////				saveResultToFile();
////				System.exit(0);
////			}
////		});
////	}
////
////	private Result readResultFromFile() {
////		Result result = null;
////		try (FileInputStream fileIn = new FileInputStream(RESULT_FILE_PATH);
////			 ObjectInputStream in = new ObjectInputStream(fileIn)) {
////			result = (Result) in.readObject();
////			System.out.println("Result 对象已从文件中读取");
////		} catch (IOException | ClassNotFoundException e) {
////			System.out.println("读取 Result 对象时发生错误: " + e.getMessage());
////		}
////		return result;
////	}
////
////	private void saveResultToFile() {
////		try (FileOutputStream fileOut = new FileOutputStream(RESULT_FILE_PATH);
////			 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
////			out.writeObject(result);
////			System.out.println("Result 对象已保存到文件中");
////		} catch (IOException e) {
////			System.out.println("保存 Result 对象时发生错误: " + e.getMessage());
////		}
////	}
////
////	private void addEmployeeToResult(Employee employee) {
////		if (result.getEmployees_list() == null) {
////			result.setEmployees_list(new ArrayList<>());
////		}
////		result.getEmployees_list().add(employee);
////		System.out.println("成功添加员工");
////	}
////
////	public Result getResult() {
////		return result;
////	}
////}
//
//

//第二版

/*
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class pro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	private JFrame jFrame;
	private Socket socket;


	 // Launch the application.

	public static void main(String[] args) {
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


	//  Create the frame.


public pro() {
		jFrame = this;

		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 990);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("员工管理");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("添加员工");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new Panel(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("修改员工");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new ModifyEm(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("删除员工");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new Delete(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_1 = new JMenu("查询员工");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("单条件");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new Search(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);
	}
}
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;


public class pro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	private JFrame jFrame;
	private Socket socket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new Panel(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("修改员工");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new ModifyEm(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("删除员工");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new Delete(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_1 = new JMenu("查询员工");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("单条件");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jPanel != null)
					jFrame.remove(jPanel);
				try {
					socket = new Socket("localhost", 8983); // 连接到本地服务端
					jPanel = new Search(socket);
					jFrame.getContentPane().add(jPanel);
					jFrame.setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(jFrame, "无法连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);

		// 添加窗口关闭监听器
		addWindowClosingListener();
	}

	private void addWindowClosingListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (socket != null && !socket.isClosed()) {
					try {
						socket.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
	}
}
