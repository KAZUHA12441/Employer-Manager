package project;

//原版
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.net.Socket;
//
//public class Search extends JPanel {
//
//	private static final long serialVersionUID = 1L;
//	private JTextField textField;
//	private JTable table;
//
//	/**
//	 * Create the panel.
//	 */
//	public Search(Socket socket) {
//		setLayout(new BorderLayout(0, 0));
//
//		JPanel panel = new JPanel();
//		add(panel, BorderLayout.CENTER);
//		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//
//		textField = new JTextField();
//		panel.add(textField);
//		textField.setColumns(10);
//
//		JComboBox comboBox = new JComboBox();
//		comboBox.setModel(new DefaultComboBoxModel(new String[] {"请选择", "工号", "姓名"}));
//		panel.add(comboBox);
//
//		JButton btnNewButton = new JButton("开始查询");
//		btnNewButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		panel.add(btnNewButton);
//
//		FlowLayout f=(FlowLayout)panel.getLayout();//水平间距
//		f.setHgap(60);//水平间距
//
//		table = new JTable();
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//			},
//			new String[] {
//				"\u59D3\u540D", "\u6027\u522B", "\u5DE5\u53F7", "\u7535\u8BDD\u53F7\u7801"
//			}
//		));
//		table.getColumnModel().getColumn(2).setPreferredWidth(170);
//		table.getColumnModel().getColumn(3).setPreferredWidth(225);
//		table.setPreferredScrollableViewportSize(new Dimension(800,700));
//		JScrollPane scrollPane = new JScrollPane(table);
//		add(scrollPane, BorderLayout.SOUTH);
//
//
//		scrollPane.add(table.getTableHeader());
//
//	}
//}



import data.Employee;
import data.Result;
import data.Search_Key;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;
import com.google.gson.Gson;



public class Search extends JPanel {

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
	private List<Employee> employees;

	public Search(Socket socket) {
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

		btnSearch = new JButton("开始查询");
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
					updateTable();
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
					updateTable();
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
				employees = result.getEmployees_list();
				totalPages = (int) Math.ceil((double) employees.size() / pageSize);
				currentPage = 1; // 重置当前页码
				updateTable();
			} else {
				JOptionPane.showMessageDialog(this,"searchEmployee() ");
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


				employees = result.getEmployees_list();
				totalPages = (int) Math.ceil((double) employees.size() / pageSize);
				currentPage = 1; // 重置当前页码
				JOptionPane.showMessageDialog(this, "我是傻逼");
				updateTable();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "通信错误: " + e.getMessage());
		}
	}

	private void updateTable() {
		model.setRowCount(0);
		int startIndex = (currentPage - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, employees.size());

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
	}
}



