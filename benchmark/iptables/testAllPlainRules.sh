sudo iptables -F
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 10 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 20 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 30 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 40 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 50 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 60 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 70 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 80 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 90 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 100 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 110 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 120 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 130 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 140 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 150 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 160 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 170 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 180 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 190 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'

sudo iptables -F
rm rules
echo "*filter\n" >> rules
echo ":INPUT ACCEPT [618:94015]\n" >> rules
echo ":FORWARD ACCEPT [0:0]\n" >> rules
echo ":OUTPUT ACCEPT [269:50438]\n" >> rules
i=1
while [ "$i" -le 200 ]; do
	j=0
	while [ "$j" -le 49 ]; do
		k=0
		while [ "$k" -le 199 ]; do
			echo "-A INPUT -s 4.$i.$j.$k/32 -j DROP" >> rules
			k=$((k+1))
		done
		j=$((j+1))
	done
	i=$((i+1))
done
echo "COMMIT" >> rules
sudo iptables-restore < rules
sudo ping 127.0.0.1 -c 5000 -f | sed '$!d'


