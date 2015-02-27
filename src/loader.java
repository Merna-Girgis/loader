import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
public class loader {
	public static void main (String args[]) throws IOException{
		FileOutputStream file = new FileOutputStream("DEV05.txt");
        DataOutputStream out= new DataOutputStream(file);
		String  read , length ,message;
		int count =1 , stAddress , address_d ,x=0 , h=0 , c=0;
		String st_address;
		boolean found = false;
		ArrayList<String> array = new ArrayList<String>();
		ArrayList<String> error_dub = new ArrayList<String>();
		ArrayList<String> error_h = new ArrayList<String>();
		ArrayList<String> error_d = new ArrayList<String>();
		ArrayList<String> error_r = new ArrayList<String>();
		ArrayList<String> error_cs = new ArrayList<String>();
		ArrayList<String> error_ref = new ArrayList<String>();
		ArrayList<String> error_notdef = new ArrayList<String>();
		 Set<String> set=new HashSet<String>();
		 Set<String>duplicates=new HashSet<String>();
		
		try{
			array.add("Control Section"+"   "+"Symbol Name"+"   "+"Address"+"   "+"Length");
			
			FileInputStream serverFile = new FileInputStream("DEVF3.txt");
			DataInputStream in = new DataInputStream(serverFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
            read = br.readLine();
            String [] messageparts = read.split(" ");
            st_address = messageparts[1];
           
            out:for(int i=1;i<=count;i++){
              for(int y=1;y<5;y++){
                	for(int z=1+y;z<5;z++){
                		if(messageparts[y].equals(messageparts[z]) && y!=h){
                			error_cs.add(messageparts[y]+" is a dublicate name of control section ");
                			break out;
                		}
                	}
                }
            	message = messageparts[i+1];
            FileInputStream File = new FileInputStream(message+".txt");
			DataInputStream input = new DataInputStream(File);
			BufferedReader brr = new BufferedReader(new InputStreamReader(input)); 
			message = brr.readLine();
            String header = message.substring(0,1);
            if(header.equals("H") || header.equals("h")){}
            else{ error_h.add("enter H or h at the first row ... incorrect header in "+messageparts[i+1]);break;}
			length = message.substring(15,19);
			array.add(messageparts[i+1]+"\t"+"\t"+"\t"+"\t"+st_address+"\t"+"  "+length);
			message = brr.readLine();
			String define = message.substring(0,1);
			if(define.equals("D") || header.equals("d")){}
            else{ error_d.add("enter D or d at the second row ... incorrect defintion in "+messageparts[i+1]);break;}
			stAddress = Integer.parseInt(st_address,16);
	
			    for(int z=0;z<message.length()-1;z+=12){ 
			    address_d = Integer.parseInt(message.substring(7+z,13+z),16);
			    array.add("\t"+"\t"+"  "+message.substring(1+z,7+z)+"\t"+Integer.toHexString((address_d+stAddress)));
			    error_dub.add(message.substring(1+z,7+z));
			    }
			st_address = Integer.toHexString(((stAddress+Integer.parseInt(length,16))));
            count++;
            
            message = brr.readLine();
            int z=0;
            while(z<message.trim().length()){
            	error_ref.add(message.substring(1+z,7+z));
            	z+=6;
            }
            c+=6;
            
			 String ref = message.substring(0,1);
			if(ref.equals("R") || header.equals("r")){}
            else{ error_r.add("enter R or r at the first row ... incorrect refrence in "+messageparts[i+1]);break;}	
           	
            }
            }catch(FileNotFoundException e){
				System.out.println("");
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("");
			}catch(NumberFormatException e){
				System.out.println("");
			}catch(StringIndexOutOfBoundsException e){
				System.out.println("");
			}
		
        for (int i=0;i<array.size();i++){
        out.writeBytes(array.get(i)+"\r\n");
        }
          
        //System.out.println(error_ref);
		for(String input:error_dub){
	           if(!set.add(input)){
	              duplicates.add(input);
	           }
	        }
		for(String item:duplicates){
           out.writeBytes(item +" is duplicated "+"\r\n");
        }
        
        for(int g=0;g<error_h.size();g++){
			out.writeBytes(error_h.get(g)+"\r\n");
	       }
        for(int g=0;g<error_d.size();g++){
			out.writeBytes(error_d.get(g)+"\r\n");
	       }
        for(int g=0;g<error_r.size();g++){
			out.writeBytes(error_r.get(g)+"\r\n");
	       }
        for(int g=0;g<error_cs.size();g++){
			out.writeBytes(error_cs.get(g)+"\r\n");
	       }
        
        for(int b=0;b<=error_ref.size()-1;b++){
            if(!error_dub.contains(error_ref.get(b)) ) {
                out.writeBytes(error_ref.get(b)+" is not defined "+"\r\n");
                
            }
        }
	}}

