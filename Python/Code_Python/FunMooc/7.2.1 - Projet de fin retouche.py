"""
Titre : Projet FUN MOOC Apprendre à coder avec Python 2021
Auteur : David V.
Date : 21/10/2021

Petit jeu où il faut parcourir un château, récupérer tous les trésors et sortir à l'autre bout.

CONDITION DE VICTOIRE : Arriver à l'autre bout du chateau en ayant récupéré tous les objets du château.

Le programme recoit en entrée 3 fichiers :
- FIchier du plan des lieux sous forme de matrice contenant des chiffres
        valeur 0 pour une case vide,
        valeur 1 pour un mur (infranchissable),
        valeur 2 pour la case de sortie/victoire,
        valeur 3 pour une porte qui sera franchissable en répondant à une question,
        valeur 4 pour une case contenant un objet à collecter.

- Fichier des objets. Contient leurs coordonnées puis leurs noms
- Fichier des portes. Contient leurs coordonnées, la question qui sera posée à l'arrivée à la porte ainsi que la réponse

"""
#############################
####### Zone d'import #######
#############################

import os
os.chdir("C:/Users/vilaj/Desktop/Formation Python/Mooc Python/Fichier de travail")

from CONFIGS import *

import turtle



###########################################################
####### Zone de définition des variables globales #######
###########################################################


ZONE_PLAN_MINI = (-240, -240)  # Coin inférieur gauche de la zone d'affichage du plan
ZONE_PLAN_MAXI = (50, 200)  # Coin supérieur droit de la zone d'affichage du plan
POINT_AFFICHAGE_ANNONCES = (-240, 240)  # Point d'origine de l'affichage des annonces
POINT_AFFICHAGE_INVENTAIRE = (70, 210)  # Point d'origine de l'affichage de l'inventaire

# Les valeurs ci-dessous définissent les couleurs des cases du plan
COULEUR_CASES = 'white'
COULEUR_COULOIR = 'white'
COULEUR_MUR = 'grey'
COULEUR_OBJECTIF = 'yellow'
COULEUR_PORTE = 'orange'
COULEUR_OBJET = 'green'
COULEUR_VUE = 'wheat'
COULEURS = [COULEUR_COULOIR, COULEUR_MUR, COULEUR_OBJECTIF, COULEUR_PORTE, COULEUR_OBJET, COULEUR_VUE]

COULEUR_EXTERIEUR = 'white'

# Couleur et dimension du personnage
COULEUR_PERSONNAGE = 'red'
RATIO_PERSONNAGE = 0.9  # Rapport entre diamètre du personnage et dimension des cases
POSITION_DEPART = (0, 1)  # Porte d'entrée du château

# Désignation des fichiers de données à utiliser
fichier_plan = 'plan_chateau3.txt'
fichier_questions = 'dico_questions.txt'
fichier_objets = 'dico_objets3.txt'


###############################################
####### Zone de définition des fonctions #######
###############################################

def creation_matrice(nom_fichier):
    """
    Création d'une matrice à partir du fichier reçu en entrée
    :param nom_fichier: Nom du fichier contenant le plan
    :return: Retourne la matrice du plan
    """
    matrice = [[int(element) for element in ligne if element.isnumeric()] for ligne in open(nom_fichier,encoding="UTF_8")]
    return matrice


def mise_en_forme_zone_annonce():
    """
    Mise en forme de la zone d'annonce. Trace un cadre autour de la zone, rend toute la zone blanche
    et écrit le titre "Annonce" en haut du cadre.
    :return:
    """

    #Paramètre de la zone à tracer
    turtle.speed(0)  # Augmentation de la vitesse d'affichage
    turtle.color("black","white")
    turtle.begin_fill()
    turtle.up() # Sécurité pour ne rien écrire

    # Se positionner en dessous à gauche de la zone d'annonce pour créer un cadre
    turtle.goto((POINT_AFFICHAGE_ANNONCES[0]-10),(POINT_AFFICHAGE_ANNONCES[1]-20))

    #Trace le cadre autour de la zone
    turtle.down()
    for i in range(2):
        turtle.forward(610)
        turtle.left(90)
        turtle.forward(60)
        turtle.left(90)
    turtle.end_fill()
    turtle.up()

    #Ecrit le titre de la zone et le souligner
    turtle.goto((POINT_AFFICHAGE_ANNONCES[0]), (POINT_AFFICHAGE_ANNONCES[1] + 20))
    turtle.write("Annonce : ")
    turtle.down()
    turtle.forward(43)
    turtle.up()


