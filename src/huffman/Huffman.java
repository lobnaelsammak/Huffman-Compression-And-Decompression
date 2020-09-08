/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author lobna
 */
public class Huffman {

    /**
     */
   public ArrayList<Node> list ;
   public PriorityQueue<Node> priorityqueue;
   public ArrayList<String> characters;
   public ArrayList<Integer> frequencies;
   public byte[] input,output;
   public String out,in,encodedBinary;
   public Integer extrazeros;
   
    
    public Huffman()
    {
        this.list = new ArrayList<>();
        this.frequencies = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.priorityqueue= new PriorityQueue<>();
        //this.input = new byte[];
        this.in = new String();
        this.out=new String();
        
        
    }
   //---------------------------------------------COMPRESSION METHODS-------------------------------------------------------//
    public String getinput(String input ) throws FileNotFoundException, IOException {
        //BufferedReader fis = new BufferedReader(new FileReader(input));
       byte[] inn2 = Files.readAllBytes(Paths.get(input));
       String s = new String(inn2);
       out =s;
       return s;
    
       
    }
    
    /**
     *
     * @param input
     */
 
    public void getchar(String input)
    {
        int i,index ; 
        for(i=0;i<input.length();i++)
        {
            /*if (" ".equals(String.valueOf(input.charAt(i))));
            {
                continue;
            }*/
            if(!characters.contains(String.valueOf(input.charAt(i))))
            { 
                    characters.add(String.valueOf(input.charAt(i)));
            frequencies.add(1);}
            
            else{
                    
                 frequencies.set(characters.indexOf(String.valueOf(input.charAt(i))), frequencies.get(characters.indexOf(String.valueOf(input.charAt(i))))+1);
            }
        
        }
        
        
    
    }
    public void nodeList()
    {
        int i ; 
        for(i=0;i<frequencies.size();i++)
        {
        list.add(new Node(characters.get(i),frequencies.get(i)));
        
        
        }
    
    }
   
    /**
     *
     * @return
     */
    public Node priorityqueueandlist()
   {   int i, sum; 
   

   
        for(i=0;i<list.size();i++) //CREATING A LEAF NODE FOR EACH CHARACTER (LEFT AND RIGHT ARE SET TO NULL)
        {
            priorityqueue.add(list.get(i));
        
        }
        
        while(priorityqueue.size()>1)
        {
           Node n1= priorityqueue.remove();
           Node n2=priorityqueue.remove();
           
           Node internal = new Node("",n1.frequency+n2.frequency);
           internal.left=n1;
           internal.right=n2;
           priorityqueue.add(internal);
        
        }
        return priorityqueue.remove();
        
   }
   
   public void getCode(Node node,String s)
   {
       int i;
       
    
      if (node != null) {
            if (node.right != null)
                getCode(node.right, s + "1");

            if (node.left != null)
                getCode(node.left, s + "0");

            if (node.left == null && node.right == null)
            {
                node.code=s;
                list.set(list.indexOf(node), node);
         try{   
            out=out.replaceAll(String.valueOf(node.letter), s);
       }
       catch(
           PatternSyntaxException e
       ){
       out=out.replaceAll("\\"+String.valueOf(node.letter), s);
       
       }
            
            }
        }
    }
      
   
   
   
   
