/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author lobna
 */
public class Node implements Comparable<Node> {
    public String letter;
    public int frequency ;
    public String code ;
    public Node left;
    public Node right;
    

    public Node(String letter, int frequency)  //normal node 
    {
        this.letter = letter;
        this.frequency = frequency;
        this.left=null;
        this.right=null;
        this.code="";
    } 

  
    public int getFrequency() {
        return frequency;
    }

    /**
     *
     * @param t
     * @return
     */
    
    @Override
    public int compareTo(Node t) //USED TO DETERMINE ORDER OF ELEMENTS IN PRIORITY QUEUE
    {
        int comperage = t.getFrequency();
        return this.getFrequency()-comperage;
    }

    

    
    
        
    
    
}
