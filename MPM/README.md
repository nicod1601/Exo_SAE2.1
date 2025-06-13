# Exo_SAE2.1

  

## Présentation

  

Ce projet est une application Java permettant de gérer la planification de projets selon la méthode MPM (Méthode des Potentiels Métra). L’interface graphique permet d’ajouter, modifier et visualiser les tâches d’un projet, de gérer les dépendances, et d’obtenir le chemin critique.

 
## Lancement du programme

  

1.  **Compilation et exécution** :

Ouvrez un terminal à la racine du projet et lancez :

```sh

chmod +x compilation.sh

./compilation.sh

```
Le script compile tous les fichiers listés dans `compile.list` et lance l’application.

 2. **Importer un fichier** : 
 Pour importez un fichier, allez sur le menu fichier en haut à gauche du programme puis importé. Puis sélectionner un fichier au format `.txt` et respectant la norme d'écriture qui est la suive : 
 `NOM|DURÉE|CordX,CordY|PRÉDÉCESSEURS`

3.  **Logs** :

Les erreurs de compilation ou d’exécution sont enregistrées dans le dossier `class/logs/`.

  

## Utilisation

  

-  **Ajouter une tâche** : Menu `Fichier > Ajouter Tache` ou Clique droit puis `Ajouter`. Sélectionner le prédécesseur, le nom et la durée de la tâche.  

-  **Modifier une tâche** : Clique droit sur une tâche puis `Modifier`
- **Supprimer une tâche** : Clique droit sur une tâche puis `Supprimer`
- **Supprimer une flèche** : Clique droit sur une flèche puis `Supprimer flèche`
-**Ajouter un prédécesseur** : Clique droit sur une tâche puis `Ajouter Prc`
-  **Actualiser** : Menu `Fichier > Actualiser`
-  **Enregistrer/Enregistrer sous** : Menu `Fichier > Enregistrer` ou `Enregistrer sous`
-  **Options** : Menu `Fichier > Options` pour personnaliser l’affichage ou les paramètres

  

## Auteurs

  

- DELPECH Nicolas

- FOYER Emilien

- GUTU Nichita

- KULPA Clément

  

## Licence

  

Projet réalisé dans le cadre de la SAE 2.1, 2.2, 2.5 – Groupe 09.

Usage pédagogique uniquement.