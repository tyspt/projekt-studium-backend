mvn clean
mvn install

scp ./target/*.jar tony@jiaweitang.com:~

ssh -t tony@jiaweitang.com << EOF
    pkill -f projektstudium
    java -jar projektstudium.backend*.jar > /dev/null 2>&1 &
EOF

read -p "done!"