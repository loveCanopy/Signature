#set -x
echo -e "\n"|keytool -genkeypair -keyalg RSA -keysize 2048 -sigalg SHA1withRSA -validity 3600 -alias myCertificate -keystore myKeystore.keystore  -storepass 246437 -dname CN=YUJIE,OU=CCST,O=JLU,L=JILIN,ST=JILIN,C=CHINA

keytool -list  -v -keystore myKeystore.keystore -storepass 246437

keytool -exportcert -alias myCertificate -keystore myKeystore.keystore -file myCer.cer -rfc -storepass 246437

keytool -printcert -file myCer.cer -storepass 246437

keytool -certreq -alias myCertificate -keystore myKeystore.keystore -file myCsr.csr -v -storepass 246437

echo -e "y"|keytool -importcert -trustcacerts -alias myCertificate_new -file myCer.cer -keystore myKeystore.keystore -storepass 246437

keytool -list -alias myCertificate -keystore myKeystore.keystore -storepass 246437
