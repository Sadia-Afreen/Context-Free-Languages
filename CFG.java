import java.util.*;

public class CFG {
    int numOfRules, count = 0, bound = 0;
    String insideDerivationKey = "", insideDerivationValue = "", inputString = "", simulationType = "";
    ArrayList<String> terminals, rules;
    HashMap<String, List<List<String>>> grammar = new HashMap<>();
    HashMap<String, String> initialRule = new HashMap<>();
    HashMap<String, String> terminalRules = new HashMap<>();
    ArrayList<String> formattedInputString = new ArrayList<>();
    ArrayList<String> formattedInputStringForBound;
    ArrayList<String> cloneStack = new ArrayList<>();
    ArrayList<String> stack = new ArrayList<>();
    ArrayList<PDA> backtrack = new ArrayList<>();
    HashMap<String, HashMap<List<String>, Boolean>> visitedMap = new HashMap<>();


    public void makeTerminalRules() {
        for (String it : terminals) {
            terminalRules.put(it, it);
        }
    }

    public static void addRule(HashMap<String, List<List<String>>> grammar, String key, List<List<String>> rules) {
        if (!grammar.containsKey(key)) {
            grammar.put(key, new ArrayList<>());
        }
        grammar.get(key).addAll(rules);
    }

    public void makeDerivationRules() {
        for (String it : rules) {
            String[] parts = it.split("->");
            insideDerivationKey = parts[0].trim();
            insideDerivationValue = parts[1].trim();
            String[] partsWithoutSpace = insideDerivationValue.split("_");
            addRule(grammar, insideDerivationKey, Arrays.asList(Arrays.asList(partsWithoutSpace)));
        }
    }

    public void makeInitialRule() {
        initialRule.put("!", "S$");
    }

    public void readInitialRule() {
        for (int i = 0; i < initialRule.get("!").length(); i++) {
            stack.add(String.valueOf(initialRule.get("!").charAt(i)));
        }
    }

    public void readInputString(String inputString) {
        this.inputString = inputString;
        if (inputString.contains(String.valueOf('_'))) {
            String[] partsWithoutSpace = inputString.split("_");
            formattedInputString.addAll(Arrays.asList(partsWithoutSpace));
        } else {
            for (int i = 0; i < inputString.length(); i++) {
                formattedInputString.add(String.valueOf(inputString.charAt(i)));
            }
        }
        formattedInputStringForBound = new ArrayList<>(formattedInputString);
    }

    public void initializeVisitedMap() {
        for (Map.Entry<String, List<List<String>>> entry : grammar.entrySet()) {
            String key = entry.getKey();
            List<List<String>> innerList = entry.getValue();

            // Creating a HashMap to store visited status for each inner list
            HashMap<List<String>, Boolean> innerVisitedMap = new HashMap<>();

            for (List<String> list : innerList) {
                // Initializing all inner lists as not visited initially
                innerVisitedMap.put(list, false);
            }

            // Putting the inner visited map for the corresponding key
            visitedMap.put(key, innerVisitedMap);
        }
    }
    public void setBound(){
        if(simulationType.equals("1")){
            bound = 100;
        }
        if(simulationType.equals("2")){
//            bound = (formattedInputStringForBound.size() - 1)/(count-1);
            bound = 100;
        }
        if(simulationType.equals("3")){
            bound = 2 * formattedInputStringForBound.size() - 1;
        }
    }
    public void processInput() {
        if (formattedInputString.size() == 1 && formattedInputString.get(0).equals("!")) {
            System.out.println("yes");
            return;
        }
        while (!(formattedInputString.size() == 0)) {
            if (count > bound) {
                System.out.println("no");
//                System.out.println("count: "+count);
                return;
            }
            String key = stack.get(0);
            if (stack.get(0).equals("$") && formattedInputString.size() > 0) {
                if (backtrack.size() > 0) {
                    stack = backtrack.get(backtrack.size() - 1).stackPDA;
                    count = backtrack.get(backtrack.size()-1).count;
                    backtrack.remove(backtrack.size() - 1);
                    key = stack.get(0);
                }

            }
            if (isLowerCase(key) && !(key.equals(formattedInputString.get(0)))) {
                if (backtrack.size() > 0) {
                    stack = backtrack.get(backtrack.size() - 1).stackPDA;
                    count = backtrack.get(backtrack.size()-1).count;
                    backtrack.remove(backtrack.size() - 1);
                    key = stack.get(0);
                } else {
                    System.out.println("no");
//                    System.out.println("count: "+count);
                    return;
                }
            }
            if (key.equals(formattedInputString.get(0))) {
                stack.remove(0);
                formattedInputString.remove(0);
                key = stack.get(0);
            }
            if (!isLowerCase(key) && formattedInputString.size() > 0) {
                count++;
                for (int i = 0; i < grammar.get(key).size(); i++) {
                    if (!(visitedMap.get(key).get(grammar.get(key).get(i)))) { //if not visited
                        //marking inner visited map list as true
                        HashMap<List<String>, Boolean> innerVisitedMap = visitedMap.get(key);
                        innerVisitedMap.put(grammar.get(key).get(i), true);

                        if (grammar.get(key).size() > 1) {
                            PDA node = new PDA(new ArrayList<>(stack), grammar.get(key).get(i),count);
                            backtrack.add(node);
                        } else {
                            PDA node = new PDA((stack), grammar.get(key).get(i),count);
                            backtrack.add(node);
                        }
                    }

                }
                initializeVisitedMap();
                if (backtrack.size() > 0) {
                    stack = backtrack.get(backtrack.size() - 1).stackPDA;
                    backtrack.remove(backtrack.size() - 1);
                }
            }
        }
        for (String i : stack) {
            if (isLowerCase(i) && !i.equals("$")) {
                System.out.println("no");
//                System.out.println("Count: " + count);
                return;
            }
        }
        System.out.println("yes");
//        System.out.println("Count: " + count);

    }

    public boolean isLowerCase(String str) {
        return str.equals(str.toLowerCase());
    }
    CFG(int numOfRules, ArrayList<String> rules, ArrayList<String> terminals, String simulationType) {
        this.numOfRules = numOfRules;
        this.rules = rules;
        this.terminals = terminals;
        this.simulationType = simulationType;
    }
}
