// Josh Liu ID# 260612384

// Linked list with reference chains both forwards and backwards
public class DoublyLinkedList{
   
	// Beginning StringNode and number of items in list (excluding start)
	private StringNode start;
    private int length;
    
    // constructor sets the start StringNode
    public DoublyLinkedList(){
       start = new StringNode("");
       length = 0;
    }

    // find if the value exists in the list
    // if it does, return the first StringNode that matches
    // return null otherwise
    public StringNode find( String value ){
        
    	// goes through the StringNodes in order and compares data
    	StringNode currentStringNode = start.getNext();	
    	for (int i = 0; i < length; i++) {
    		if (currentStringNode.getString().equals(value)) {
    			return currentStringNode;
    		}
    		if (currentStringNode.getNext() != null) {
    			currentStringNode = currentStringNode.getNext();
    		}	
    	}
        return null;
    }
    
    // insert a new StringNode at the beginning of the list
    public void insertStart( String value ){
        
    	// creates the new StringNode
    	StringNode newStringNode = new StringNode(value);
    	// getting the first StringNode 
    	StringNode firstStringNode = start.getNext();
    	// connect start to the new StringNode
    	start.setNext(newStringNode);
    	newStringNode.setPrev(start);
    	// if there is a first StringNode, connect it to the new StringNode
    	if (firstStringNode != null) {
        	newStringNode.setNext(firstStringNode);  
        	firstStringNode.setPrev(newStringNode);
    	}
    	// increment the length of the list
    	length++;
    }
    
    // insert a new StringNode at the end of the list
    public void insertEnd( String value ){
    	
    	// find the start StringNode and create the new StringNode
    	StringNode currentStringNode = start;
    	StringNode newStringNode = new StringNode(value);
    	// go through list until the end
    	while (currentStringNode.getNext() != null) {
    		currentStringNode = currentStringNode.getNext();
    	}
    	// connect the end StringNode to the new StringNode
    	currentStringNode.setNext(newStringNode);
    	newStringNode.setPrev(currentStringNode);
    	// increment the length of the list
    	length++;
    }

    // remove all the occurrences of the value in the list
    public void remove( String value ){
    	
    	// loops through the list finding every match and removing until there is no match
       	StringNode targetStringNode = find(value);   	
    	while (targetStringNode != null) {
        	// removes Node if its the last one
    		if (targetStringNode.getNext() == null) {
        		targetStringNode.getPrev().setNext(null);
        	}
        	else {
        		// connects the StringNodes previous to and after the target (leaving the target unreferenced)
        		targetStringNode.getPrev().setNext(targetStringNode.getNext());
        		targetStringNode.getNext().setPrev(targetStringNode.getPrev());
        	}
           	targetStringNode = find(value);
    		// length of the list is decremented
           	length--;
    	}
    }

    // remove from the list the StringNode at the position given
    // by the value of index.
    public void removeAtIndex( int index ){
       
    	StringNode currentStringNode = start;
    	// catch for index 0 and below (not possible)
    	if (index < 1) {
    		return;
    	}
    	// goes through the list to the index position StringNode (if at the end, method returns out)
    	for (int i = 0; i < index; i++) {
    		// eventually sets the index StringNode to currentStringNode
    		if (currentStringNode.getNext() != null) {
    			currentStringNode = currentStringNode.getNext();
    		}
    		else {
    			return;
    		}
    	}
    	
    	// removes the Node if it's the last one
    	if (currentStringNode.getNext() == null) {
    		currentStringNode.getPrev().setNext(null);
    	}
    	else {
        	// connects the StringNodes previous to and after the target (leaving the target unreferenced)
    		currentStringNode.getPrev().setNext(currentStringNode.getNext());
    		currentStringNode.getNext().setPrev(currentStringNode.getPrev());
    	}
		// length is decremented
		length--;
    }
   
    // return the string in reverse order (goes to last StringNode and prints backwards)
    public String toStringReverse(){
        
    	String result = "";
        StringNode currentStringNode = start;
        while (currentStringNode.getNext() != null) {
        	currentStringNode = currentStringNode.getNext();
        }
        for (int i = 0; i < length; i++) {
        	result += currentStringNode.getString();
        	currentStringNode = currentStringNode.getPrev();
        }
        return result;
    }

    // return the string of all data of StringNodes in the list
    public String toString(){
        
    	String str = "";
        StringNode currentStringNode = start;
        while (currentStringNode != null) {
            str += currentStringNode.getString();
            currentStringNode = currentStringNode.getNext();
        }        
        return str;
    }
    
    public static void main (String[] args){
        
    	// testing 
    	DoublyLinkedList list = new DoublyLinkedList();
        String once = "And you may find yourself ";
        
        list.insertStart("I am helpless. ");
        list.insertEnd(once);
        list.insertEnd("There is hope. ");
        list.insertEnd("I am helpless. ");
        list.insertEnd("Hello!. ");
        list.insertEnd("I do not believe. ");
        
        StringNode n = list.find(once);
        n.addString("in a shotgun shack. ");
        
        System.out.println(list);
        
        list.remove("I do not believe. ");
        list.removeAtIndex(1);
        
        System.out.println(list);
        
        System.out.println(list.toStringReverse());
    }
}
