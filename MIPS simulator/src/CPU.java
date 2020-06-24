import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CPU 
{
	public static int Execute(Parser p, LinkedHashMap<String, Integer> map, int PC, LinkedHashMap<String, Integer> labels, LinkedHashMap<String, Integer> mem)
	{
		int indexOfSrc1 = 0;
		int indexOfSrc2 = 0;
		int indexOfDest = 0;
		
	
		int valueOfSrc1 = 0;
		int valueOfSrc2 = 0;
		int valueOfDest = 0;
		
		int valueOfShamt = 0;
		
		
		String key =  "";
//________________ R-Type Instructions _______________________		

		if(p.type == 'r')
		{
			if(p.inst.equals("add"))
			{

				// 1- get the addresses of the source and destination.
				// 2- convert them to decimal to give the index of the register in our map.
				indexOfSrc1 = Integer.parseInt(p.src1,2);
				indexOfSrc2 = Integer.parseInt(p.src2,2);
				indexOfDest = Integer.parseInt(p.dest,2);
				
				// 3- get the value of each register by the index in step 2.
				// 4- make the operation.
				valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
				valueOfSrc2 = (new ArrayList<Integer>(map.values())).get(indexOfSrc2);
				valueOfDest = valueOfSrc1 + valueOfSrc2;
				
				// 5- put the result of step 4 as value of the destination address by the index in step 5.
				key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
				map.replace(key, valueOfDest);
			}
			else if(p.inst.equals("sub"))
			{
				// 1- get the addresses of the source and destination.
				// 2- convert them to decimal to give the index of the register in our map.
				indexOfSrc1 = Integer.parseInt(p.src1,2);
				indexOfSrc2 = Integer.parseInt(p.src2,2);
				indexOfDest = Integer.parseInt(p.dest,2);
				
				// 3- get the value of each register by the index in step 2.
				// 4- make the operation.
				valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
				valueOfSrc2 = (new ArrayList<Integer>(map.values())).get(indexOfSrc2);
				valueOfDest = valueOfSrc1 - valueOfSrc2;
				
				// 5- put the result of step 4 as value of the destination address by the index in step 5.
				key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
				map.replace(key, valueOfDest);
			}
			else if(p.inst.equals("and"))
			{
				// 1- get the addresses of the source and destination.
				// 2- convert them to decimal to give the index of the register in our map.
				indexOfSrc1 = Integer.parseInt(p.src1,2);
				indexOfSrc2 = Integer.parseInt(p.src2,2);
				indexOfDest = Integer.parseInt(p.dest,2);
				
				// 3- get the value of each register by the index in step 2.
				// 4- make the operation.
				valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
				valueOfSrc2 = (new ArrayList<Integer>(map.values())).get(indexOfSrc2);
				valueOfDest = valueOfSrc1 & valueOfSrc2;
				
				// 5- put the result of step 4 as value of the destination address by the index in step 5.
				key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
				map.replace(key, valueOfDest);
			}
			else if(p.inst.equals("or"))
			{
				// 1- get the addresses of the source and destination.
				// 2- convert them to decimal to give the index of the register in our map.
				indexOfSrc1 = Integer.parseInt(p.src1,2);
				indexOfSrc2 = Integer.parseInt(p.src2,2);
				indexOfDest = Integer.parseInt(p.dest,2);
				
				// 3- get the value of each register by the index in step 2.
				// 4- make the operation.
				valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
				valueOfSrc2 = (new ArrayList<Integer>(map.values())).get(indexOfSrc2);
				valueOfDest = valueOfSrc1 | valueOfSrc2;
				
				// 5- put the result of step 4 as value of the destination address by the index in step 5.
				key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
				map.replace(key, valueOfDest);
			}
			else if(p.inst.equals("sll"))
			{			
				/// sll $t0,$s0,2.
				
				// 1- get the addresses of the source and destination.
				// 2- convert them to decimal to give the index of the register in our map.
				indexOfSrc2 = Integer.parseInt(p.src2,2);
				indexOfDest = Integer.parseInt(p.dest,2);
				
				// 3- get the value of each register by the index in step 2.
				// 4- make the operation.
				valueOfSrc2 = (new ArrayList<Integer>(map.values())).get(indexOfSrc2);
				valueOfShamt = Integer.parseInt(p.Shamt, 2);
				
				valueOfDest = valueOfSrc2 << 2;
				// 5- put the result of step 4 as value of the destination address by the index in step 5.
				key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
				map.replace(key, valueOfDest);
			}
			else if(p.inst.equals("slt"))
			{
				// slt $t0,$s0,$s1
				
				// 1- get the addresses of the source and destination.
				// 2- convert them to decimal to give the index of the register in our map.
				indexOfSrc1 = Integer.parseInt(p.src1,2);
				indexOfSrc2 = Integer.parseInt(p.src2,2);
				indexOfDest = Integer.parseInt(p.dest,2);
				
				// 3- get the value of each register by the index in step 2.
				// 4- make the operation.
				valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
				valueOfSrc2 = (new ArrayList<Integer>(map.values())).get(indexOfSrc2);
				
				if(valueOfSrc1 < valueOfSrc2)
				{
					valueOfDest = 1;
				}else
				{
					valueOfDest = 0;

				}
				
				// 5- put the result of step 4 as value of the destination address by the index in step 5.
				key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
				map.replace(key, valueOfDest);
				
			}
			else if(p.inst.equals("jr"))
			{
				// jr $s0
				indexOfSrc1 = Integer.parseInt(p.src1, 2);
				valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
				
				key = "0x0000" + Integer.toHexString(valueOfSrc1);
				PC = mem.get(key); 
				/// Because the PC start from 0 and the line numbers start from 1 so we subtract one from the pc to be the same
				PC--;
			}
		}
//________________ I-Type Instructions _______________________		

		else if(p.type == 'i')
		{
			    if(p.inst.equals("andi"))
			    {
			    	indexOfSrc1 = Integer.parseInt(p.src1,2);
					indexOfDest = Integer.parseInt(p.dest,2);
					
					// 3- get the value of each register by the index in step 2.
					// 4- make the operation.
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					
					valueOfDest = valueOfSrc1 & Integer.parseInt(p.imm, 2);
					
					// 5- put the result of step 4 as value of the destination address by the index in step 5.
					key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
					map.replace(key, valueOfDest);
			    }
			    else if(p.inst.equals("ori"))
			    {
			    	indexOfSrc1 = Integer.parseInt(p.src1,2);
					indexOfDest = Integer.parseInt(p.dest,2);
					
					// 3- get the value of each register by the index in step 2.
					// 4- make the operation.
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					
					valueOfDest = valueOfSrc1 | Integer.parseInt(p.imm, 2);
					
					// 5- put the result of step 4 as value of the destination address by the index in step 5.
					key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
					map.replace(key, valueOfDest);
			    }
			    else if(p.inst.equals("addi"))
			    {
			    	//addi $t, $s, imm
			    	indexOfSrc1 = Integer.parseInt(p.src1,2);
					indexOfDest = Integer.parseInt(p.dest,2);
					
					// 3- get the value of each register by the index in step 2.
					// 4- make the operation.
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					
					valueOfDest = valueOfSrc1 + Integer.parseInt(p.imm, 2);
					
					// 5- put the result of step 4 as value of the destination address by the index in step 5.
					key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
					map.replace(key, valueOfDest);
			    	
			    }
			    else if(p.inst.equals("lw"))
			    {
			       // lw $to,52($at)
			    	
			    	indexOfSrc1 = Integer.parseInt(p.src1,2);
					indexOfDest = Integer.parseInt(p.dest,2);
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					valueOfSrc1 +=  Integer.parseInt(p.imm, 2);
					
					key = "0x0000" + Integer.toHexString(valueOfSrc1);
					valueOfDest = mem.get(key);
					key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
					map.replace(key, valueOfDest);
			    }
			    else if(p.inst.equals("sw"))
			    {
			    	// sw $to,52($at)
			    	
			    	indexOfSrc1 = Integer.parseInt(p.src1,2); 		//at
			    	
					indexOfDest = Integer.parseInt(p.dest,2);		// t0

					valueOfDest = (new ArrayList<Integer>(map.values())).get(indexOfDest);
					
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					valueOfSrc1 +=  Integer.parseInt(p.imm, 2);
					
					key = "0x0000" + Integer.toHexString(valueOfSrc1);
					mem.replace(key, valueOfDest);
					
			    }
			    else if(p.inst.equals("beq"))
			    {
			       /// beq $s2, $s3, L1
			    	indexOfSrc1 = Integer.parseInt(p.src1,2); 		
					indexOfDest = Integer.parseInt(p.dest,2);
					
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					valueOfDest = (new ArrayList<Integer>(map.values())).get(indexOfDest);
					
					if(valueOfSrc1 == valueOfDest)
					{
						PC = Integer.parseInt(p.imm, 2);
					}
			    	
			    	
			    }
			    else if(p.inst.equals("bne"))
			    {
			    	indexOfSrc1 = Integer.parseInt(p.src1,2); 		
					indexOfDest = Integer.parseInt(p.dest,2);
					
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					valueOfDest = (new ArrayList<Integer>(map.values())).get(indexOfDest);
					
					if(valueOfSrc1 != valueOfDest)
					{
						PC = Integer.parseInt(p.imm, 2);
					}
			    }
			    else if(p.inst.equals("slti"))
			    {
			    	indexOfSrc1 = Integer.parseInt(p.src1,2);
					indexOfDest = Integer.parseInt(p.dest,2);
					
					// 3- get the value of each register by the index in step 2.
					// 4- make the operation.
					valueOfSrc1 = (new ArrayList<Integer>(map.values())).get(indexOfSrc1);
					
					if(valueOfSrc1 < Integer.parseInt(p.imm, 2))
					{
						valueOfDest = 1;
					}else
					{
						valueOfDest = 0;
					}
					
					// 5- put the result of step 4 as value of the destination address by the index in step 5.
					key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
					map.replace(key, valueOfDest);
			    }
			    else if(p.inst.equals("lui"))
			    {
			        /// lui $t, imm
			    	indexOfDest = Integer.parseInt(p.dest,2);
			    	key =  new ArrayList<String>(map.keySet()).get(indexOfDest);
			    	valueOfShamt = Integer.parseInt(p.imm, 2);
			    	map.replace(key, valueOfShamt << 16);
			    	
			    }

		}
		else if(p.type == 'j')
		{
			if(p.inst.equals("j"))
			{
				 /// j L1.
				PC = Integer.parseInt(p.imm, 2); 
			}
		}
		return PC;
	}
	

}
