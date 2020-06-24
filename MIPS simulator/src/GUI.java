import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.*;
import java.beans.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
//_______________________________________________________


/* TODO:

1- Delete Row when the current instruction executed.
2- modify the size of the row to accommodate the immediate

*/
public class GUI 
{

	private JFrame frmMipsSimulator;
	private JTextField CurrentInstruction;
	private JTextField PC;
	private JTextField InstructionType;
	public JTable table_2;
	
	public static LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
	public static LinkedHashMap<String, Integer> mem = new LinkedHashMap<String, Integer>();
	public static LinkedHashMap<String, Integer> labels = new LinkedHashMap<String, Integer>();
	public static Vector<String> errors = new Vector<String>();
	
	public String[] after_split  = {};
	private JTextArea codearea;
	public  DefaultTableModel tableModel;

	public static int Program_Counter = 0;
	public Parser p = new Parser();
	private JTable Memory;
	
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmMipsSimulator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void insertRow()
	{
		if(p.type == 'r')
		{
			Object[] columnName = {"Opcode", "rs", "rt" , "rd" , "shamt" , "funct"};
			Object[] Values = {p.op, p.src1, p.src2 , p.dest , p.Shamt , p.func};
			
			DefaultTableModel test = (DefaultTableModel)table_2.getModel();
			test.addRow(columnName);
			test.addRow(Values);	
					
		}else if(p.type == 'i')
		{
			Object[] columnName = {"Opcode", "rs", "rt", "imm"};
			Object[] Values = {p.op, p.src1, p.dest , p.imm};
			
			DefaultTableModel test = (DefaultTableModel)table_2.getModel();
			test.addRow(columnName);
			test.addRow(Values);
		}else if(p.type == 'j')
		{
			Object[] columnName = {"Opcode", "imm"};
			Object[] Values = {p.op, p.imm};
			
			DefaultTableModel test = (DefaultTableModel)table_2.getModel();
			test.addRow(columnName);
			test.addRow(Values);
			
		}
	}
	
	public void initializeRegister()
	{
		 map.put("$0",0);
		 map.put("$at",Integer.parseInt("1000",16));
		 map.put("$v0",null);
		 map.put("$v1",null);
		 map.put("$a0",null);
		 map.put("$a1",null);
		 map.put("$a2",null);
		 map.put("$a3",null);
		 map.put("$t0",null);
		 map.put("$t1",null);
		 map.put("$t2",null);
		 map.put("$t3",null);
		 map.put("$t4",null);
		 map.put("$t5",null);
		 map.put("$t6",null);
		 map.put("$t7",null);
		 map.put("$s0",null);
		 map.put("$s1",null);
		 map.put("$s2",null);
		 map.put("$s3",null);
		 map.put("$s4",null);
		 map.put("$s5",null);
		 map.put("$s6",null);
		 map.put("$s7",null);
		 map.put("$t8",null);
		 map.put("$t9",null);
		 map.put("$k0",null);
		 map.put("$k1",null);
		 map.put("$gp",null);
		 map.put("$sp",null);
		 map.put("$fp",null);
		 map.put("$ra",null);
		 
	}
	
	public void initializeMemory()
	{
		/// Data segment.
				for(int i = 4096; i < 5096; i += 4)
				{	
					
					mem.put("0x0000" + Integer.toHexString(i), null);
					
					
				}
	}
	