   public void outputFile() throws FileNotFoundException, IOException //NEED TO WRITE RATIO AND CODING TABLE !!!(CAN USE ALTERNATIVE METHOD, READ INPUT AND SWITCH TO CODE)
   {
       FileWriter fw = new FileWriter("output.txt");
       PrintWriter pw = new PrintWriter(fw);
       String s = "Character Frequency Code";
       //pw.print(s);
      int i;
       for(i=0;i<list.size();i++)
       {
       s+=list.get(i).letter +" "+list.get(i).frequency+" "+list.get(i).code+"\n";
       
       }
       pw.print(s);
       String encodedString = "";
       int extraZeros;
        char nextChar;
        //System.out.println(temp2);
for( i = 0; i <= out.length()-8; i += 8) { 
     nextChar = (char)Integer.parseInt(out.substring(i, i+8), 2);
     encodedString += nextChar;
}



 extraZeros=8-out.substring(i).length();
String temp3 =  out.substring(i)  ;
for(int j=0;j<extraZeros;j++)
{          temp3+="0";   

}
    encodedString+=(char)Integer.parseInt(temp3,2);
    pw.print("extra zeros = "+extraZeros);
    pw.print("\n");
    pw.print("encoded string");
    pw.print(encodedString);
    
    
  
//pw.write(extraZeros+"\n"+temp);
   pw.close();
   //System.out.println(encodedString);
   }
   //---------------------------------------------------------------------------------------------------------------------//
   
   
   //--------------------------------------------DECOMPRESSION METHODS----------------------------------------------------//
   public void readDecompress(String input) throws FileNotFoundException, IOException
   {
        String[] s = input.split("\n");
        String[] temp;
        int i=0 , j=0;
        if(!"Character Frequency Code".equals(s[0]))
                System.out.println("unsupported format -MISSING HEADER-");
        
        else
        {
          while(!s[i].contains("extra zeros = "))  
          {
              temp=s[i].split(" ");
               Node n = new Node(s[0],Integer.valueOf(s[1]));
               n.code=s[2];
               list.add(n);
               i++;
          
          }
          temp=s[i].split(" ");
          extrazeros=Integer.valueOf(s[3]);
          i++;
          in=s[i]; //ENCODED STRING
        
        
        }
       
      
      }
      
   public void getencoded()
   {
       in=in.substring(0, in.length()-1);
       encodedBinary="";
      for (int i = 0; i < in.length(); i++) { 
          
             
                    char c = in.charAt(i);

                    String cbinary = Integer.toBinaryString((int)c);
                while(cbinary.length()<8)
                    {cbinary="0"+cbinary;

                            }
                    encodedBinary += cbinary;

                          } 
       
      encodedBinary=encodedBinary.substring(0, encodedBinary.length()-extrazeros);
      
      
   }
   
   public void decompress(Node root)
   {
       String ans = ""; 
    Node current = root;
    for (int i=0;i<encodedBinary.length();i++) 
    { 
        if ("0".equals(encodedBinary.charAt(i))) 
           current = current.left; 
        else
           current = current.right; 
  
        // reached leaf node 
        if (current.left==null && current.right==null) 
        { 
            ans += current.letter; 
            current = root; 
        } 
    } 
   
     ans+='\0'; 
     out = ans;
   
   }
   public void writetofiledecom() throws IOException
   {
       FileWriter fw = new FileWriter("output.txt");
       try (PrintWriter pw = new PrintWriter(fw)) {
           pw.print(out);
       }
   
   
   
   }
   //--------------------------------------------------------------------------------------------------------------------//
    public static void main(String[] args) throws IOException {
                Huffman instance = new Huffman();
               
		Scanner scanner = new Scanner(System. in);
                System.out.println("CHOOSE OPERATION 1-COMPRESS \t 2-DECOMPRESS \n");
                String operation = scanner.nextLine();
               System.out.println("enter file name \n");
                String input = scanner. nextLine();
                String temp;
                
                
		if("compress".equals(operation))
              {
                  temp= instance.getinput(input);
                  instance.getchar(temp);
                  instance.nodeList();
                    Node root =instance.priorityqueueandlist();
                    instance.getCode(root, "");
                    instance.outputFile();
                    
                    
                
              }
               else if("decompress".equals(operation))
               {
                    temp = instance.getinput(input);
                    instance.readDecompress(temp);
                    instance.getencoded();
                    Node root = instance.priorityqueueandlist();
                    instance.decompress(root);
                    instance.writetofiledecom();
                    
               
               }
                    
    }
    
}
