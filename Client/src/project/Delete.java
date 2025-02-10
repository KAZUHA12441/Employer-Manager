package project;
//

 //原版
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//
//public class Delete extends JPanel {
//
//	private static final long serialVersionUID = 1L;
//	private JTextField textField;
//	private JTable table;
//
//
//	 // Create the panel. 删除员工界面
//
//	public Delete() {
//		setLayout(new BorderLayout(0, 0));
//
//		JPanel panel = new JPanel();
//		add(panel, BorderLayout.NORTH);
//
//		textField = new JTextField();
//		textField.setText("工号/姓名");
//		textField.setColumns(10);
//		panel.add(textField);
//
//		JComboBox comboBox = new JComboBox();
//		comboBox.setModel(new DefaultComboBoxModel(new String[] {"请选择", "工号", "性别"}));
//		panel.add(comboBox);
//
//		JButton btnNewButton_1 = new JButton("开始删除");
//		panel.add(btnNewButton_1);
//
//
//
//		table = new JTable();
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//			},
//			new String[] {
//				"\u59D3\u540D", "\u59D3\u540D", "\u5DE5\u53F7", "\u7535\u8BDD\u53F7\u7801"
//			}
//		));
//
//		JScrollPane scrollPane = new JScrollPane(table);
//		add(scrollPane, BorderLayout.CENTER);
//
//	}
//
//}



import data.Employee;
import data.Result;
import data.Search_Key;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.List;
import com.google.gson.Gson;

public class Delete extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;
	private JComboBox<String> comboBox;
	private JButton btnNewButton_1;
	private JButton btnPrevPage;
	private JButton btnNextPage;
	private JLabel lblPageNumber;
	private Socket socket;
	private Gson gson;
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalPages = 1;


	public Delete(Socket socket) {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		textField = new JTextField();
		textField.setText("工号/姓名");
		textField.setColumns(10);
		panel.add(textField);

		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"请选择", "工号", "姓名"}));
		panel.add(comboBox);

		btnNewButton_1 = new JButton("开始删除");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteEmployee();
			}
		});
		panel.add(btnNewButton_1);

		model = new DefaultTableModel(
				new Object[][]{},
				new String[]{"姓名", "性别", "工号", "职位", "电话号码", "薪水"}
		);

		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		// 分页控件
		JPanel paginationPanel = new JPanel();
		add(paginationPanel, BorderLayout.SOUTH);

		btnPrevPage = new JButton("上一页");
		btnPrevPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentPage > 1) {
					currentPage--;
					loadEmployeeData();
				}
			}
		});
		paginationPanel.add(btnPrevPage);

		lblPageNumber = new JLabel("第 1 页");
		paginationPanel.add(lblPageNumber);

		btnNextPage = new JButton("下一页");
		btnNextPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentPage < totalPages) {
					currentPage++;
					loadEmployeeData();
				}
			}
		});
		paginationPanel.add(btnNextPage);

		// 初始化 Gson
		gson = new Gson();

		// 连接到服务端
		try {
			this.socket = new Socket("localhost", 8983); // 假设服务端运行在本地，端口为8983
			loadEmployeeData(); // 加载所有员工数据到表格中
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "无法连接到服务器: " + e.getMessage());
		}

		// 添加右键菜单
		addRightClickMenu();
	}

	private void addRightClickMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem deleteItem = new JMenuItem("删除该行");
		deleteItem.addActionListener(e -> handleDelete());
		popupMenu.add(deleteItem);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					int row = table.rowAtPoint(e.getPoint());
					if (row >= 0 && row < table.getRowCount()) {
						table.setRowSelectionInterval(row, row);
						popupMenu.show(table, e.getX(), e.getY());
					}
				}
			}
		});
	}

	/*  原版
	private void deleteEmployee() {

		String key = textField.getText().trim();
		String field = (String) comboBox.getSelectedItem();

		if (key.isEmpty() || field.equals("请选择")) {
			JOptionPane.showMessageDialog(this, "请输入有效的工号或姓名，并选择字段");
			return;
		}

		Search_Key searchKey = new Search_Key();
		searchKey.setKey(key);
		searchKey.setFiled(field);

		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			// 发送删除请求
			out.println("delete");
			out.println(gson.toJson(searchKey));

			// 接收响应
			String response = in.readLine();
			Result result = gson.fromJson(response, Result.class);

			if (result.isSuccess()) {
				JOptionPane.showMessageDialog(this, result.getMessage());
				// 更新表格
				loadEmployeeData();
			} else {
				JOptionPane.showMessageDialog(this, result.getMessage());
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
		}
	}*/

	private void deleteEmployee() {
		String key = textField.getText().trim();
		String field = (String) comboBox.getSelectedItem();

		if (key.isEmpty() || field.equals("请选择")) {
			JOptionPane.showMessageDialog(this, "请输入有效的工号或姓名，并选择字段");
			return;
		}

		Search_Key searchKey = new Search_Key();
		searchKey.setKey(key);
		searchKey.setFiled(field);

		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			// 发送删除请求
			out.println("delete");
			out.println(gson.toJson(searchKey));

			// 接收响应
			String response = in.readLine();
			Result result = gson.fromJson(response, Result.class);

			if (result.isSuccess()) {
				JOptionPane.showMessageDialog(this, result.getMessage());
				// 更新表格
				loadEmployeeData();
			} else {
				JOptionPane.showMessageDialog(this, result.getMessage());
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
		}
	}

	private void handleDelete() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			int employeeID = (Integer) model.getValueAt(selectedRow, 2); // 获取工号

			// 发送删除请求
			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

				out.println("delete");
				Search_Key searchKey = new Search_Key();
				searchKey.setKey(String.valueOf(employeeID));
				searchKey.setFiled("工号");
				out.println(gson.toJson(searchKey));

				// 接收响应
				String response = in.readLine();
				Result result = gson.fromJson(response, Result.class);

				if (result.isSuccess()) {
					JOptionPane.showMessageDialog(this, result.getMessage());
					// 更新表格
					model.removeRow(selectedRow);
					loadEmployeeData(); // 重新加载数据
				} else {
					JOptionPane.showMessageDialog(this, result.getMessage());
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
			}
		}
	}
 //原版
