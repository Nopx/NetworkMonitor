echo "*filter\n"
echo ":INPUT ACCEPT [618:94015]\n"
echo ":FORWARD ACCEPT [0:0]\n"
echo ":OUTPUT ACCEPT [269:50438]\n"
i=0
while [ "$i" -le 255 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/16 -j DROP"
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT"
