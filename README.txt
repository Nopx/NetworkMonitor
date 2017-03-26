SPECIAL ADDITION FOR PROJECT 04
I apoligize that this is so complicated, if you have questions please mail me or ask me to come to your office.
Compilation:
	-The filter classes are in the filter subdirectory and are detached from the rest of the program
	-The classes are already compiled
	-The executable file "compile" will compile the classes
Execution of Q3 and Q4:
	-Testing was implemented in the main classes of BloomFilter.java and Trie.java
	-The file testBloom.sh will adapt the config file and run a test for all increments of rules between 0 and 2.000.000
	-executeBloom will execute the BloomFilter's main method and print a concluding time into the console
	-executeTrie will execute the Trie's main method and print a concluding time and memory usage into the console
Execution of Q1 and Q2:
	-The shell script "testAllPlainRules.sh" tests and updates the firewall rules automatically in all increments from 0 to 2.000.000
	-The shell script "testIPSets.sh" does the same for rules made with IPSets
	-The tests are done with ping floods





****NOT RELEVANT FOR PROJECT04****
NOTHING SIGNIFICANT HAS CHANGED SINCE PROJECT02

Compilation:
	To compile execute the "compile" file
	The compiled files will end up in the bin directory
Execution:
	To execute use the "execute" file
	Arguments can be appended to the "execute" file (e.g. "./execute arg0 arg1 arg2 arg3")
Arguments:
	1.Device to capture from
	2.Amount of Packets to sniff");
	3.Source File (optional)");
	4.Destination File (optional)");
	If no Destination File is given, the packets will be interpreted and printed to the output.");
Code execution:
	-The point of entry is the Sniffer class
	-The class names should have self explanatory names and the code is commented
Special files and folders:
	/samples/ - Contains the sample pcap files for testing
	QuestionAnswers.txt - answers to questions from the PDFs
	ErrorNotice.log - all ERRORs and NOTICEs thrown by ARP packets get logged here