def mise_en_forme_zone_inventaire():
    """
    Mise en forme de la zone de l'inventaire. Trace un cadre autour de la zone, rend toute la zone blanche
    et ecrit le titre "Inventaire" en haut du cadre.
    :return:
    """

    # Paramètre de la zone à tracer
    turtle.speed(0)  # Augmentation de la vitesse d'affichage
    turtle.color("black", "white")
    turtle.begin_fill()
    turtle.up()  # Sécurité pour ne rien écrire

    # Placement en haut a gauche de la zone d'inventaire pour debut de tracé du cadre
    turtle.goto((POINT_AFFICHAGE_INVENTAIRE[0] - 10), (POINT_AFFICHAGE_INVENTAIRE[1]))

    # Trace le cadre autour de la zone
    turtle.down()
    for i in range(2):
        turtle.forward(300)
        turtle.right(90)
        turtle.forward(500)
        turtle.right(90)
    turtle.end_fill()
    turtle.up()

    # Ecrit le titre de la zone et le souligner
    turtle.goto(POINT_AFFICHAGE_INVENTAIRE[0], (POINT_AFFICHAGE_INVENTAIRE[1]-18))
    turtle.write("Inventaire : ")
    turtle.down()
    turtle.forward(50)
    turtle.up()


def calculer_pas(matrice):
    """
    A partir de la matrice reçu en entrée, renvoit la dimension à donner aux cases pour
    que le plan tienne dans la zone de la fenêtre turtle
    :param matrice:Matrice representant le plan du chateau
    :return:Le pas calculé
    """
    # Calcul de la taille de la zone en X et Y a partir des données contenues dans le fichier CONFIGS
    taille_x = abs(ZONE_PLAN_MINI[0]) + abs(ZONE_PLAN_MAXI[0])
    taille_y = abs(ZONE_PLAN_MINI[1]) + abs(ZONE_PLAN_MAXI[1])

    # Calcul des pas sur l'axe X et l'axe Y
    pas_y = taille_y / len(matrice)
    pas_x = taille_x / len(matrice[0])

    # Le plus petit pas est concervé et est renvoyé en sortie de fonction
    if pas_x <= pas_y:
        calcul_pas = pas_x
    else:
        calcul_pas = pas_y

    return (calcul_pas)



def coordonnees(case, pas):
    """
    Calcul de la position (abscisse et ordonnée) du coin inférieur gauche de la case reçu en entrée grace à
    la dimension "pas" reçu en entrée.
    :param case: Coordonnées d'une case (ligne , colonne)
    :param pas: Taille de la case (en pixel)
    :return: renvoie les coordonées pixel du coin bas et gauche de la cas d'entrée (abscisse et ordonnée)
    """

    coord_y = ZONE_PLAN_MAXI[1] - (case[0] * pas)
    coord_x = ZONE_PLAN_MINI[0] + (case[1] * pas)

    return(coord_x,coord_y)



def tracer_carre(dimension):
    """
    Trace un carré en utilisant la dimension reçu en entrée
    :param dimension: Correspond à la dimension d'un coté du carré
    :return:
    """
    angle = 90
    turtle.down()
    turtle.begin_fill()

    # Tracé du carré
    for i in range(4):
        turtle.forward(dimension-1) # Le -1 est simplement esthétique, pour réduire légérement la taille des cases afin
                                    # que chaques cases ne mangent pas un bout de ses voisinnes.
        turtle.left(angle)

    turtle.end_fill()
    turtle.up()



