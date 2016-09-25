// Josh Liu ID# 260612384

// Node for a doubly LinkedList
public class StringNode{

	// held data + pointers to previous and next nodes
    private String data;
    private StringNode next;
    private StringNode previous;
    
    // constructor takes in the held data as input
    public StringNode(String input_data){
        data = input_data;
        next = null;
        previous = null;
    }
    
    // method for appending to the data string
    public void addString(String s) {
    	data += s;
    }
    
    // SETTERS and GETTERS 
    
    public void setString(String s) {
    	data = s;
    }
    
    public void setNext(StringNode n) {
    	next = n;
    }
    
    public void setPrev(StringNode p) {
    	previous = p;
    }
    
    public String getString() {
    	return data;
    }
    
    public StringNode getNext() {
    	return next;
    }
    
    public StringNode getPrev() {
    	return previous;
    }
    
}
