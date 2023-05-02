import subprocess


for i in range(6,21):
	subprocess.call(["touch","map0" + str(i) + ".txt" ])

