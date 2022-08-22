package schd;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	GraphDraw gd = null;
	Vector<Process> pv = new Vector<Process>();
	Vector<Process> savepv = new Vector<Process>();
	Vector<Result> rv = new Vector<Result>();
	int timeSlice;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public static Vector<Process> sort(Vector<Process> pv)
	{
		int i = 0, j = 0;
		for(i = 0; i < pv.size(); i++)
		{
			for(j = i-1; j > -1; j--) 
			{
				if(pv.get(i).arriveTime >= pv.get(j).arriveTime)
				{
					break;
				}		
			}
			if(j+1 != i) {
				pv.add(j+1, pv.get(i));
				pv.remove(i+1);
			}
		}
		return pv;
	}
	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Process Scheduling");
		timeSlice = 5;
		
		Process a, b, c, d;
		a = new Process(1, 0, 7, 2);
		b = new Process(2, 7, 8, 3);
		c = new Process(3, 13, 12, 2);
		d = new Process(4, 4, 13, 1);
		
		pv.add(a); pv.add(b); pv.add(c); pv.add(d); pv = sort(pv);
	
		for(int i = 0; i < pv.size(); i++)
			savepv.add(pv.get(i));
		
		table = new JTable();
		table.setForeground(Color.BLACK);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"PID", "도착 시간", "실행 시간", "Priority"
			}
		));
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = 0; i < pv.size(); i++)
		{
			model.addRow(new Object[]{pv.get(i).processID, pv.get(i).arriveTime, pv.get(i).burstTime, pv.get(i).priority});
		}
		
		
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.setCellSelectionEnabled(true);
		table.setBounds(12, 55, 300, 116);
		contentPane.add(table);
		JScrollPane jsp = new JScrollPane(table);
		contentPane.add(jsp);
		jsp.setBounds(12, 93, 300, 116);
		jsp.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {"FCFS", "SJF", "PS", "RR", "preemptiveSJF", "preemptivePS"}));
		comboBox.setBounds(324, 119, 120, 40);
		contentPane.add(comboBox);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"PID", "Turnaround Time", "Waiting Time", "Response Time"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setForeground(Color.BLACK);
		table_1.setColumnSelectionAllowed(true);
		table_1.setCellSelectionEnabled(true);
		table_1.setBounds(12, 227, 432, 116);
		contentPane.add(table_1);
		JScrollPane jsp_1 = new JScrollPane(table_1);
		contentPane.add(jsp_1);
		jsp_1.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		jsp_1.setBounds(12, 290, 432, 116);
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(12, 415, 222, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(12, 440, 222, 24);
		contentPane.add(lblNewLabel_1);
		
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(12, 256, 127, 24);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(12, 465, 222, 24);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setBounds(12, 490, 222, 24);
		contentPane.add(lblNewLabel_1_2);
		
		JButton btnRun = new JButton("실행");
		btnRun.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				int watingTime = 0;
				
				if(rv.size() != 0)
				{
					DefaultTableModel model = (DefaultTableModel) table_1.getModel();
					for(int i = 0; i < rv.size(); i++)
					{
						model.removeRow(0);
					}
					rv.removeAllElements();
				}
				
				if(comboBox.getSelectedItem() == "FCFS")
				{
					FCFS fcfs = new FCFS();
					fcfs.run(pv, rv);
					gd = fcfs.gd;
				}
				
				else if(comboBox.getSelectedItem() == "SJF")
				{
					SJF sjf = new SJF();
					sjf.run(pv, rv);
					gd = sjf.gd;
				}
				
				else if(comboBox.getSelectedItem() == "PS")
				{
					PS ps = new PS();
					ps.run(pv, rv);
					gd = ps.gd;
				}
				
				else if(comboBox.getSelectedItem() == "preemptiveSJF")
				{
					PreemptiveSJF psjf = new PreemptiveSJF();
					psjf.run(pv, rv);
					gd = psjf.gd;
				}
				
				else if(comboBox.getSelectedItem() == "preemptivePS")
				{
					PreemptivePS pps = new PreemptivePS();
					pps.run(pv, rv);
					gd = pps.gd;
				}
				
				else if(comboBox.getSelectedItem() == "RR")
				{
					RR rr = new RR();
					rr.timeSlice = timeSlice;
					rr.run(pv, rv);
					gd = rr.gd;
				}
				
				lblNewLabel_2.setText((String)comboBox.getSelectedItem());
				DefaultTableModel model = (DefaultTableModel) table_1.getModel();
				for(int i = 0; i < rv.size(); i++)
				{
					model.addRow(new Object[]{rv.get(i).processID, rv.get(i).endP - rv.get(i).arrivalP, rv.get(i).waitingTime, rv.get(i).startP - rv.get(i).arrivalP});
					watingTime += rv.get(i).waitingTime;
				}
				
				lblNewLabel.setText("전체 실행시간 : " + gd.sumOfBurst());
				lblNewLabel_1.setText("평균 대기시간 : " + (Math.round((double)watingTime / rv.size() * 1000)/1000.0));
				lblNewLabel_1_1.setText("Throughput : " + (Math.round(((double)rv.size() / gd.EndPList.get(gd.EndPList.size()-1)) * 1000)/1000.0));
				lblNewLabel_1_2.setText("CPU utilization ratio : " + (Math.round(((double)gd.sumOfBurst() / gd.EndPList.get(gd.EndPList.size()-1)) * 1000)/1000.0));
				
				
				for(int i = 0; i < savepv.size(); i++) 
				{
					pv.add(savepv.get(i));
				}
			}
		});
		
		btnRun.setBounds(324, 169, 120, 40);
		contentPane.add(btnRun);
		
		JButton btnNewButton = new JButton("간트 차트 보기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gd == null)
					return;
				
				ChartShow cs = new ChartShow();
				cs.gr = gd;
			}
		});
		btnNewButton.setBounds(324, 434, 120, 40);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("프로세스 추가");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("") || textField_1.getText().equals("") || textField_2.getText().equals("") || textField_3.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "값을 입력하십시오.");
					return;
				}
				String str;
				Character ch;
				
				str = textField.getText();
				for(int i = 0; i<str.length(); i++)
				{
					ch = str.charAt(i);
					if(!(Character.isDigit(ch)))
					{
						JOptionPane.showMessageDialog(null, "숫자를 입력하십시오.");
						return;
					}
				}
				str = textField_1.getText();
				for(int i = 0; i<str.length(); i++)
				{
					ch = str.charAt(i);
					if(!(Character.isDigit(ch)))
					{
						JOptionPane.showMessageDialog(null, "숫자를 입력하십시오.");
						return;
					}
				}
				str = textField_2.getText();
				for(int i = 0; i<str.length(); i++)
				{
					ch = str.charAt(i);
					if(!(Character.isDigit(ch)))
					{
						JOptionPane.showMessageDialog(null, "숫자를 입력하십시오.");
						return;
					}
				}
				str = textField_3.getText();
				for(int i = 0; i<str.length(); i++)
				{
					ch = str.charAt(i);
					if(!(Character.isDigit(ch)))
					{
						JOptionPane.showMessageDialog(null, "숫자를 입력하십시오.");
						return;
					}
				}
				if(Integer.parseInt(textField.getText()) > 99 || Integer.parseInt(textField.getText()) > 99 || Integer.parseInt(textField.getText()) > 99 || Integer.parseInt(textField.getText()) > 99)
				{
					JOptionPane.showMessageDialog(null, "100이하의 수를 입력하십시오.");
					return;
				}
				
				for(int i = 0; i < pv.size(); i++)
				{
					if(pv.get(i).processID == Integer.parseInt(textField.getText()))
					{
						JOptionPane.showMessageDialog(null, "이미 존재하는 PID입니다.");
						return;
					}
				}
				
				Process ps = new Process(Integer.parseInt(textField.getText()), Integer.parseInt(textField_1.getText()), Integer.parseInt(textField_2.getText()), Integer.parseInt(textField_3.getText()));
				
				pv.add(ps); 
				pv = sort(pv);
				for(int i = 0; i < savepv.size(); i++)
				{
					model.removeRow(0);
				}
				savepv.removeAllElements();
				for(int i = 0; i < pv.size(); i++) 
				{
					savepv.add(pv.get(i));
					model.addRow(new Object[]{pv.get(i).processID, pv.get(i).arriveTime, pv.get(i).burstTime, pv.get(i).priority} );
				}
			}
		});
		
		btnNewButton_1.setBounds(324, 37, 120, 20);
		contentPane.add(btnNewButton_1);
		
		textField = new JTextField();
		textField.setBounds(12, 37, 60, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(79, 37, 60, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(151, 37, 60, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(223, 37, 60, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("프로세스 삭제");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1)
				{
					JOptionPane.showMessageDialog(null, "삭제 할 프로세스를 선택하십시오.");
					return;
				}
				int a = table.getSelectedRow();
				pv.remove(a);
				savepv.remove(a);
				model.removeRow(a);
			}
		});
		btnNewButton_2.setBounds(324, 89, 120, 20);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel_3 = new JLabel("PID");
		lblNewLabel_3.setBounds(10, 12, 60, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("도착 시간");
		lblNewLabel_4.setBounds(79, 12, 60, 15);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("실행 시간");
		lblNewLabel_5.setBounds(151, 12, 60, 15);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Priority");
		lblNewLabel_6.setBounds(223, 12, 60, 15);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("현재 Time Slice = " + timeSlice);
		lblNewLabel_7.setBounds(195, 256, 127, 24);
		contentPane.add(lblNewLabel_7);
		
		JButton btnNewButton_3 = new JButton("Time Slice 설정");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textField_4.getText();
				Character c;
				if(str.equals(""))
				{
					JOptionPane.showMessageDialog(null, "값을 입력하십시오.");
					return;
				}
				for(int i = 0; i < str.length(); i++)
				{
					c = str.charAt(i);
					if(!(Character.isDigit(c)))
					{
						JOptionPane.showMessageDialog(null, "숫자를 입력하십시오.");
						return;
					}
				}
				if(Integer.parseInt(str) > 50)
				{
					JOptionPane.showMessageDialog(null, "50이하의 숫자를 입력하십시오.");
					return;
				}
				timeSlice = Integer.parseInt(str);
				lblNewLabel_7.setText("현재 Time Slice = " + timeSlice);
			}
		});
		btnNewButton_3.setBounds(324, 257, 120, 23);
		contentPane.add(btnNewButton_3);
		
		textField_4 = new JTextField();
		textField_4.setBounds(324, 226, 120, 21);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
	}
}
