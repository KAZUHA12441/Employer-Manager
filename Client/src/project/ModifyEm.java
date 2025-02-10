package project;


//原版
//
//import data.Employee;
//import data.Result;
//import data.Search_Key;
//import net.server;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.*;
//import java.net.Socket;
//import com.google.gson.Gson;
//
//public class ModifyEm extends JPanel {
//
//	private static final long serialVersionUID = 1L;
//	private JTextField searchField;
//	private JTable table;
//	private JComboBox<String> searchType;
//	private JButton searchButton;
//	private DefaultTableModel model;
//	private Socket socket;
//	private Result result;
//	private JFrame parentFrame; // 新增一个成员变量
//
//	public ModifyEm(Socket socket) {
//		this.socket = socket;
//		this.result = new Result();
//		setLayout(new BorderLayout(0, 0));
//
//		JPanel northPanel = new JPanel();
//		add(northPanel, BorderLayout.NORTH);
//
//		searchField = new JTextField();
//		searchField.setText("工号/姓名");
//		northPanel.add(searchField);
//		searchField.setColumns(10);
//
//		searchType = new JComboBox<>();
//		searchType.setModel(new DefaultComboBoxModel<>(new String[]{"请选择", "工号", "名字"}));
//		northPanel.add(searchType);
//
//		searchButton = new JButton("开始查询");
//		searchButton.addActionListener(this::handleSearch);
//		northPanel.add(searchButton);
//
//		model = new DefaultTableModel(
//				new Object[][]{},
//				new String[]{"姓名", "性别", "工号", "职位", "电话号码", "薪水"}
//		) {
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				return false;
//			}
//		};
//
//		table = new JTable(model);
//		JScrollPane scrollPane = new JScrollPane(table);
//		add(scrollPane, BorderLayout.CENTER);
//
//		// 添加右键菜单
//		addRightClickMenu();
//	}
//
//	private void loadEmployeeData(Result result) {
//		model.setRowCount(0);
//		for (Employee employee : result.getEmployees_list()) {
//			model.addRow(new Object[] {
//					employee.getName(),
//					employee.getSex(),
//					employee.getID(),
//					employee.getPosts(),
//					employee.getPhoneNumber(),
//					employee.getSalary()
//			});
//		}
//	}
//
//	private void handleSearch(ActionEvent e) {
//		try {
//			String keyword = searchField.getText().trim();
//			String type = (String) searchType.getSelectedItem();
//
//			if ("请选择".equals(type)) {
//				JOptionPane.showMessageDialog(this, "请选择查询类型");
//				return;
//			}
//
//			Search_Key searchKey = new Search_Key();
//			searchKey.setKey(keyword);
//			searchKey.setFiled(type);
//
//			Gson gson = new Gson();
//			String json = gson.toJson(searchKey);
//
//			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//			out.println("search");
//			out.println(json);
//
//			String response = in.readLine();
//			Result result = gson.fromJson(response, Result.class);
//			loadEmployeeData(result);
//		} catch (IOException ex) {
//			ex.printStackTrace();
//			JOptionPane.showMessageDialog(this, "查询失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//		}
//	}
//
//	private void addRightClickMenu() {
//		JPopupMenu popupMenu = new JPopupMenu();
//		JMenuItem modifyItem = new JMenuItem("修改该行员工信息");
//		modifyItem.addActionListener(this::handleModifyRow);
//		popupMenu.add(modifyItem);
//
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				if (SwingUtilities.isRightMouseButton(e)) {
//					int row = table.rowAtPoint(e.getPoint());
//					if (row >= 0 && row < table.getRowCount()) {
//						table.setRowSelectionInterval(row, row);
//						popupMenu.show(table, e.getX(), e.getY());
//					}
//				}
//			}
//		});
//	}
//	//原版
///*
//	private void handleModifyRow(ActionEvent e) {
//		int selectedRow = table.getSelectedRow();
//		if (selectedRow != -1) {
//			int employeeID = (Integer) model.getValueAt(selectedRow, 2);
//
//			// 创建一个对话框来修改员工信息
//			JDialog modifyDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "修改员工信息", true);
//			modifyDialog.setLayout(new GridLayout(0, 2));
//
//			JLabel sexLabel = new JLabel("性别:");
//			JTextField sexField = new JTextField((String) model.getValueAt(selectedRow, 1));
//			modifyDialog.add(sexLabel);
//			modifyDialog.add(sexField);
//
//			JLabel postsLabel = new JLabel("职位:");
//			JTextField postsField = new JTextField((String) model.getValueAt(selectedRow, 3));
//			modifyDialog.add(postsLabel);
//			modifyDialog.add(postsField);
//
//			JLabel phoneNumberLabel = new JLabel("电话号码:");
//			JTextField phoneNumberField = new JTextField(model.getValueAt(selectedRow, 4).toString());
//			modifyDialog.add(phoneNumberLabel);
//			modifyDialog.add(phoneNumberField);
//
//			JLabel salaryLabel = new JLabel("薪水:");
//			JTextField salaryField = new JTextField(model.getValueAt(selectedRow, 5).toString());
//			modifyDialog.add(salaryLabel);
//			modifyDialog.add(salaryField);
//
//			JButton saveButton = new JButton("保存");
//			saveButton.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					try {
//						String newSex = sexField.getText().trim();
//						String newPosts = postsField.getText().trim();
//						String newPhoneNumber = phoneNumberField.getText().trim();
//						String newSalary = salaryField.getText().trim();
//
//						Employee modifiedEmployee = new Employee();
//						modifiedEmployee.setID(employeeID);
//						modifiedEmployee.setName((String) model.getValueAt(selectedRow, 0));
//						modifiedEmployee.setSex(newSex);
//						modifiedEmployee.setPosts(newPosts);
//						modifiedEmployee.setPhoneNumber(Integer.valueOf(newPhoneNumber));
//						modifiedEmployee.setSalary(Integer.valueOf(newSalary));
//
//						Gson gson = new Gson();
//						String json = gson.toJson(modifiedEmployee);
//
//						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//						out.println("modify");
//						out.println(json);
//
//						String response = in.readLine();
//						Result result = gson.fromJson(response, Result.class);
//
//						if (result.isSuccess()) {
//							model.setValueAt(newSex, selectedRow, 1);
//							model.setValueAt(newPosts, selectedRow, 3);
//							model.setValueAt(newPhoneNumber, selectedRow, 4);
//							model.setValueAt(newSalary, selectedRow, 5);
//							JOptionPane.showMessageDialog(ModifyEm.this, "修改成功");
//						} else {
//							JOptionPane.showMessageDialog(ModifyEm.this, "修改失败: " + result.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//						}
//
//						modifyDialog.dispose();
//					} catch (Exception ex) {
//						ex.printStackTrace();
//						JOptionPane.showMessageDialog(ModifyEm.this, "修改失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//					}
//				}
//			});
//
//			modifyDialog.add(saveButton);
//			modifyDialog.pack();
//			modifyDialog.setLocationRelativeTo(this);
//			modifyDialog.setVisible(true);
//		} else {
//			JOptionPane.showMessageDialog(this, "请选择要修改的员工");
//		}
//	}*/
//private void handleModifyRow(ActionEvent e) {
//	int selectedRow = table.getSelectedRow();
//	if (selectedRow != -1) {
//		int employeeID = (Integer) model.getValueAt(selectedRow, 2);
//
//		// 创建一个对话框来修改员工信息
//		JDialog modifyDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "修改员工信息", true);
//		modifyDialog.setLayout(new GridLayout(0, 2));
//
//		// 性别
//		JLabel sexLabel = new JLabel("性别:");
//		JTextField sexField = new JTextField((String) model.getValueAt(selectedRow, 1));
//		modifyDialog.add(sexLabel);
//		modifyDialog.add(sexField);
//
//		// 职位
//		JLabel postsLabel = new JLabel("职位:");
//		JTextField postsField = new JTextField((String) model.getValueAt(selectedRow, 3));
//		modifyDialog.add(postsLabel);
//		modifyDialog.add(postsField);
//
//		// 电话号码
//		JLabel phoneNumberLabel = new JLabel("电话号码:");
//		JTextField phoneNumberField = new JTextField(model.getValueAt(selectedRow, 4).toString());
//		modifyDialog.add(phoneNumberLabel);
//		modifyDialog.add(phoneNumberField);
//
//		// 洒水
//		JLabel salaryLabel = new JLabel("薪水:");
//		JTextField salaryField = new JTextField(model.getValueAt(selectedRow, 5).toString());
//		modifyDialog.add(salaryLabel);
//		modifyDialog.add(salaryField);
//
//		// 保存按钮
//		JButton saveButton = new JButton("保存");
//		saveButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					String newSex = sexField.getText().trim();
//					String newPosts = postsField.getText().trim();
//					String newPhoneNumber = phoneNumberField.getText().trim();
//					String newSalary = salaryField.getText().trim();
//
//					Employee modifiedEmployee = new Employee();
//					modifiedEmployee.setID(employeeID);
//					modifiedEmployee.setName((String) model.getValueAt(selectedRow, 0));
//					modifiedEmployee.setSex(newSex);
//					modifiedEmployee.setPosts(newPosts);
//					modifiedEmployee.setPhoneNumber(Integer.valueOf(newPhoneNumber));
//					modifiedEmployee.setSalary(Integer.valueOf(newSalary));
//
//					// 发送修改请求到服务端
//					Gson gson = new Gson();
//					String json = gson.toJson(modifiedEmployee);
//
//					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//					out.println("modify");
//					out.println(json);
//
//					String response = in.readLine();
//					Result result = gson.fromJson(response, Result.class);
//
//					if (result.isSuccess()) {
//						model.setValueAt(newSex, selectedRow, 1);
//						model.setValueAt(newPosts, selectedRow, 3);
//						model.setValueAt(newPhoneNumber, selectedRow, 4);
//						model.setValueAt(newSalary, selectedRow, 5);
//						JOptionPane.showMessageDialog(ModifyEm.this, "修改成功");
//					} else {
//						JOptionPane.showMessageDialog(ModifyEm.this, "修改失败: " + result.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//					}
//
//					modifyDialog.dispose();
//				} catch (Exception ex) {
//					ex.printStackTrace();
//					JOptionPane.showMessageDialog(ModifyEm.this, "修改失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		});
//
//		modifyDialog.add(saveButton);
//		modifyDialog.pack();
//		modifyDialog.setLocationRelativeTo(this);
//		modifyDialog.setVisible(true);
//	} else {
//		JOptionPane.showMessageDialog(this, "请选择要修改的员工");
//	}
//}
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


public class ModifyEm extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;
	private JComboBox<String> comboBox;
	private JButton btnSearch;
	private JButton btnPrevPage;
	private JButton btnNextPage;
	private JLabel lblPageNumber;
	private Socket socket;
	private Gson gson;
	private int currentPage = 1;
	private int pageSize = 10;
	private int totalPages = 1;

	public ModifyEm(Socket socket) {
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

		btnSearch = new JButton("搜索");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchEmployee();
			}
		});
		panel.add(btnSearch);

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
			this.socket = socket;
			loadEmployeeData(); // 加载所有员工数据到表格中
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "无法连接到服务器: " + e.getMessage());
		}

		// 添加右键菜单
		addRightClickMenu();
	}

	private void addRightClickMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem modifyItem = new JMenuItem("修改该行");
		modifyItem.addActionListener(e -> handleModifyRow());
		popupMenu.add(modifyItem);

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

	private void handleModifyRow() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			int employeeID = (Integer) model.getValueAt(selectedRow, 2); // 获取工号

			// 弹出修改对话框
			EmployeeDialog dialog = new EmployeeDialog(employeeID, socket);
			dialog.setVisible(true);

			// 如果修改成功，重新加载数据
			if (dialog.isModified()) {
				loadEmployeeData();
			}
		}
	}

	private void searchEmployee() {
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

			// 发送搜索请求
			out.println("search");
			out.println(gson.toJson(searchKey));

			// 接收响应
			String response = in.readLine();
			Result result = gson.fromJson(response, Result.class);

			if (result.isSuccess()) {
				List<Employee> employees = result.getEmployees_list();
				model.setRowCount(0);
				for (Employee employee : employees) {
					model.addRow(new Object[] {
							employee.getName(),
							employee.getSex(),
							employee.getID(),
							employee.getPosts(),
							employee.getPhoneNumber(),
							employee.getSalary()
					});
				}
			} else {
				JOptionPane.showMessageDialog(this, result.getMessage());
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
		}
	}

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

	// 弹出对话框类，用于修改员工信息
	private class EmployeeDialog extends JDialog {
		private JTextField txtName;
		private JTextField txtSex;
		private JTextField txtID;
		private JTextField txtPosts;
		private JTextField txtPhoneNumber;
		private JTextField txtSalary;
		private JButton btnModify;
		private boolean modified = false;

		public EmployeeDialog(int employeeID, Socket socket) {
			setTitle("修改员工信息");
			setSize(400, 300);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setLayout(new GridLayout(7, 2));

			JLabel lblName = new JLabel("姓名:");
			txtName = new JTextField();
			add(lblName);
			add(txtName);

			JLabel lblSex = new JLabel("性别:");
			txtSex = new JTextField();
			add(lblSex);
			add(txtSex);

			JLabel lblID = new JLabel("工号:");
			txtID = new JTextField();
			txtID.setEditable(false);
			add(lblID);
			add(txtID);

			JLabel lblPosts = new JLabel("职位:");
			txtPosts = new JTextField();
			add(lblPosts);
			add(txtPosts);

			JLabel lblPhoneNumber = new JLabel("电话号码:");
			txtPhoneNumber = new JTextField();
			add(lblPhoneNumber);
			add(txtPhoneNumber);

			JLabel lblSalary = new JLabel("薪水:");
			txtSalary = new JTextField();
			add(lblSalary);
			add(txtSalary);

			btnModify = new JButton("修改");
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					handleModify(employeeID);
				}
			});
			add(btnModify);

			// 加载员工信息
			loadEmployeeInfo(employeeID);
		}

		private void loadEmployeeInfo(int employeeID) {
			try {
				// 发送搜索请求以获取员工信息
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				Search_Key searchKey = new Search_Key();
				searchKey.setKey(String.valueOf(employeeID));
				searchKey.setFiled("工号");
				out.println("search");
				out.println(gson.toJson(searchKey));

				// 接收响应
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String response = in.readLine();
				Result result = gson.fromJson(response, Result.class);

				if (result.isSuccess() && !result.getEmployees_list().isEmpty()) {
					Employee employee = result.getEmployees_list().get(0);
					txtName.setText(employee.getName());
					txtSex.setText(employee.getSex());
					txtID.setText(String.valueOf(employee.getID()));
					txtPosts.setText(employee.getPosts());
					txtPhoneNumber.setText(String.valueOf(employee.getPhoneNumber()));
					txtSalary.setText(String.valueOf(employee.getSalary()));
				} else {
					JOptionPane.showMessageDialog(this, "未找到该员工");
					dispose();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
			}
		}

		private void handleModify(int employeeID) {
			String name = txtName.getText().trim();
			String sex = txtSex.getText().trim();
			String posts = txtPosts.getText().trim();
			String phoneNumber = txtPhoneNumber.getText().trim();
			String salary = txtSalary.getText().trim();

			if (name.isEmpty() || sex.isEmpty() || posts.isEmpty() || phoneNumber.isEmpty() || salary.isEmpty()) {
				JOptionPane.showMessageDialog(this, "请填写所有字段");
				return;
			}

			Employee modifyEmployee = new Employee();
			modifyEmployee.setID(employeeID);
			modifyEmployee.setName(name);
			modifyEmployee.setSex(sex);
			modifyEmployee.setPosts(posts);
			modifyEmployee.setPhoneNumber(Integer.parseInt(phoneNumber));
			modifyEmployee.setSalary(Integer.parseInt(salary));

			try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

				// 发送修改请求
				out.println("modify");
				out.println(gson.toJson(modifyEmployee));

				// 接收响应
				String response = in.readLine();
				Result result = gson.fromJson(response, Result.class);

				if (result.isSuccess()) {
					JOptionPane.showMessageDialog(this, result.getMessage());
					modified = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, result.getMessage());
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
			}
		}

		public boolean isModified() {
			return modified;
		}
	}
}
