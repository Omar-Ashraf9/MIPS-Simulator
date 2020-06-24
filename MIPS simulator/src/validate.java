import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Vector;

public class validate 
{
	// TODO Auto-generated method stub
			
	public static void checkForErrors(String[] Instruction,LinkedHashMap<String, Integer> labels,Vector<String> errors)
	{
		
		
		for(int i = 0; i < Instruction.length; i++)
		{
			Instruction[i].trim();
			String[] commands = Instruction[i].split("[\\s,]+");   
			
			if(commands.length == 4) 
			{
				if(commands[0].matches("add?|sub?|and?|or?|slt?")&&
						commands[1].matches("\\$((s[0-7])|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)")&&
						commands[2].matches("\\$((s[0-7])|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)")&&
						commands[3].matches("\\$((s[0-7])|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)"))
				
				{
					
					
					
					
				}else if(commands[0].matches("sll?|addi?|andi?|ori?|slti?|beq?|bne?")&&
						!commands[0].matches("^add?|^and?|^or?|^slt?")&&
						 commands[1].matches("\\$((s[0-7])|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)")&&
						 commands[2].matches("\\$((s[0-7])|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)")&&
						 commands[3].matches("\\w+|[0-9]+")) 
				{
					
					if(commands[0].matches("beq?|bne?" )&& commands[3].matches("\\w+"))
					{
						System.out.println("Valid input");
						
						/***Get the Key of the label***/
						if(labels.containsKey(commands[3])) 
						{
							
							
						}else 
						{
							errors.add("Error at line: " + (i + 1));
						}
						///----------------------------

					}
					else if(commands[0].matches("addi?|andi?|ori?|slti?|sll?" ) && commands[3].matches("[0-9]+")) 
					{
					}
					else
					{
						errors.add("Error at line: " + (i + 1));
					}
						
					
				}
				else 
				{
					errors.add("Error at line: " + (i + 1));

				}
			}
//___________________________________________________________________________________________________________________________________
			//lw $s1,32($at)
			else if(commands.length == 3)
			{
				if(commands[0].matches("lui?|lw?|sw?")&&
					commands[1].matches("\\$((s[0-7])|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)") &&
					commands[2].matches("([0-9]+)?|[0-9]*\\(\\$(s[0-7]|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)\\)"))
				{
					
				}
				else
				{
					errors.add("Error at line: " + (i + 1));
				}
				
			}
			// j label
			else if(commands.length == 2)
			{
				if(commands[0].matches("jr")&&commands[1].matches("\\$((s[0-7])|0|v[01]|a[0-3]|t[0-9]|ra|at|k[01]|gp|sp|fp)"))
				{
					
				}
				else if(commands[0].matches("j")&& commands[1].matches("\\w+"))
				{
					
					/***Get the Key of the label***/
					if(labels.containsKey(commands[1])) 
					{
						
					}else 
					{
						errors.add("Error at line: " + (i + 1));
					}
					///----------------------------
					
				}else
				{
					errors.add("Error at line: " + (i + 1));
				}
			}	
			else if(commands.length == 1) 
	    	{
				if(Instruction[i].matches("\\w+:"))
				{
					
				}else 
				{
					errors.add("Error at line: " + (i + 1));
				}
	    	}
			else
			{
				errors.add("Error at line: " + (i + 1));
			}
		
			
		}
		
		
	}
}

	