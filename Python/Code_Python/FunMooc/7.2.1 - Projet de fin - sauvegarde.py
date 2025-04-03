"""

"""
from CONFIGS import *  # Fichier des constantes globales du programme
import turtle


def afficher_plan(matrice):
    """
    Permet de dessiner le plan du chateau grace a Turtle
    :param matrice: Matrice representant le plan du chateau
    :return:
    """

    def calculer_pas(matrice):
        """
        A partir de la matrice recu en entrée, renvoit la dimension à donner aux cases pour
        que le plan tienne dans la zone de la fenêtre turtle
        :param matrice:Matrice representant le plan du chateau
        :return:Le pas calculé
        """
        # Calcul de la taille de la zone en X et Y a partir des données contenu dans le fichier CONFIGS
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


        return calcul_pas


    def coordonnees(case, pas):
        """
        Calcul de la position (abscisse et ordonnée) du coin inferieur gauche de la case recu en entrée grace à
        ca dimession "pas" recu en entrée.
        :param case: Coordonnés d'une case (ligne , colonne)
        :param pas: Taille de la case (en pixel)
        :return: renvoie les coordonées pixel du coin bas et gauche de la cas d'entrée (abscisse et ordonnée)
        """

        coord_y = ZONE_PLAN_MAXI[1] - (case[0] * pas)
        coord_x = ZONE_PLAN_MINI[0] + (case[1] * pas)

        return(coord_x,coord_y)


    def tracer_carre(dimension):
        """
        Trace un carré en utilisant la dimension recu en entrée
        :param dimension: Correspond a la dimension d'un coté du carée
        :return:
        """
        angle = 90
        turtle.down()
        turtle.begin_fill()

        # Tracé du carré
        for i in range(4):
            turtle.forward(dimension)
            turtle.left(angle)

        turtle.end_fill()
        turtle.up()



    def tracer_case(case, couleur, pas):
        """
        Permet de tracer un carré a l'endroit indiqué par "case" de la couleur "couleur" et de la dimanssion "pas"
        :param case: coordonées pixel du coin bas et gauche d'une case
        :param couleur: Couleur que prendra le carrée
        :param pas: Taille du coté du carré
        :return:
        """

        turtle.up()
        turtle.goto(case[0], case[1]) #Mise en position du Curseur
        turtle.color(couleur)
        tracer_carre(pas) #Appel à la fonction de tracé du carré


    # Début du traitement principal de la fonction afficher_plan
    dimension = calculer_pas(matrice) #Calcul de la dimmension du plan a afficher
    turtle.speed(0)  #Augmentation de la vitesse d'affichage
    for x,ligne in enumerate(matrice): #Pour chaque case de la matrice, calcul des coordonnée, de la couleur et tracé de la case
        for y,case in enumerate(ligne):
            coord = coordonnees((x,y), dimension)
            couleur = COULEURS[case]
            tracer_case(coord,couleur,dimension)
    #turtle.done()


def deplacer(matrice, position, mouvement):
    """
    Verification de la possibilté du deplacement demandé par le joueur. Si possible, deplacement du personnage
    et affichage sur la carte.
    :param matrice: Matrice du plan
    :param position: Case actuelle ou ce situe le personnage
    :param mouvement: Position demandé par le joueur pour le deplacement du personnage
    :return:
    """
    if matrice[mouvement(0)][mouvement(1)] == 0:
        position = mouvement
    elif matrice[mouvement(0)][mouvement(1)] == 1:
        pass
    elif matrice[mouvement(0)][mouvement(1)] == 2:
        position = mouvement
    elif matrice[mouvement(0)][mouvement(1)] == 3:
        pass
    elif matrice[mouvement(0)][mouvement(1)] == 4:
        position = mouvement




def deplacer_gauche():
    """
    Calcul de la position souhaitée par le joueur apres saisi d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le deplacement du personnage vers la gauche
    """
    global matrice, position_personnage
    turtle.onkeypress(None, "Left")  # Désactive la touche Left
    # Calcul la position du personnage quand le placement vers la gauche
    position_souhaite = (position_personnage[0],position_personnage[1]-1)
    turtle.onkeypress(deplacer_gauche, "Left")  # Réassocie la touche Left à la fonction deplacer_gauche
    print(position_personnage)
    print(position_souhaite)
    return position_souhaite


def deplacer_droite():
    """
    Calcul de la position souhaitée par le joueur apres saisi d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le deplacement du personnage vers la droite
    """
    global matrice, position_personnage
    turtle.onkeypress(None, "Right")  # Désactive la touche Right
    # Calcul la position du personnage quand le placement vers la droite
    position_souhaite = (position_personnage[0],position_personnage[1]+1)
    turtle.onkeypress(deplacer_gauche, "Right")  # Réassocie la touche Right à la fonction deplacer_gauche
    print(position_personnage)
    print(position_souhaite)
    return position_souhaite



def deplacer_haut():
    """
    Calcul de la position souhaitée par le joueur apres saisi d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le deplacement du personnage vers le haut
    """
    global matrice, position_personnage
    turtle.onkeypress(None, "Up")  # Désactive la touche Up
    # Calcul la position du personnage quand le placement vers le haut
    position_souhaite = (position_personnage[0]-1,position_personnage[1])
    turtle.onkeypress(deplacer_gauche, "Up")  # Réassocie la touche Up à la fonction deplacer_gauche
    print(position_personnage)
    print(position_souhaite)
    return position_souhaite



def deplacer_bas():
    """
    Calcul de la position souhaitée par le joueur apres saisi d'une touche de direction sur le clavier
    :return: Case souhaitée par le joueur pour le deplacement du personnage vers le bas
    """
    global matrice, position_personnage
    turtle.onkeypress(None, "Down")  # Désactive la touche Down
    # Calcul la position du personnage quand le placement vers le bas
    position_souhaite = (position_personnage[0]+1,position_personnage[1])
    turtle.onkeypress(deplacer_gauche, "Down")  # Réassocie la touche Down à la fonction deplacer_gauche
    print(position_personnage)
    print(position_souhaite)
    return position_souhaite




matrice = [[1, 0, 1, 1, 1, 1], [1, 0, 0, 0, 0, 1], [1, 0, 4, 0, 0, 1], [1, 1, 1, 3, 1, 1]]

#Affichage de la carte
afficher_plan(matrice)

#Deplacement du personnage
position_personnage = POSITION_DEPART

turtle.listen()  # Déclenche l’écoute du clavier
mouvement = turtle.onkeypress(deplacer_gauche, "Left")  # Associe à la touche Left une fonction appelée deplacer_gauche
mouvement = turtle.onkeypress(deplacer_droite, "Right")
mouvement = turtle.onkeypress(deplacer_haut, "Up")
mouvement = turtle.onkeypress(deplacer_bas, "Down")
turtle.mainloop()  # Place le programme en position d’attente d’une action du joueur