def tracer_case(case, couleur, pas):
    """
    Permet de tracer un carré a l'endroit indiqué par "case" de la couleur "couleur" et de la dimension "pas"
    :param case: coordonées pixel du coin bas et gauche d'une case
    :param couleur: Couleur que prendra le carré
    :param pas: Taille du coté du carré
    :return:
    """

    turtle.up()
    turtle.goto(case[0], case[1] - pas) # Mise en position du Curseur. Le pas est soustrait afin que le bord haut gauche
                                        # du plan soit toujours aux mêmes coordonées peu importe la taille des cases.
    turtle.color(couleur)
    tracer_carre(pas) #Appel à la fonction de tracé du carré



def tracer_joueur(position_joueur, dimension):
    """
    Trace le rond représentant la position du joueur
    :param position_joueur: Coordonnées (ligne, colonne) de la postion du joueur dans la matrice
    :param dimension: Dimension de la case
    :return:
    """

    #Position de départ du personnage
    turtle.up()
    coordonnees_case = coordonnees(position_joueur,dimension) #Calcul des coordonées du bas gauche la case

    #Calcule les coordonnées (abscisse, ordonnée) du centre de la case
    coordonnees_centre_rond = (coordonnees_case[0] + (dimension/2),(coordonnees_case[1] + (dimension/2))-dimension)
    turtle.goto(coordonnees_centre_rond)
    turtle.down()
    turtle.dot((RATIO_PERSONNAGE * dimension), COULEUR_PERSONNAGE)
    turtle.up()



def afficher_plan(matrice):
    """
    Permet de dessiner le plan du chateau grâce à Turtle
    :param matrice: Matrice représentant le plan du château
    :return:
    """

    turtle.shape("blank")
    dimension = calculer_pas(matrice) #Calcul de la dimension du plan à afficher
    turtle.speed(0)  #Augmentation de la vitesse d'affichage

    for x,ligne in enumerate(matrice): #Pour chaque case de la matrice, calcul des coordonnées,
                                       # de la couleur et tracé de la case
        for y,case in enumerate(ligne):
            if case != 0 :
                coord = coordonnees((x,y), dimension)
                couleur = COULEURS[case]
                tracer_case(coord,couleur,dimension)

    #Position de départ du joueur
    tracer_joueur(POSITION_DEPART,dimension)



def deplacer(matrice, position, mouvement, liste_objet, inventaire, dictionnaire_portes):
    """
    Verification de la possibilité du déplacement demandé par le joueur. Si possible, déplacement du personnage
    et affichage sur la carte.
    :param matrice: Matrice du plan
    :param position: Case actuelle où se situe le personnage
    :param mouvement: Position demandée par le joueur pour le déplacement du personnage
    :return:
    """

    dimension = calculer_pas(matrice)  # Calcul de la dimension du plan à afficher
    prochaine_position = position

    if matrice[mouvement[0]][mouvement[1]] == 0: # Passage sur un case vide
        mouvement_joueur(position, dimension, mouvement, matrice)
        prochaine_position = mouvement

    elif matrice[mouvement[0]][mouvement[1]] == 1: # Passage dans un mur
        pass

    elif matrice[mouvement[0]][mouvement[1]] == 2: # Arrivé à l'objectif
        prochaine_position = verification_objectif_reussi(liste_objet,inventaire,position, dimension, mouvement, matrice)

    elif matrice[mouvement[0]][mouvement[1]] == 3: # Passage dans une porte
        prochaine_position = question_porte(matrice, position, mouvement, dimension, dictionnaire_portes)

    elif matrice[mouvement[0]][mouvement[1]] == 4: # Passage sur un objet
        initialisation_case(matrice, mouvement, dimension)
        mouvement_joueur(position, dimension, mouvement, matrice)
        ramasser_objet(mouvement, liste_objet, inventaire)
        prochaine_position = mouvement

    return prochaine_position



