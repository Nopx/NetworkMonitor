beforeCode =0 #0 is time, 1 is send, 2 is recv
listElement=0 #contains the last list element read out
sends={}
recvs={}
with open("2000000.txt", "r") as f:
	for l in f:
		if beforeCode==1:
			sends[listElement]=int(l)
			beforeCode=0
		if beforeCode==2:
			recvs[listElement]=int(l)
			beforeCode=0
		if "recv" in l:
			listElement = int(l[:4])
			beforeCode = 2
		if "send" in l:
			listElement = int(l[:4])
			beforeCode = 1
average=0
cnt=0
for k in sends.iterkeys():
	try:
		if recvs[k]>0 and sends[k]>0:
			average = average+ recvs[k]-sends[k]
			cnt=cnt+1
	except:
		pass

print average/cnt
