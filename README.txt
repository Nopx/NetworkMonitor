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
Special files:
	testCap.pcap - has a few ARP packets saved in order to test the functionalities required by Project02, question 2/3
	QuestionAnswers.txt - answers to questions from the PDFs
	config.cfg - contains a configuration for which addresses are broadcast / blacklisted
	ErrorNotice.log - all ERRORs and NOTICEs thrown by ARP packets get logged here
