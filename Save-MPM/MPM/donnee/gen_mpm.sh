#!/bin/bash
# filepath: /home/nicod16/Documents/Exo_SAE2.1/MPM/donnee/gen_mpm.sh

read -p "Combien de tâches voulez-vous ? " N
read -p "Nom du fichier de sortie : " FILENAME



for ((i=1; i<=N; i++)); do
    NAME="T$i"
    DURATION=$(( (RANDOM % 7) + 2 ))
    if [ $i -eq 1 ]; then
        echo "$NAME|$DURATION|" >> "$FILENAME"
    elif [ $i -eq 2 ]; then
        echo "$NAME|$DURATION|T$((i-1))" >> "$FILENAME"
    else
        echo "$NAME|$DURATION|T$((i-1)),T$((i-2))" >> "$FILENAME"
    fi
done