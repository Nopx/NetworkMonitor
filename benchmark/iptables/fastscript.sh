i=1
while [ "$i" -le 40 ]; do
	#ipset create set$i hash:ip
	#iptables -A INPUT -m set --match-set set$i src -j DROP
	sudo ipset destroy set$i; 
	i=$((i+1))

done