def mouvement_joueur(position, dimension, mouvement, matrice):
    """
    Calcul des coordonnées de déplacement du personnage, effectue le déplacement et modifie la couleur de la case
    que le joueur quitte, sauf s'il s'agit de la case objectif qui reste de sa couleur.
    :param position: Position du personnage pour le faire disparaître de la case
    :param dimension: taille d'une case
    :param mouvement: Position de la case où doit se déplacer le personnage
    :return:
    """
    coord = coordonnees((position[0], position[1]), dimension)
    couleur_case = COULEUR_VUE
    if matrice[position[0]][position[1]]  == 2:
        couleur_case = COULEUR_OBJECTIF
    tracer_case(coord, couleur_case, dimension)
    tracer_joueur(mouvement, dimension)



def deplacer_gauche():
    """
    Calcul de la position souhaitée par le joueur après saisie d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le déplacement du personnage vers la gauche
    """
    global matrice, position_personnage, dictionnaire_objet, inventaire, dictionnaire_portes

    desactiver_mouvement()

    # Calcul la position du personnage quand le placement vers la gauche
    position_souhaite = (position_personnage[0],position_personnage[1]-1)
    prochaine_position = deplacer(matrice, position_personnage, position_souhaite, dictionnaire_objet, inventaire, dictionnaire_portes)
    position_personnage = prochaine_position
    activer_mouvement()



def deplacer_droite():
    """
    Calcul de la position souhaitée par le joueur après saisie d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le déplacement du personnage vers la droite
    """
    global matrice, position_personnage, dictionnaire_objet, inventaire, dictionnaire_portes

    desactiver_mouvement()

    # Calcul la position du personnage quand le placement vers la droite
    position_souhaite = (position_personnage[0],position_personnage[1]+1)
    prochaine_position = deplacer(matrice, position_personnage, position_souhaite, dictionnaire_objet, inventaire, dictionnaire_portes)
    position_personnage = prochaine_position
    activer_mouvement()



def deplacer_haut():
    """
    Calcul de la position souhaitée par le joueur après saisie d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le déplacement du personnage vers le haut
    """
    global matrice, position_personnage, dictionnaire_objet, inventaire, dictionnaire_portes

    desactiver_mouvement()

    # Calcul la position du personnage quand le placement vers le haut
    position_souhaite = (position_personnage[0]-1,position_personnage[1])
    prochaine_position = deplacer(matrice, position_personnage, position_souhaite, dictionnaire_objet, inventaire, dictionnaire_portes)
    position_personnage = prochaine_position
    activer_mouvement()



def deplacer_bas():
    """
    Calcul de la position souhaitée par le joueur après saisie d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le déplacement du personnage vers le bas
    """
    global matrice, position_personnage, dictionnaire_objet, inventaire, dictionnaire_portes

    desactiver_mouvement()

    # Calcul la position du personnage quand le placement vers le bas
    position_souhaite = (position_personnage[0]+1,position_personnage[1])
    prochaine_position = deplacer(matrice, position_personnage, position_souhaite, dictionnaire_objet, inventaire, dictionnaire_portes)
    position_personnage = prochaine_position
    activer_mouvement()



def desactiver_mouvement():
    """
    Desactive les touches de déplacements
    :return:
    """
    turtle.onkeypress(None, "Down")  # Désactive la touche Down
    turtle.onkeypress(None, "Up")  # Désactive la touche Up
    turtle.onkeypress(None, "Left")  # Désactive la touche Left
    turtle.onkeypress(None, "Right")  # Désactive la touche Right


def activer_mouvement():
    """
    Active les touches de déplacement
    :return:
    """
    turtle.onkeypress(deplacer_gauche, "Left")  # Réassocie la touche Left à la fonction déplacer_gauche
    turtle.onkeypress(deplacer_droite, "Right")  # Réassocie la touche Right à la fonction déplacer_droite
    turtle.onkeypress(deplacer_haut, "Up")  # Réassocie la touche Up à la fonction déplacer_haut
    turtle.onkeypress(deplacer_bas, "Down")  # Réassocie la touche Down à la fonction déplacer_bas