	private void initialize() 
	{
		initializeRegister();
		initializeMemory();
		
		//
			
//____________________________________________________________________________________
		frmMipsSimulator = new JFrame();
		frmMipsSimulator.getContentPane().setBackground(new Color(95, 158, 160));
		frmMipsSimulator.setTitle("Mips Simulator");
		frmMipsSimulator.setBounds(100, 100, 1243, 577);
		

		frmMipsSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMipsSimulator.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Execute Current");
		btnNewButton.setEnabled(false);
		
		btnNewButton.setFont(new Font("Corbel", Font.BOLD, 14));
		btnNewButton.setBounds(879, 485, 201, 41);
		frmMipsSimulator.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Execute entire program");
		btnNewButton_1.setEnabled(false);
		
		btnNewButton_1.setFont(new Font("Corbel", Font.BOLD, 14));
		btnNewButton_1.setBounds(455, 485, 215, 41);
		frmMipsSimulator.getContentPane().add(btnNewButton_1);
		
		JTabbedPane Options = new JTabbedPane(JTabbedPane.TOP);
		Options.setBounds(369, 10, 844, 392);
		frmMipsSimulator.getContentPane().add(Options);
		
		JPanel panel_1 = new JPanel();
		Options.addTab("Text Edito", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 841, 365);
		panel_1.add(scrollPane);
		
		codearea = new JTextArea();
		codearea.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		codearea.setBackground(Color.white);
		scrollPane.setViewportView(codearea);
		
		JPanel panel = new JPanel();
		Options.addTab("Data Segment", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPaneDataSeg = new JScrollPane();
		scrollPaneDataSeg.setBounds(0, 0, 839, 365);
		panel.add(scrollPaneDataSeg);
		
		DefaultTableModel modelformem = new DefaultTableModel(new Object[] { "Key", "Value" }, 0);
	    for(Map.Entry<String,Integer> entry : mem.entrySet()) 
	    {
	    	modelformem.addRow(new Object[] { entry.getKey(), entry.getValue() });
	    }
		Memory = new JTable(modelformem);
		scrollPaneDataSeg.setViewportView(Memory);
		
		LineNumberingTextArea linenum =  new LineNumberingTextArea(codearea);
		scrollPane.setRowHeaderView(linenum);
		
		JLabel lblNewLabel = new JLabel("Current Instruction");
		lblNewLabel.setFont(new Font("Corbel", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 307, 116, 37);
		frmMipsSimulator.getContentPane().add(lblNewLabel);
		
		CurrentInstruction = new JTextField();
		CurrentInstruction.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		CurrentInstruction.setBounds(10, 347, 215, 37);
		frmMipsSimulator.getContentPane().add(CurrentInstruction);
		CurrentInstruction.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Program Counter");
		lblNewLabel_1.setFont(new Font("Corbel", Font.BOLD, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 394, 116, 26);
		frmMipsSimulator.getContentPane().add(lblNewLabel_1);
		
		PC = new JTextField();
		PC.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		PC.setBounds(10, 423, 215, 32);
		frmMipsSimulator.getContentPane().add(PC);
		PC.setColumns(10);
		
		JLabel lblInstructionType = new JLabel("Instruction type");
		lblInstructionType.setFont(new Font("Corbel", Font.BOLD, 12));
		lblInstructionType.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstructionType.setBounds(10, 465, 102, 34);
		frmMipsSimulator.getContentPane().add(lblInstructionType);
		
		InstructionType = new JTextField();
		InstructionType.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		InstructionType.setBounds(10, 492, 215, 31);
		frmMipsSimulator.getContentPane().add(InstructionType);
		InstructionType.setColumns(10);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 21, 333, 261);
		frmMipsSimulator.getContentPane().add(tabbedPane_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("Register File", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 328, 234);
		panel_2.add(scrollPane_1);
		DefaultTableModel modelforTable1 = new DefaultTableModel(
		        new Object[] { "Key", "Value" }, 0
		    );
		    for(Map.Entry<String,Integer> entry : map.entrySet()) 
		    {
		    	modelforTable1.addRow(new Object[] { entry.getKey(), entry.getValue() });
		    }
		
		    
		
		JTable table_1 = new JTable(modelforTable1);
		scrollPane_1.setViewportView(table_1);
		String col[] = {"Pos","Team","P", "W", "L", "D"};
	    
		tableModel = new DefaultTableModel(col, 0);
		
		
		
		
		table_2 =  new JTable(tableModel);
		table_2.setFillsViewportHeight(true);
		table_2.setBounds(369, 412, 844, 37);
		frmMipsSimulator.getContentPane().add(table_2);
		
		JButton btnValidate = new JButton("Validate");
		btnValidate.setFont(new Font("Corbel", Font.BOLD, 14));
		btnValidate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//BufferedReader br = new BufferedReader();
				String instruction = codearea.getText();
				after_split = instruction.split("\n");
				/// Load all labels to perform validation.
				
				for (int i = 0; i < after_split.length; i++) 
				{
				     String line = after_split[i].toString();
				     if(line.contains(Character.toString(':')))
				     {
				    	labels.put(line.substring(0, line.length() - 1), i);
				     }
				}
				
				validate.checkForErrors(after_split,labels,errors);
				
				String allErrors = "";
				for(int i = 0; i < errors.size(); i++)
				{
					allErrors += errors.get(i) + "\n";
				}
				if(errors.size() > 0)
				{
					JOptionPane.showMessageDialog(new JFrame(), allErrors, "Dialog",
						     JOptionPane.ERROR_MESSAGE);
					errors.clear();
				}else
				{
					JOptionPane.showMessageDialog(new JFrame(), "Now you can execute the program", "Dialog",
						     JOptionPane.INFORMATION_MESSAGE);
				}
				/// to be able to execute again.
				Program_Counter = 0;
				initializeRegister();
				initializeMemory();
				DefaultTableModel modelforTable1 = new DefaultTableModel(new Object[] { "Key", "Value" }, 0);
			    for(Map.Entry<String,Integer> entry : map.entrySet()) 
			    {
			    	modelforTable1.addRow(new Object[] { entry.getKey(), entry.getValue() });
			    }
			table_1.setModel(modelforTable1);
			
			DefaultTableModel modelformem = new DefaultTableModel(new Object[] { "Key", "Value" }, 0);
		    for(Map.Entry<String,Integer> entry : mem.entrySet()) 
		    {
		    	modelformem.addRow(new Object[] { entry.getKey(), entry.getValue() });
		    }
		Memory.setModel(modelformem);
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(true);
				//btnValidate.setEnabled(false);
			}
		});
		btnValidate.setBounds(712, 485, 126, 41);
		frmMipsSimulator.getContentPane().add(btnValidate);
		
		
		
		btnNewButton.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				
				/// To delete the table that represent the instruction format of each instruction.
				
				DefaultTableModel model = (DefaultTableModel) table_2.getModel();
				model.setRowCount(0);
				//___________________________________________________________________
				if(Program_Counter == 0)
				{
					/// load all instruction in string and then split on \n.
					String instruction = codearea.getText();
					after_split = instruction.split("\n");
					/// load all labels.
					
					for (int i = 0; i < after_split.length; i++) 
					{
					     String line = after_split[i].toString();
					     if(line.contains(Character.toString(':')))
					     {
					    	labels.put(line.substring(0, line.length() - 1), i);
					     }
					}
				}
				/// advance program counter at labels
				if(after_split[Program_Counter].contains(":"))
				{
					Program_Counter++;

				}
				
				if(Program_Counter < after_split.length)
				{
					/// get the current instruction by the PC and display it in the current instruction textArea.

					CurrentInstruction.setText(after_split[Program_Counter]);
					/// increment Program counter.
					Program_Counter++;
					PC.setText(Integer.toString(Program_Counter));
					
					/// call parse function to fill the attributes of the instruction.
					p.Parse(CurrentInstruction.getText(), map, labels);
					
					
					/// Call execute method in CPU class to calculate or execute the instruction.
					Program_Counter = CPU.Execute(p, map, Program_Counter, labels, mem);
					
					
					/// Remove the old map and reload it again with the new table model to contain the new values
				    

					DefaultTableModel modelforTable1 = new DefaultTableModel(new Object[] { "Key", "Value" }, 0);
					    for(Map.Entry<String,Integer> entry : map.entrySet()) 
					    {
					    	modelforTable1.addRow(new Object[] { entry.getKey(), entry.getValue() });
					    }
					table_1.setModel(modelforTable1);
					
					DefaultTableModel modelformem = new DefaultTableModel(new Object[] { "Key", "Value" }, 0);
				    for(Map.Entry<String,Integer> entry : mem.entrySet()) 
				    {
				    	modelformem.addRow(new Object[] { entry.getKey(), entry.getValue() });
				    }
				Memory.setModel(modelformem);
					
					InstructionType.setText(Character.toString(p.type));
					insertRow();
					
				}
				if(Program_Counter == after_split.length)
				{
					btnNewButton.setEnabled(false);
					btnNewButton_1.setEnabled(false);
				}
				
			}
			
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				
				
