The project implements NFA and DFA. The project has three files FASimulator, ENFA, DFA where FASimulator is the
execution file. The tests and the evaluations folder are also included in /tests and /eval folder. The project report
outlining experiment results for benchmark files is included in the project folder as 'Project 1 Report.pdf'. The whole
project was implemented in java language. To run the project, following steps should be followed:

Compile the java files: javac *.java -target 8 -source 8 <br>
To run the program for NFA: java CFGDeriver 1 TC4/tests/tc1.txt TC4/tests/in1_1.txt <br>
To run the program for DFA: java FASimulator 2 tests/tc0.txt tests/in0_1.txt <br>
Output will be 'yes' or 'no' for accept and reject, respectively for input strings. As the DFA was not converted from
the NFA, it will output "Invalid input for DFA." when input file has multiple transtition for a single alphabet.