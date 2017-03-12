Installation:
	This program works with JNetPcap. In order to use it you have to download the library from here: http://jnetpcap.com/download
	Since JnetPcap uses JNI there is sadly no way around this manual installation.
Compilation:
	The code is precompiled in the /bin folder.
	To compile manually type: "javac -cp .:DNSPacket:jnetpcap.jar Sniffer.java"
Execution:
	To execute you need to know the location of your jnetpcap library.
	If executing from the bin directory, type: "sudo java -Djava.library.path=PATH_TO_JNETPCAP_LIBRARY -cp ../jnetpcap.jar:.:DNSPacket Sniffer"
	To execute then type: "sudo java -Djava.library.path=PATH_TO_JNETPCAP_LIBRARY -cp jnetpcap.jar:.:DNSPacket Sniffer"
Arguments:
	1.Amount of Packets to sniff");
	2.Source File (optional)");
	3.Destination File (optional)");
	If no Destination File is given, the packets will be interpreted and printed to the output.");
