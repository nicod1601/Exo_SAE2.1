# Exo_SAE2.1

## Présentation

Ce projet est une application Java permettant de gérer la planification de projets selon la méthode MPM (Méthode des Potentiels Métra). L’interface graphique permet d’ajouter, modifier et visualiser les tâches d’un projet, de gérer les dépendances, et d’obtenir le chemin critique.

## Structure du projet

- `compilation.sh` : Script de compilation et d'exécution
- `compile.list` : Liste des fichiers Java à compiler
- `Equipe_9-Diagramme-de-class.pdf` : Diagramme de classes UML
- `class/` : Répertoire de sortie des fichiers compilés
- `class/logs/` : Logs d’erreurs de compilation ou d’exécution
- `MPM/` : Code source principal
- `Controleur.java` : Contrôleur principal de l’application
- `donnee/` : Données du projet
- `ihm/` : Interface graphique (Swing)
- `metier/` : Logique métier
- `Save-MPM/` : Sauvegarde d’une version précédente du projet

## Installation

1. **Prérequis** :
   - Java JDK 17 ou supérieur installé
   - Système compatible Unix (Linux, macOS) ou Windows avec bash

2. **Compilation et exécution** :
   Ouvrez un terminal à la racine du projet et lancez :
   ```sh
   ./compilation.sh
   ```
   Le script compile tous les fichiers listés dans `compile.list` et lance l’application.

3. **Logs** :
   Les erreurs de compilation ou d’exécution sont enregistrées dans le dossier `logs/`.

## Utilisation

- **Ajouter une tâche** : Menu `Fichier > Ajouter Tache`
- **Actualiser** : Menu `Fichier > Actualiser`
- **Importer/Enregistrer** : Menu `Fichier > Importer` ou `Enregistrer sous`
- **Modifier une tâche** : Sélectionner une tâche dans l’interface puis utiliser les boutons ou menus contextuels
- **Options** : Menu `Fichier > Options` pour personnaliser l’affichage ou les paramètres

## Organisation du code

- `MPM/ihm/` : Composants de l’interface graphique (Swing)
- `MPM/metier/` : Logique métier (gestion des tâches, projet, calculs)
- `MPM/Controleur.java` : Fait le lien entre l’interface et la logique métier

## Diagramme de classes

Voir le fichier `Equipe_9-Diagramme-de-class.pdf` pour une vue d’ensemble des relations entre les classes.

## Auteurs

- DELPECH Nicolas
- FOYER Emilien
- GUTU Nichita
- KULPA Clément

## Licence

Projet réalisé dans le cadre de la SAE 2.1 – Groupe 09.  
Usage pédagogique uniquement.