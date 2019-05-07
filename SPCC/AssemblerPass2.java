public static void pass2()
{
  System.out.println("******Starting Pass 2******");
  try
  {
    String line,token,content,register;
    int i,reg=0,con=0,len=0,index=0;
    BufferedReader br = new BufferedReader(new FileReader("output.txt"));
		FileWriter fw=new FileWriter("final.txt");
    for(i=0;i<16;i++)
		  basetbl[i] = new Base("N",-1);
    line = br.readLine();
    while(line!=null)
    {
      line = line.trim();
      StringTokenizer st = new StringTokenizer(line," \t"); 
      String label="",mnemonic="",operand="",lcp="";
      int count = st.countTokens();
      if(count==3)
      {
        lcp=st.nextToken();
        label="";
        mnemonic=st.nextToken();
        operand = st.nextToken();
      }
      else if(count==4)
      {
        lcp=st.nextToken();
        label=st.nextToken();
        mnemonic=st.nextToken();
        operand = st.nextToken();
      }
      else if(count==2)
      {
        lcp=st.nextToken();
        mnemonic=st.nextToken();;
        operand=label="";
      }
      if(pot.contains(mnemonic))
      {
        if(mnemonic.equalsIgnoreCase("DC") )
        {
          while(lc%4 !=0) lc++;
          len = 4;
          if(operand.indexOf(',')==-1)
          {
            token = operand.substring(2,operand.length()-1);
            fw.write(lc+"\t"+token+"\n");
            fw.flush();
          }  
          else
          {
            String arr[] = operand.split(",");
					  token = arr[0].substring(2,arr[0].length());
            fw.write(lc+"\t"+token+"\n");
            fw.flush();
            for(i=1;i<arr.length;i++)
            {
              lc += len;
              token = arr[i];
              if(token.endsWith("'"))
              token = token.substring(0,token.length()-1);
              fw.write(lc+"\t"+token+"\n");
              fw.flush();
            }
          }
        }
        else if(mnemonic.equalsIgnoreCase("DS")) 
        {
          while(lc%4 !=0) lc++;
          if(operand.endsWith("F"))
          operand = operand.substring(0,operand.length()-1);
          len = 4*(Integer.parseInt(operand)); 
          fw.write(lc+"\n...\n...\n...\n");
          fw.flush();
        }
        else if(mnemonic.equalsIgnoreCase("USING") || mnemonic.equalsIgnoreCase("DROP")) 
        {
          String arr[] = operand.split(",");
          content = arr[0];
          register = arr[1];
          if(isDigit(register))
            reg = Integer.valueOf(register);
          else
          {
            for(SL s :sym)
            {
              if(s.name.equals(register))
                reg = s.value;
            }
          }
          if(isDigit(content))
            con = Integer.valueOf(content);
          else if(content.equals("*"))
              con = lc;
          else
          {
            for(SL s :sym)
              if(s.name.equals(register))
                con = s.value;
          }
          basetbl[reg].avl = (mnemonic.equals("USING")) ? "Y" : "N" ;
          basetbl[reg].content = (mnemonic.equals("USING")) ? con : -1 ;
        }
        else if(mnemonic.equalsIgnoreCase("START")) 
          lc = Integer.parseInt(operand);
        else if(mnemonic.equalsIgnoreCase("END") || mnemonic.equalsIgnoreCase("LTORG"))
        {
          if(mnemonic.equalsIgnoreCase("LTORG"))
          {
            while(lc%8 != 0)
            lc++;
          }
          for(SL l:lit)
          {
            if(l.value>=lc)
            {
              String temp = l.name;
              if(temp.startsWith("F'"))
                temp = temp.substring(2,temp.length()-1);
              else
              {
                int in = temp.indexOf('(');
                if(in>=0)
                {
                  temp = temp.substring(in+1,temp.indexOf(')'));
                  for(SL s : sym)
                  {
                    if(temp.equalsIgnoreCase(s.name))
                      temp = Integer.toString(s.value);
                  }
                }
              }
              fw.write(lc+"\t"+temp+"\n");
              fw.flush();
              lc += l.len;
            }
          }
        }
      }
      else if(checkMot(mot,mnemonic)!=-1)
      {
        int indexReg=0,in,fi,offset;
        boolean f;
        if(mnemonic.equals("BNE") || mnemonic.equals("BR"))
        {
          if(!isDigit(operand))
          {
            for(SL s : sym)
            {
              if(operand.equalsIgnoreCase(s.name))
              {
                if(s.ar == 'A')
                  operand = Integer.toString(s.value);
                else
                {
                  int n = nearest(s.value);
                  offset = s.value - basetbl[n].content;
                  operand = Integer.toString(offset)+"(0,"+Integer.toString(n)+")";
                }
              }
            }
          }
          if(mnemonic.equals("BNE"))
          {
            fw.write(lc+"\tBC\t7,"+operand+"\n");
            lc += 4;
          }
          else
          {
            fw.write(lc+"\tBCR\t15,"+operand+"\n");
            lc += 2;
          }
        }
        else
        {
          index = checkMot(mot,mnemonic);
          len = mot.elementAt(index).len;
          String arr[] = operand.split(",");
          String op1 = arr[0];
          String op2 = arr[1];
					String op3=new String();
					in = op2.indexOf('(');
          if((op2.charAt(0)!='=') && (in>=0))
          {
            fi = op2.indexOf(')');
            op3 = op2.substring(in+1,fi);
            op2 = op2.substring(0,in);
          }
          if(in<0)
            indexReg = 0;
          else
          {
            for(SL s : sym)
            {
              if(op3.equalsIgnoreCase(s.name))
                indexReg = s.value;
            }
          }
					if(isDigit(op1))
            fw.write(lc+"\t"+mnemonic+"\t"+op1+",");
          else
          {
            f = false;
            for(SL s : sym)
            {
              if(op1.equalsIgnoreCase(s.name))
              {
                if(s.ar == 'A')
                  fw.write(lc+"\t"+mnemonic+"\t"+s.value+",");
                else
                {
                  int n = nearest(s.value);
                  offset = s.value - basetbl[n].content;
                  fw.write(lc+"\t"+mnemonic+"\t"+offset+"("+indexReg+","+n+"),");
                }
              }
            }
            if(!f)
            {
              for(SL s : lit)
              {
                if(op1.equalsIgnoreCase(s.name))
                {
                  if(s.ar == 'A')
                    fw.write(s.value+"\n");
                  else
                  {
                    int n = nearest(s.value);
                    offset = s.value - basetbl[n].content;
                    fw.write(offset+"("+indexReg+","+n+")"+"\n");
                  }
                }
              }
            }
          }
					if(isDigit(op2))
						fw.write(op2+"\n");
          else
          {
            f = false;
            for(SL s : sym)
            {
              if(op2.equalsIgnoreCase(s.name))
              {
                f = true;
                if(s.ar == 'A')
                  fw.write(s.value+"\n");
                  // fw.flush();
                else
                {
                  int n = nearest(s.value);
                  offset = s.value - basetbl[n].content;
                  fw.write(offset+"("+indexReg+","+n+")"+"\n");
                  fw.flush();
                }
              }
            }
            if(!f)
            {
              for(SL s : lit)
              {
                if(op2.equalsIgnoreCase(s.name))
                {
                  if(s.ar == 'A')
                  fw.write(s.value+"\n");
                }
                else
                {
                  int n = nearest(s.value);
                  offset = s.value - basetbl[n].content;
                  fw.write(offset+"("+indexReg+","+n+")"+"\n");
                }
              }
            }
          }
        }
        len = mot.elementAt(index).len;
      }
      if(!mnemonic.equals("LTORG"))
      lc+=len;
      line = br.readLine();
    }
    fw.close();
    br.close();
  }
  catch(Exception e)
  {
			e.printStackTrace();
	}
  System.out.println("\nBase Table:\n");
  System.out.println("Reg\t| Cont |\tAvl");
  for(int i=0;i<16;i++)
  {
    Base entry = basetbl[i];
    if(entry.content!=-1)
    System.out.println(i+"\t|\t"+entry.content+"\t|\t"+entry.avl);
  }

}