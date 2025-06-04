#!/bin/bash

javac @compile.list -d ./class
cd class
java MPM.Controleur
echo "Fichier chargé"