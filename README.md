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



1. **Compilation et exécution** :
   Ouvrez un terminal à la racine du projet et lancez :
   ```sh
   ./compilation.sh
   ```
   Le script compile tous les fichiers listés dans `compile.list` et lance l’application.

3. **Logs** :
   Les erreurs de compilation ou d’exécution sont enregistrées dans le dossier `class/logs/`.

## Utilisation

- **Ajouter une tâche** : Menu `Fichier > Ajouter Tache`
- **Actualiser** : Menu `Fichier > Actualiser`
- **Importer/Enregistrer** : Menu `Fichier > Importer` ou `Enregistrer sous`
- **Modifier une tâche** : Sélectionner une tâche dans l’interface puis utiliser les boutons ou menus contextuels
- **Options** : Menu `Fichier > Options` pour personnaliser l’affichage ou les paramètres

## Liste des erreurs
   - Erreur **1** : Il n'y a pas 2 séparateurs dans le fichier texte. 
   - Erreur **2** : Une durée dans le fichier importé n'est pas un entier. 
   - Erreur **3** : Le nom d'une tâche dans le fichier importé est en double. 
   - Erreur **4** : Un prédecesseur dans le fichier importé n'existe pas. 
   - Erreur **5** : Une tâche dans le fichier importé n'a pas de nom. 
   - Erreur **6** : Un prédesceur dans le fichier importé n'a pas de nom. 
   - Erreur **7** : Le fichier importé n'existe pas ou n'est pas accessible.
   - Erreur **8** : 

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