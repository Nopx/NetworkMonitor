from subprocess import Popen
Popen("sudo iptables -A INPUT 65.65.44.100 -j",shell=True)
