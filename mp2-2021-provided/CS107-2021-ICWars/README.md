# ICWARS

Le but du jeu est de battre son adversaire en détruisant toutes ses unités.

Chaque joueur commence un niveau avec deux unités : 
- un **Tank**
- un **Soldat**

# Contrôles
Il y a plusieurs touches activables pour jouer à **ICWARS** :
- Les **flèches directionnelles** : Pour se déplacer dans le **jeu**.
- **Enter** : Pour interagir avec une **unité**.

- **R** : Pour **réinitialiser** le jeu et retourner au premier niveau.

- **N** : Pour passer au niveau suivant. S'il n'y a plus de niveau, un écran de **GameOver** s'affiche.

- **W** : Pour qu'une unité exécute l'action **"Attendre"**.

- **A** : Pour qu'une unité exécute l'action **"Attaque"**.

- **TAB** : Pour passer son tour à **l'adversaire**.

# Fonctionnalités

Les fonctionnalités du jeu sont les suivantes : 
- Il y a **2 niveaux**.

- Le **joueur** joue contre une **Intelligence Artificielle** qui déplace automatiquement ses unités et attaque l'unité la plus proche s'il peut l'attaquer.

- Chaque **unité** a un **rayon de déplacement** qui lui est propre.

- Chaque **unité** a un nombre maximal de **points de vie** qui lui est propre.

- Chaque **unité** provoque des **dégâts d'attaque** qui lui sont propres.

- Les **Points de vie**  d'une **unité** baissent si **l'unité** est attaquée.

- Le **joueur** interagit avec une **unité** en appuyant sur **ENTER**. Si cette **unité** n'a pas bougé, alors il peut la déplacer. Si elle a bougé mais n'a pas encore **"agi"**, alors le **joueur** peut sélectionner une **action** à réaliser. Sinon **l'unité** ne peut plus rien faire.

- Lorsque le joueur choisit l'action **"Attendre"**, elle se **répare** et gagne 1 **Point de vie** pour le tour suivant.

- Lorsque le joueur choisit l'action **"Attaque"**, le **joueur** peut choisir quelle **unité ennemie** située à l'intérieur du **rayon d'attaque** de son unité attaquer. Sinon il retourne à la sélection d'une action et doit sélectionner l'action **"Attendre"**.

- Lorsqu'une **unité** n'a plus de **Points de vie**, elle disparait du jeu car elle est morte.

- Lorsqu'un **joueur** a perdu toutes ses **unités**, le **jeu** passe au **niveau** suivant.

- Lorsque le **jeu** est terminé, un écran de **GameOver** s'affiche et peut être déplacé par le **joueur**.


