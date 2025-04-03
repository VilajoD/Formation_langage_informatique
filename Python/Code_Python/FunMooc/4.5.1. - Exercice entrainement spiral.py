"""
Petit code Python pour créer une spiral avec la fonction Turtle

"""

import turtle

def spiral(couleur,taille,angle):
    turtle.width(taille)
    turtle.color(couleur)
    turtle.speed(0)
    for i in range(0,100,5):
        turtle.circle(i,angle)

spiral("red",10,90)

turtle.up()
turtle.goto(0,0)
turtle.down()

spiral("Blue",10,90)

turtle.done()