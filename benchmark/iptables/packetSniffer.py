from scapy.all import *
import time

def pkt_callback(pkt):
	timetime = int(round(time.time() * 1000))
	if DNS in pkt:
		if pkt[DNS].opcode==7:
			with open("2000000.txt", "a") as myfile:
				myfile.write(""+str(pkt[DNS].id)+"recv\n"+str(timetime)+"\n")

sniff(prn=pkt_callback,filter="src 4.2.2.2", store=0)
