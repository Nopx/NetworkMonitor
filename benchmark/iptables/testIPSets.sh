sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

p=1
c=1
while [ "$p" -le 200 ]; do
	ipset create set$c hash:ip
	iptables -A INPUT -m set --match-set set$c src -j DROP
	i=$((p))
	while [ "$i" -le $((p+4)) ]; do
		j=0
		while [ "$j" -le 49 ]; do
			k=0
			while [ "$k" -le 199 ]; do
				ipset -A set$c 4.$i.$j.$k
				k=$((k+1))
			done
			j=$((j+1))
		done
		i=$((i+1))
	done
	c=$((c+1))
	ipset create set$c hash:ip
	iptables -A INPUT -m set --match-set set$c src -j DROP
	i=$((p+5))
	while [ "$i" -le $((p+9)) ]; do
		j=0
		while [ "$j" -le 49 ]; do
			k=0
			while [ "$k" -le 199 ]; do
				ipset -A set$c 4.$i.$j.$k
				k=$((k+1))
			done
			j=$((j+1))
		done
		i=$((i+1))
	done
	sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'
	p=$((p+10))
	c=$((c+1))
done
