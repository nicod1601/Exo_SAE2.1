#!/bin/bash

javac @compile.list -d ./class
if [ $? -ne 0 ]; then
    echo "Erreur de compilation"
    exit 1
fi

cd class
java MPM.Controleur
echo "Programme chargé"