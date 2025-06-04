#!/bin/bash

javac @compile.list -d ./class
if [ $? -ne 0 ]; then
    echo "Erreur de compilation"
    exit 1
fi
echo "Compilation réussie"
echo "Lancement du programme..."
cd class
java MPM.Controleur