def creer_dictionnaire_des_objets(fichier_des_objets):
    """
    Création du dictionnaire contenant les positions et noms des objets à partir du fichier reçu en paramètre
    :param fichier_des_objets: Nom du fichier txt contenant par ligne un tuple correspondant au coordonnée de
                               l'objet suivi du nom de l'objet
    :return: Dictionnaire des objets. En cle, les coordonnées et en valeur les noms des objets.
    """
    dico = {}
    for ligne in open(fichier_des_objets,encoding="UTF_8"):
        position,nom = eval(ligne)
        dico[position] = nom
    return dico



def creer_dictionnaire_des_portes(fichier_des_portes):
    """
    Création du dictionnaire contenant les positions et les Questions/Réponses à partir du fichier reçu en paramètre
    :param fichier_des_portes: Nom du fichier txt contenant par ligne un tuple correspondant aux coordonnées de
                               la porte suivie de la Question/Réponse
    :return: Dictionnaire des portes. En cle, les coordonnées et en valeur les Questions/Réponses.
    """
    dico = {}
    for ligne in open(fichier_des_portes,encoding="UTF_8"):
        position,Q_et_R = eval(ligne)
        dico[position] = Q_et_R
    return dico



def ramasser_objet(prochaine_position, liste_objet, inventaire):
    """
    Affiche le message d'objet trouvé dans la zone de message
    Affiche l'objet dans la zone de l'inventaire
    :param prochaine_position: Position où va aller le joueur (correspond au coordonnées de l'objet)
    :param liste_objet: Liste de tous mes objets présents dans le château
    :param inventaire : Liste des objets que possède le joueur
    :return:
    """

    afficher_message("Vous avez trouvé : "+ liste_objet[prochaine_position])
    inventaire.append(liste_objet[prochaine_position])
    afficher_inventaire(inventaire)



def question_porte(matrice,position_joueur,prochaine_position,dimension, dico_porte,):
    """
    Fait disparaître les portes du plan et de la matrice.
    Affiche l'objet dans la zone de l'inventaire
    :param matrice: Matrice du plan
    :param prochaine_position: Position ou va aller le joueur (correspond au coordonnées de le porte)
    :param dimension: taille d'une case
    :param dico_porte: Liste de toutes les portes présentes dans le château
    :return:
    """

    # Récupération de la question
    afficher_message("Cette porte est fermée.")
    reponse = turtle.textinput("Question",dico_porte[prochaine_position][0])
    nouvelle_position = position_joueur

    # Vérification de la réponse donnée par le joueur
    if reponse == dico_porte[prochaine_position][1]:
        initialisation_case(matrice,prochaine_position,dimension)
        afficher_message("Bonne réponse ! La porte s'est ouverte.")
        mouvement_joueur(position_joueur, dimension, prochaine_position, matrice)
        nouvelle_position = prochaine_position
    else:
        afficher_message("Mauvaise réponse ! Cette porte est toujours fermée.")


    # Réactivation de l'écoute des saisies clavier
    turtle.listen()

    return nouvelle_position



