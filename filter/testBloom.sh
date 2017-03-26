rm config.cfg
m=1
while [ "$m" -le 20 ]; do
	i=1
	while [ "$i" -le 10 ]; do
		j=0
		while [ "$j" -le 49 ]; do
			k=0
			while [ "$k" -le 199 ]; do
				echo $(($RANDOM % 256)).$(($RANDOM % 256)).$(($RANDOM % 256)).$(($RANDOM % 256)) >>config.cfg
				k=$((k+1))
			done
			j=$((j+1))
		done
		i=$((i+1))
	done
	java -cp ..:../Ethernet:./zero-allocation-hashing-0.8.jar:. BloomFilter
	m=$((m+1))
done
