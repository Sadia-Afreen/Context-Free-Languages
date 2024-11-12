

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CFGDeriver {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> terminals = new ArrayList<>();
        ArrayList<String> rules = new ArrayList<>();
        StringBuilder terminal= new StringBuilder();

        if (args.length < 1) {
            System.out.println("Error, usage: java ClassName inputfile");
            System.exit(1);
        }
        /* Taking input files and simulation type from console */
        Scanner selectSimulation = new Scanner(args[0]);
        String simulationType = selectSimulation.next();
        Scanner testCase = new Scanner(new FileInputStream(args[1]));
        Scanner input = new Scanner(new FileInputStream(args[2]));
        String inputString = input.nextLine();

        int numOfRules;
        numOfRules = testCase.nextInt();

        /* Processing test case files */
        while (testCase.hasNext()) {
            String s = testCase.next();
            rules.add(s);
            for(int i=0; i< s.length(); i++){
                if(s.charAt(i) >= 'a' && s.charAt(i)<= 'z'){
                    terminal.append(s.charAt(i));
                }
                else{
                    if(terminal.length()!=0){
                        terminals.add(String.valueOf(terminal));
                        terminal = new StringBuilder();
                    }
                }
            }
        }
        if(terminal.length()!=0){
            terminals.add(String.valueOf(terminal));
        }

        CFG cfg = new CFG(numOfRules, rules, terminals, simulationType);
        cfg.makeTerminalRules();
        cfg.makeDerivationRules();
        cfg.makeInitialRule();
        cfg.readInitialRule();
        cfg.readInputString(inputString);
        cfg.initializeVisitedMap();
        cfg.setBound();
        cfg.processInput();
    }
}