def verification_objectif_reussi(liste_objet,inventaire,position, dimension, mouvement, matrice):
    """
    Test s'il y a autant d'objets dans l'inventaire que d'objets dans le fichier de la liste d'objet.
        - S'il n'y a pas autant d'objets dans l'inventaire que dans la liste, le personnage ne se déplace pas et renvoi
        une annonce indiquant le nombre d'objets restant à trouver pour sortir.
        - Si le nombre d'objet correspond, le personnage se déplace, une fenêtre demande le pseudo du joueur, un
        message s'affiche dans l'annonce et le jeu s'arrête et reste en attente de la fermeture de la fenêtre.

    :param liste_objet: Liste des objets presents dans le château (coordonnées + nom de l'objet)
    :param inventaire: Ensemble des objet ramassé par le joueur
    :param position: Position actuelle du personnage
    :param dimension: Taille d'une case du château
    :param mouvement: Position où souhaite se déplacer le joueur
    :param matrice: Matrice représentant les cases du château
    :return: Renvoi la prochaine position du personnage
    """

    objet_a_recup = len(liste_objet)
    objet_trouve = len(inventaire)
    prochaine_position = position
    if (objet_a_recup - objet_trouve) != 0 :
        afficher_message("Il vous manque " + str(objet_a_recup - objet_trouve) + ' trésors à trouver. Trouvez les '
                                                                                 'et revenez ensuite.')
    else :
        mouvement_joueur(position, dimension, mouvement, matrice)
        pseudo = turtle.textinput("Vous avez terminé ! ", "Quel est votre pseudo ? ")
        afficher_message("VICTOIRE ! " + pseudo + ", vous avez atteint l'arrivée avec tous "
                                                  "les trésors! Bravo ! ", ('Arial', 12, 'normal'))
        prochaine_position = mouvement
    return prochaine_position


def initialisation_case(matrice,prochaine_position,dimension):
    """
    Initalisation de la case en case couloir. Et modification de la case dans la matrice à 0
    :param matrice: Matrice du plan de château
    :param prochaine_position: Position de la case à initialiser
    :param dimension: taille d'une case
    :return:
    """

    coord_destination = coordonnees(prochaine_position,dimension)
    tracer_case(coord_destination, COULEUR_CASES, dimension)
    matrice[prochaine_position[0]][prochaine_position[1]] = 0



def afficher_message(texte, police=('Arial', 8, 'normal')):
    """
    Fonction pour faire apparaître un message reçu en entrée dans la zone de message
    :param texte: Message à faire apparaître
    :return:
    """
    mise_en_forme_zone_annonce()
    turtle.color("black")
    turtle.goto(POINT_AFFICHAGE_ANNONCES)
    turtle.write(texte,font=police)



def afficher_inventaire(inventaire):
    """
    Affiche les objets présents dans l'inventaire dans la zone d'inventaire
    :param inventaire: Liste des objets présents dans l'inventaire
    :return:
    """
    mise_en_forme_zone_inventaire()
    turtle.color('black')
    turtle.goto(POINT_AFFICHAGE_INVENTAIRE)
    for nbr_objet,nom_objet in enumerate(inventaire):
        turtle.goto((POINT_AFFICHAGE_INVENTAIRE[0] + 20),(POINT_AFFICHAGE_INVENTAIRE[1]-(20 * (nbr_objet + 2))))
        turtle.write("- " + nom_objet)





###############################
##### Traitement principal ####
###############################

# Création de la matrice du plan
matrice = creation_matrice(fichier_plan)


# Affichage des zones Annonce et Inventaire
mise_en_forme_zone_annonce()
mise_en_forme_zone_inventaire()


# Affichage de la carte
afficher_plan(matrice)


# Création de la liste des objets
dictionnaire_objet = creer_dictionnaire_des_objets(fichier_objets)


# Liste dans laquelle sont stockés les objets ramassés par le joueur
inventaire = []


# Création de la liste des Questions/Réponses pour les portes
dictionnaire_portes = creer_dictionnaire_des_portes(fichier_questions)


# Position de départ du personnage
position_personnage = POSITION_DEPART


# Déplacement du personnage
turtle.listen()  # Déclenche l’écoute du clavier
turtle.onkeypress(deplacer_gauche, "Left")  # Associe à la touche Left une fonction appelée deplacer_gauche
turtle.onkeypress(deplacer_droite, "Right") # Associe à la touche Right une fonction appelée deplacer_droite
turtle.onkeypress(deplacer_haut, "Up") # Associe à la touche Up une fonction appelée deplacer_haut
turtle.onkeypress(deplacer_bas, "Down") # Associe à la touche Down une fonction appelée deplacer_bas
turtle.mainloop()  # Place le programme en position d’attente d’une action du joueur