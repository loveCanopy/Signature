#!/bin/bash
#methodName=(KEP_SC_5_1)
methodName=(KEP_SC_5_1 KEP_SC_5_1_1 KEP_SC_5_1_2)
for dir in ${methodName[@]}
do
	file=`ls -l  $dir|awk -F " " '/Number/{print $9}'`
	for i in $file
	do
		echo $dir,$i
		head_line=`awk '/满足/{print NR}' $dir/$i`
		head_text=`awk '{if(NR>=0&&NR <='$head_line'){print $0}}' $dir/$i`
		echo $head_text
		echo -e "\n"
		line=`awk '/SB|K/{print NR}' $dir/$i`
		for numline in $line
		do
			source_line=`expr $numline - 1`
			source_text=`awk 'NR=='$source_line'{print $0}' $dir/$i`
			SK=`awk 'NR=='$numline'{print $0}' $dir/$i`
			hash_num=`expr $numline + 2`
			hash_text=`awk 'NR=='$hash_num'{print $0}' $dir/$i`
			echo $source_text
		    echo $SK
			echo $hash_text
			echo -e "\n"
		done
		#得到记录数
		num=`awk -F "" '/比较次数/{print $5}' $dir/$i |awk '{num+=1 }END {print num}'`
		#得到比较次数的总数
		number=`awk -F ":" '/比较次数/{print $2}' $dir/$i|awk '{num+=$0 }END {print num}'`
	#number=`awk '/SB|K|比较次数/{printf "%s\n",$0 }'| awk -F "" '/比较次数/{print $5}'|awk '{num+=$0 }END {print num}' $j`
		echo $dir"---"$i,Hash比对数$number,记录数$num
		hash_num=`awk 'BEGIN{printf "%0.5f",'$number'/'$num'}'`
		echo $hash_num
		echo -e "\n"
	done
done