				if(Program_Counter == 0)
				{
					/// load all instruction in string and then split on \n.
					String instruction = codearea.getText();
					after_split = instruction.split("\n");
					/// load all labels.
					
					for (int i = 0; i < after_split.length; i++) 
					{
					     String line = after_split[i].toString();
					     if(line.contains(Character.toString(':')))
					     {
					    	labels.put(line.substring(0, line.length() - 1), i);
					     }
					}
				 }
					while(Program_Counter != after_split.length)
					{
						/// To delete the table that represent the instruction format of each instruction.
						
						DefaultTableModel model = (DefaultTableModel) table_2.getModel();
						model.setRowCount(0);
						//___________________________________________________________________
						/// advance program counter at labels
						if(after_split[Program_Counter].contains(":"))
						{
							Program_Counter++;

						}
						
						if(Program_Counter < after_split.length)
						{
							/// get the current instruction by the PC and display it in the current instruction textArea.

							CurrentInstruction.setText(after_split[Program_Counter]);
							/// increment Program counter.
							Program_Counter++;
							PC.setText(Integer.toString(Program_Counter));
							
							/// call parse function to fill the attributes of the instruction.
							p.Parse(CurrentInstruction.getText(), map, labels);
							
							
							/// Call execute method in CPU class to calculate or execute the instruction.
							Program_Counter = CPU.Execute(p, map, Program_Counter, labels, mem);
							
							
							/// Remove the old map and reload it again with the new table model to contain the new values
						    

							DefaultTableModel modelforTable1 = new DefaultTableModel(new Object[] { "Key", "Value" }, 0);
							    for(Map.Entry<String,Integer> entry : map.entrySet()) 
							    {
							    	modelforTable1.addRow(new Object[] { entry.getKey(), entry.getValue() });
							    }
							table_1.setModel(modelforTable1);
							
							DefaultTableModel modelformem = new DefaultTableModel(new Object[] { "Key", "Value" }, 0);
						    for(Map.Entry<String,Integer> entry : mem.entrySet()) 
						    {
						    	modelformem.addRow(new Object[] { entry.getKey(), entry.getValue() });
						    }
						Memory.setModel(modelformem);
							
							InstructionType.setText(Character.toString(p.type));
							insertRow();
							
						}	
						}
						
					}
				
		});
		
		
		 /// Registers.
		 int row = 0;
		 for(Map.Entry<String,Integer> entry: map.entrySet())
		 {
			 /// Registers.
		      table_1.setValueAt(entry.getKey(),row,0);
		      table_1.setValueAt(entry.getValue(),row,1);
		      row++;
		 }
		 row = 0;
		 for(Map.Entry<String,Integer> entry: mem.entrySet())
		 {
			 /// Memory.
		      Memory.setValueAt(entry.getKey(),row,0);
		      Memory.setValueAt(entry.getValue(),row,1);
		      row++;
		 }
		 
		 
		     

		 
		codearea.getDocument().addDocumentListener(new DocumentListener() 
		{
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				linenum.updateLineNumbers();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				linenum.updateLineNumbers();

				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				linenum.updateLineNumbers();

			}
			
		});
		
		
		
	}
}
