from scapy.all import *
import time
from random import randint

def sendXPackets(pkt,cnt):
	for x in range(0,cnt):
		pkt[IP].src="4.2.2.2"#randomAddress()
		pkt[IP].dst="88.202.160.3"
		pkt[DNS].id=randint(10000,60000)
		timetime = int(round(time.time() * 1000))
		send(pkt)
		with open("2000000.txt", "a") as myfile:
		    myfile.write(""+str(pkt[DNS].id)+"send\n"+str(timetime)+"\n")

def randomAddress():
	return str(randint(0,200))+"."+str(randint(0,200))+"."+str(randint(0,200))+"."+str(randint(0,200))

#gratuitous
#pkt = Ether(src="30:52:cb:17:7c:3f", dst="64:6c:b2:94:5d:8b")/ARP(op=1, psrc="192.168.43.119", pdst="192.168.43.1", hwsrc="30:52:cb:17:7c:3f", hwdst="64:6c:b2:94:5d:8b")
pkt = IP()/UDP()/DNS(opcode=7,qd=DNSQR(qname="TESTTESTTEST"))

start = time.time()

sendXPackets(pkt,10)

end = time.time()
print(end - start)

