import java.util.*;
import java.io.*;

class prod{
	char left;
	String right;	
	prod(char left,String right){
		this.left=left;
		this.right=right;
	}
}
class Fff{
	static Vector<Character>followT=new Vector<Character>();
	static 	Vector<prod> vprod=new Vector<prod>();
	static Vector<Character>firstT=new Vector<Character>();
	static Vector<Vector<Character>>follow_set;	
	static Vector<Vector<Character>>firstSet;
	static Vector<Character> nonterm=new Vector<Character>();
	public static void main(String[] args) {
			
	char c;
	try{
		 File file = new File("finput.txt");
		 BufferedReader br = new BufferedReader(new FileReader(file)); 
		 String st=new String();
		 while((st=br.readLine())!=null){
		 	c=st.charAt(0);
		 	nonterm.add(c);
		 	String sp[]=st.split("/");
		 	for(int i=1;i<sp.length;i++){
		 		vprod.add(new prod(c,sp[i]));
		 	}
		 	String sp1[]=sp[0].split("->");
		 	vprod.add(new prod(c,sp1[1]));
		 }		 
	   }catch(Exception e){}
	   follow_set=new Vector<Vector<Character>>(); 
	   firstSet=new Vector<Vector<Character>>();  	 
	   for(char c1: nonterm){
	   	   firstT.add(c1);
	   	   findFirst(c1);
	   	   Vector<Character> first1=new Vector<>(firstT);	   	   
	   	   firstSet.add(first1);
	   	   firstT.clear();
	   }

	   for(char c1: nonterm){
	   	   followT.add(c1);
	   	   findFollow(c1);
	   	   Vector<Character> follow=new Vector<>(followT);	   	   
	   	   follow_set.add(follow);
	   	   followT.clear();
	   }

	   System.out.println("First");
	   for(Vector<Character> v:firstSet){
	   	  System.out.println(v);
	   }
	   System.out.println("Follow");
	   for(Vector<Character> v:follow_set){
	   	  System.out.println(v);
	   }
	}
	static void findFirst(char c){
		char rhs;
		for(prod p:vprod){
			int i=0;
			if(p.left==c){
				rhs=p.right.charAt(i);
				if(!nonterm.contains(rhs)&&!firstT.contains(rhs)){
					firstT.add(rhs);
				}else{
					findFirst(rhs);
					while(firstT.contains('#')&&p.right.length()>(++i)){
						rhs=p.right.charAt(i);
						if(!nonterm.contains(rhs)&&!firstT.contains(rhs)){
							firstT.add(rhs);
							firstT.remove((Character)'#');
						}else{
							findFirst(rhs);
						}
					}
				}
			}
		}
	}
	static void findFollow(char c){
		if(nonterm.indexOf(c)==0)
			followT.add('$');
		for(prod p:vprod){
			int pos=p.right.indexOf(c);
			if(pos>-1&&p.right.length()>(++pos)){
				char sym=p.right.charAt(pos);
				if(!nonterm.contains(sym)&&!followT.contains(sym))
					followT.add(sym);
				else{
					do{
						for(Vector<Character> v:firstSet)
							if(v.get(0)==sym)
								for(int i=1;i<v.size();i++)
									if(!followT.contains(v.get(i)))
										followT.add(v.get(i));
						
						pos++;													
					}while(p.right.length()>pos&&followT.contains('#'));

					if(pos==p.right.length())
						for(Vector<Character> v:follow_set)
							if(v.get(0)==p.left)
								for(int i=1;i<v.size();i++)
									if(!followT.contains(v.get(i)))
										followT.add(v.get(i));							
				}
			}else if(pos>-1){
				for(Vector<Character> v:follow_set)
					if(v.get(0)==p.left)
						for(int i=1;i<v.size();i++)
							if(!followT.contains(v.get(i)))
								followT.add(v.get(i));							
			}
		}
		followT.removeAll(Collections.singleton('#'));
	}	
}
