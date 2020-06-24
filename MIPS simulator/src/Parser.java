import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Parser 
{
	
	
	public String MachineCode = "";
	public String inst = "";
	public String src1 = "";
	public String src2 = "";
	public String dest = "";
	public String imm = "";
	
	public char type = '*';
	public String op = "";
	public String func = "";
	public String Shamt = "";
	
	public static String immForJump(int pos)
	{
		String binarized = Integer.toBinaryString(pos);
		int len = binarized.length();
		String fiveZeroes = "00000000000000000000000000";
		if (len < 26)
		  binarized = fiveZeroes.substring(0, 26-len).concat(binarized);
		else
		  binarized = binarized.substring(0, len);
		return binarized;

	}
	public static String Getimm(int pos)
	{
		String binarized = Integer.toBinaryString(pos);
		int len = binarized.length();
		String fiveZeroes = "0000000000000000";
		if (len < 16)
		  binarized = fiveZeroes.substring(0, 16-len).concat(binarized);
		else
		  binarized = binarized.substring(0, len);
		return binarized;

	}
	public static String GetBinaryValue(int pos)
	{
		String binarized = Integer.toBinaryString(pos);
		int len = binarized.length();
		String fiveZeroes = "00000";
		if (len < 5)
		  binarized = fiveZeroes.substring(0, 5-len).concat(binarized);
		else
		  binarized = binarized.substring(0, len);
		return binarized;

	}
	
	public void Parse(String MipsInstruction, LinkedHashMap<String, Integer> reg, LinkedHashMap<String, Integer> labels)
	{

		
		String[] test1 = MipsInstruction.split(" ");
		inst = test1[0];

		/// combine all registers.
		
		String registers = "";
		for(int i = 1; i < test1.length; i++)
		{
			registers += test1[i];
		}
		
		//________________________________________________________________
		
		String[] arrreg = registers.split(",");
//________________ R-Type Instructions _______________________		
		if(inst.equals("add"))
	    {
	        type = 'r';
	        op = "000000";
	        func = "100000";

	    }

	    else if(inst.equals("sub"))
	    {
	        type='r';
	        op = "000000";
	        func = "100010";
	    }

	    else if(inst.equals("and"))
	    {
	        type='r';
	        op = "000000";
	        func = "100100";
	    }
	   

	    else if(inst.equals("or"))
	    {
	        type='r';
	        op = "000000";
	        func = "100101";
	    }

	    else if(inst.equals("sll"))
	    {
	        type='r';
	        op = "000000";
	        func = "000000";
	    }
	    else if(inst.equals("slt"))
	    {
	        type='r';
	        op = "000000";
	        func = "101010";
	    }
	   else if(inst.equals("jr"))
	    {
	        type='r';
	        op = "000000";
	        func = "001000";
	    }
	    
//________________ I-Type Instructions _______________________
	    else if(inst.equals("andi"))
	    {
	        type = 'i';
	        op = "001100";
	    }
	    else if(inst.equals("ori"))
	    {
	        type = 'i';
	        op = "001101";
	    }
	    else if(inst.equals("addi"))
	    {
	    	///addi $s3, $s3, 4
	        type = 'i';
	        op = "001000";
	    }
	    else if(inst.equals("lw"))
	    {
	        type = 'i';
	        op = "100011";

	    }
	    else if(inst.equals("sw"))
	    {
	        type = 'i';
	        op = "101011";

	    }
	    else if(inst.equals("beq"))
	    {
	        type = 'i';
	        op = "000100";

	    }
	    else if(inst.equals("bne"))
	    {
	        type = 'i';
	        op = "000101";

	    }
	    else if(inst.equals("slti"))
	    {
	        type = 'i';
	        op = "001010";
	    }
	    else if(inst.equals("lui"))
	    {
	        type = 'i';
	        op = "001111";
	    }
//________________ J-Type Instructions _______________________

	    else if(inst.equals("j"))
	    {
	        op = "000010";
	        type = 'j';
	    }
//____________________________________________________________  
	
		/// load registers.
		// add $s0, $s1, $s2
		/// opcode  rs    rt    rd   shamt  funct
		/// 000000|10001|10010|01000|00000|100000
		if(type == 'r')
		{
			if(inst.equals("jr"))
			{
				
				int pos = new ArrayList<String>(reg.keySet()).indexOf(test1[1]);

				src1 = GetBinaryValue(pos);
				src2 = "00000";
				dest = "00000";
				Shamt = "00000";

				
			}else if(inst.equals("sll"))
			{
				/// sll $t0,$s0,2
				int pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[0]);

				dest = GetBinaryValue(pos);
				pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[1]);
				
				src1 = "00000";
				src2 = GetBinaryValue(pos);
				Shamt = GetBinaryValue(Integer.parseInt(arrreg[2]));
			}
			else
			{
				/// sll $t0,$s0,2
				
				int pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[0]);

				dest = GetBinaryValue(pos);
				pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[1]);

				src1 = GetBinaryValue(pos);
				pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[2]);

				src2 = GetBinaryValue(pos);
				Shamt = "00000";
				
			}
			
		
		}else if(type == 'i')
		{
			if(inst.equals("addi") || inst.equals("andi") || inst.equals("ori") || inst.equals("slti"))
			{
				int pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[0]);
				dest = GetBinaryValue(pos);
				
				pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[1]);
				src1 = GetBinaryValue(pos);
				
				imm = Getimm(Integer.parseInt(arrreg[2]));

			}else if(inst.equals("lw") || inst.equals("sw"))
			{
				///   Example: lw $s1,32($s2)
				///   inst = lw
				//___________________________________________________________________
				
				/// Get the index of the destination register from map and then get 
				/// the binary code of this index.
				
				int pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[0]);
				dest = GetBinaryValue(pos);		/// destination = 10001 ($s1 = 17)
				
				
				/// split on the ( to get the offset and source register.
				
				String[] off = arrreg[1].split("\\("); 		/// 32 $s2)
				/// Get the binary value of the immediate in 16 bits.
				imm = Getimm(Integer.parseInt(off[0]));
				pos = new ArrayList<String>(reg.keySet()).indexOf(off[1].substring(0, 3));
				src1 = GetBinaryValue(pos);
				
			}else if(inst.equals("lui"))
			{
				/// lui $t0,50
				/// int = lui
				
				int pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[0]);
				dest = GetBinaryValue(pos); 	/// destination = 01000 ($t0 = 8)
				src1 = "-----";
				int temp = Integer.parseInt(arrreg[1]);
				imm = Getimm(temp); 			/// extend the number in 16 bit.
			}else if(inst.equals("beq") || inst.equals("bne"))
			{
				/// beq $s2, $s3, L1
				int pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[0]);
				src1 = GetBinaryValue(pos); 	///  
				pos = new ArrayList<String>(reg.keySet()).indexOf(arrreg[1]);
				dest = GetBinaryValue(pos);
				imm = Getimm(labels.get(arrreg[2]));
			}
			
		}
		else if(type == 'j')
		{
			/// j L1 
			imm = immForJump(labels.get(test1[1]));

		}
	}
	public static void main(String args[])
	{
		 
		
		

	}
}
