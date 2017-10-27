#!/bin/bash
set -x
cd /mnt/d/Test || exit 101
methodName=(KEP_SC_5_1 KEP_SC_5_1_1 KEP_SC_5_1_2 KEP_SC_5_1_3 KEP_SC_5_1_4)
for i in ${methodName[@]}
do
	rmdir $i
	mkdir $i
	cd $i
	for j in `seq 10 20`
	do
		touch "Number="$j.txt
		java -jar /mnt/d/Test/test.jar $i $j 30 >> "Number="$j.txt
		pids=(${pids[*]} $!)	
	done
	cd ..
done	
cnt=0
echo 'Start Background Pids: '${pids[@]}
for pid in ${pids[@]}
do
    echo 'Wait Pid  '$pid
    wait $pid
    if [ $? -eq 0 ]; then
        ((cnt++))
    fi
done

end=`date +"%Y-%m-%d %H:%M:%S"`

if [ $cnt -ne ${#pids[@]} ]; then
        exit 1
else
	echo "[$end] Rsync file success"
fi	

