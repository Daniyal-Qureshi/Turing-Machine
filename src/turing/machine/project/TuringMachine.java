
package turing.machine.project;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TuringMachine {

	
	static String startState;
	static ArrayList<String> fileData;
	static ArrayList<String> acceptStates,states;
	static ArrayList<Transition> transitions;
	
	
	public TuringMachine() {
		
		
		//intialization
		acceptStates=new ArrayList<String>();
		transitions=new ArrayList<Transition>();
		fileData=new ArrayList<String>();
		FileOpener();
		
		
		//start states and accept states
		
		startState=fileData.get(0);
		acceptStates=new ArrayList<String>();
		String[] temp=fileData.get(1).split(" ");
		
	
		
		
	
		for (String string : temp) 
			acceptStates.add(string);
	
		
                
                
                                 
                                  
		SetTransitions();
		         
                                
                
		states=states();
		DeterminsticValidation();
		
	
	
	
		int rs=JOptionPane.YES_OPTION;	
		//Looping for User's choice to run again and again or to stop
		while(rs==JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(null,inputAlphabet().toString(), "Input Alphabet",JOptionPane.INFORMATION_MESSAGE);
			String input=JOptionPane.showInputDialog("Enter Input String");
			
			while(!InputValidation(input))
			 input=JOptionPane.showInputDialog("Please Enter a valid String");
					
			if(Run(input.toCharArray()))
				JOptionPane.showMessageDialog(null, input+" is Accepted \n Tape : "+Tape.cells,"Acceptance",JOptionPane.INFORMATION_MESSAGE);
		
			
			else
				JOptionPane.showMessageDialog(null, input+" is Rejected  \n Tape  : "+Tape.cells,"Rejection",JOptionPane.ERROR_MESSAGE);
			
			
			rs=JOptionPane.showConfirmDialog(null,"Yes: Continue with same TM"
					+ "\nNo: Change TM \n Cancel: Exit  "
					
					);
			
			if(rs==JOptionPane.YES_OPTION) continue;
			
			
			else if(rs==JOptionPane.NO_OPTION) {
				new  TuringMachine();
				break;
			}
			
			else if(rs==JOptionPane.CANCEL_OPTION)	
			System.exit(0);
		
	   }

	
	

	
	
}
	
	
	
	
	public static void main(String[] args) {
		try {
		new TuringMachine();
		}
		
		catch (Exception e) {
		
		//uncomment below statement for resolving undocumented exceptions
		//e.printStackTrace();	
		
		
		}
		
		
		
	}
	
	
	public static void FileOpener() {
//==========================Complexity 
//   Linear O(n)		
		Reader reader=null;
		BufferedReader bufrReader=null;
		
		JFileChooser chooser=new JFileChooser("TM Configuration");
		FileNameExtensionFilter filter=new FileNameExtensionFilter("text document", "txt");
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		if(chooser.showOpenDialog(chooser)==JFileChooser.APPROVE_OPTION){
			try
			{			
				
			 reader=new FileReader(new File(chooser.getSelectedFile().getAbsolutePath()));
			 bufrReader=new BufferedReader(reader);
			String string="";
			
			
			
			while((string=bufrReader.readLine())!=null&&string.length()!=0) 
				
				
				fileData.add(string);
			
			
		
			}
			catch (Exception e) {
				
				e.printStackTrace();
				
	
				System.out.println("Invalid Choice");	
			}
			
			finally {
				
				try {
				if(reader!=null)
					reader.close();
				if(bufrReader!=null)
					bufrReader.close();
				}
				
				catch (Exception e) {}
				
			}
			
			
		
		}
			
	}
		
	
	
	
	
	
	public static ArrayList<Character> inputAlphabet(){
//==========================Complexity 
//   Quadratic binary loops  O(n^2) 

		ArrayList<Character> alphabetlist=new ArrayList<Character>();
                                
                                    String[] array=fileData.get(3).split(" ");
                                            
                                for(int i=0;i<array.length;i++)
                                    alphabetlist.add(array[i].charAt(0));

                                
		return alphabetlist;  		
	}
	
	public static ArrayList<Character> tapeAlphabet(){
		
//=======Complexity
		
//=======Linear time O(n)		
		
		
		ArrayList<Character> tapelist=new ArrayList<Character>();
		String string="";
		for(int i=4;i<fileData.size();i++){
                                    Pattern pattern=Pattern.compile("\\s(.+)\\s");
                                    Matcher matcher=pattern.matcher(fileData.get(i).toString());
                                        
                                        
                                    if(matcher.find()){
                                        string=matcher.group();
                                        string=string.trim();
                                        string=string.replace("(", "");
                                        string=string.replace(")", "");
                                        string=string.replace("L", "");
                                        string=string.replace("R", "");
                                        string=string.replace(",", "");
                                        string=string.replace(" ", ""); 
                                        for (int j = 0; j < string.length(); j++) 
                                            if(!inputAlphabet().contains(string.charAt(j))&&!tapelist.contains(string.charAt(j)))
                                                  tapelist.add(string.charAt(j));
                                                     
                                                                          
                                    }

                                 }
		
                
                                return tapelist;
		
	}
	
		
	
	public static ArrayList<String> states(){
		
		//=======Complexity
				
		//=======Linear time  single loop O(n)				
				
				
				
		ArrayList<String> statesList=new ArrayList<String>();
				
		
		String[] line;
		
			for (int i = 0; i <fileData.size(); i++) {
				line=fileData.get(i).split(" ");
				
				
				if(line.length==3)
					{
						
						if(!statesList.contains(line[0]))
							statesList.add(line[0]);
						
						if(!statesList.contains(line[line.length-1]))
							statesList.add(line[line.length-1]);

					}
							
			}

			return statesList;
		
		}
		
	
	
	
	public static void  SetTransitions() {
		//=======Complexity
		
		//=======Linear time O(n) depends on growing of dataset		
		
		
		
		Transition transition=new Transition();
		
		String[] line= {};
		String string="";
		
	
		for (int i = 4; i <fileData.size(); i++)  {
			transition=new Transition();
			line=fileData.get(i).split(" ");
			
	
			transition.FromState=line[0];
			transition.ToState=line[line.length-1];
			Pattern pattern=Pattern.compile("\\s(.+)\\s");
			Matcher matcher=pattern.matcher(fileData.get(i).toString());
			
			
			if(matcher.find()) {	
			string=matcher.group();
			string =string.trim();
			string=string.replace(")", "");
			string=string.replace("(", "");
			
			line=string.split(",");
                        
			if(line[0].equals(" "))
                                                            line[0]="B";
                                                                
                                                if(line[1].equals(" "))
                                                            line[1]="B";
                                                                
                                                         
                        
                        
			transition.inputSymbol=line[0].charAt(0);
			transition.writeSymbol=line[1].charAt(0);
			transition.direction=line[2].charAt(0);
                                               if(line[0]=="B"||line[1]=="B"){
			
                                                 String change=transition.FromState+" "+"("+transition.inputSymbol+","+transition.writeSymbol+","+transition.direction+") "+transition.ToState;
                                                  fileData.set(i, change);
                                                }

			transitions.add(transition);
		}
		
		
	   }
		
	}	
	
	
public static void DeterminsticValidation() {
	//=======Complexity
	
	//=======Linear time O(n) single loop		
		
		String firstState,secondState;
		char firstinput,secondinput;
		
		for (int i = 4; i < fileData.size() ;i++) {
                                               firstState=fileData.get(i).split(" ")[0];
			firstinput=fileData.get(i).split(" ")[1].charAt(1);
			
			for (int j = i+1; j < fileData.size(); j++) {
				secondState=fileData.get(j).split(" ")[0];
				secondinput=fileData.get(j).split(" ")[1].charAt(1);
				if(firstState.equals(secondState)&&firstinput==secondinput) {
				JOptionPane.showMessageDialog(null,fileData.get(i)+"\n"+fileData.get(j), "Non Determinstic Transition Found",JOptionPane.ERROR_MESSAGE);
				new TuringMachine();
				return;			
				}
				
			}
	
		}
		

	}
	

	
	public static boolean InputValidation(String input) {
		
		//=======Complexity
		
		//=======Linear time O(n)		
		try {
		char[] array=input.toCharArray();
		for (int i = 0; i < array.length; i++) 
			if(!inputAlphabet().contains(array[i]))
				return false;
		
		
		}
		catch (Exception e) {}
		
					
		return true;
		
	}

	
	
	public static boolean Run(char[] input){
		//=======Complexity
		
		//=======Quadratic time O(n^2)		
			
			String currentstate=fileData.get(0);
			
			new Tape();
			
			
			Tape.cells.add('B');
			for (int i = 0; i < input.length; i++) 
				Tape.cells.add(input[i]);
	
			
			Tape.cells.add('B');
			Tape.headPosition=1;
				
			boolean flag=true;
			
			while (true) {	
				flag=true;
				for (int i = 0; i < transitions.size(); i++) {
					if(transitions.get(i).FromState.equals(currentstate)&&
					transitions.get(i).inputSymbol==Tape.cells.get(Tape.headPosition)) {
						flag=false;
						if(transitions.get(i).inputSymbol=='B'&&transitions.get(i).writeSymbol!='B')		
						{
								
							if(transitions.get(i).direction=='R') 
								Tape.cells.add(0,'B');	
							else 
								Tape.cells.add('B');
						}		
										
						Tape.cells.set(Tape.headPosition, transitions.get(i).writeSymbol);
						currentstate=transitions.get(i).ToState;
					
						if(transitions.get(i).direction=='R')
							Tape.headPosition+=1;
						else 
							Tape.headPosition-=1;
							
					}		
	
				}
				
				if(flag)
					break;
							
			}
			
	
                        
                               if(acceptStates.contains(currentstate))
			return true;
			
		else 
			return false;
			
		
		
			
	}
		
	
	

}