/*	private void loadEmployeeData() {
		try {
			// 发送搜索请求以获取所有员工数据
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println("search");
			out.println(gson.toJson(new Search_Key()));

			// 接收响应
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = in.readLine();
			Result result = gson.fromJson(response, Result.class);

			if (result.isSuccess()) {
				List<Employee> employees = result.getEmployees_list();
				totalPages = (int) Math.ceil((double) employees.size() / pageSize);

				int startIndex = (currentPage - 1) * pageSize;
				int endIndex = Math.min(startIndex + pageSize, employees.size());

				model.setRowCount(0);
				for (int i = startIndex; i < endIndex; i++) {
					Employee employee = employees.get(i);
					model.addRow(new Object[] {
							employee.getName(),
							employee.getSex(),
							employee.getID(),
							employee.getPosts(),
							employee.getPhoneNumber(),
							employee.getSalary()
					});
				}

				lblPageNumber.setText("第 " + currentPage + " 页 / 共 " + totalPages + " 页");
			} else {
				JOptionPane.showMessageDialog(this, result.getMessage());
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
		}
	}*/

	private void loadEmployeeData() {
		try {
			// 发送搜索请求以获取所有员工数据
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println("search");
			out.println(gson.toJson(new Search_Key()));

			// 接收响应
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = in.readLine();
			Result result = gson.fromJson(response, Result.class);

			if (result.isSuccess()) {
				List<Employee> employees = result.getEmployees_list();
				totalPages = (int) Math.ceil((double) employees.size() / pageSize);

				int startIndex = (currentPage - 1) * pageSize;
				int endIndex = Math.min(startIndex + pageSize, employees.size());

				model.setRowCount(0);
				for (int i = startIndex; i < endIndex; i++) {
					Employee employee = employees.get(i);
					model.addRow(new Object[] {
							employee.getName(),
							employee.getSex(),
							employee.getID(),
							employee.getPosts(),
							employee.getPhoneNumber(),
							employee.getSalary()
					});
				}

				lblPageNumber.setText("第 " + currentPage + " 页 / 共 " + totalPages + " 页");
			} else {
				JOptionPane.showMessageDialog(this, result.getMessage());
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
		}
	}
}





