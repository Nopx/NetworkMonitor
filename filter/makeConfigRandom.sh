rm config.cfg
i=1
while [ "$i" -le 40 ]; do
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
m=$((m+1))
