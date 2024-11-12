import java.util.ArrayList;
import java.util.List;

public class PDA {
    ArrayList<String> stackPDA;
    List<String> grammar;
    int count =0;
    public PDA(ArrayList<String> stack, List<String> grammar, int count){
        this.stackPDA = stack;
        this.grammar = grammar;
        this.count = count;
        pop();
        push(this.grammar);
    }

    public String top(){
        return stackPDA.get(0);
    }
    public void pop(){
        stackPDA.remove(0);
    }
    public boolean containsUpperCase(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return true; // If an uppercase letter is found, return true
            }
        }
        return false;
    }

    public void push(List<String> value){
        if(!value.get(0).equals("!")){
            if(containsUpperCase(value.get(0))){
                for(int i=value.get(0).length()-1; i>=0; i--){
                    stackPDA.add(0, String.valueOf(value.get(0).charAt(i)));
                }
            }
            else{
                for(int i=value.size()-1; i>=0; i--){
                    stackPDA.add(0, value.get(i));
                }
            }
        }
    }

}
