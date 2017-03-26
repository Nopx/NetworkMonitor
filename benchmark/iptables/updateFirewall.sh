sudo iptables -F; rm rules; sh makeRules.sh > rules; sudo iptables-restore < rules
