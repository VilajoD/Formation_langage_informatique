import turtle
from math import pi, sin, cos
import random
import time

def pave(abscisse_centre, ordonnee_centre, longueur_arete, color1, color2, color3):
    """ Dessine avec turtle un pave hexagonal
        en position ( abscisse_centre, ordonnee_centre)
        paramètres :
        - (abscisse_centre, ordonnee_centre) : point centre du pavé
        -  longueur_arete : longueur de chaque arête du pavé
        - color1, color2, color3 : les couleurs des 3 hexagones"""

    def dessin(taille, couleur, angle1, angle2):

        turtle.color(couleur)
        turtle.begin_fill()
        turtle.forward(taille)
        turtle.right(angle1)
        turtle.forward(taille)
        turtle.right(angle2)
        turtle.forward(taille)
        turtle.right(angle1)
        turtle.forward(taille)
        turtle.right(angle2)
        turtle.end_fill()

    turtle.up()
    turtle.goto(abscisse_centre,ordonnee_centre)
    angle1 = 120
    angle2 = 60
    turtle.down()
    dessin(longueur_arete, color1, angle1, angle2)
    dessin(longueur_arete, color2, -angle1, -angle2)
    turtle.right(angle1)
    dessin(longueur_arete, color3, angle1, angle2)
    turtle.right(360-angle1)

turtle.hideturtle()
turtle.speed(0)
turtle.reset()
time.sleep(5)

while True:
    pave(random.randint(-300,300),  random.randint(-300,300),
        random.randint(10,50), 'black', 'red', 'blue')
    pave(random.randint(-300,300),  random.randint(-300,300),
        random.randint(10,50), 'white', 'grey', 'black')