import turtle
from random import randint

def polygone_turtle(cote,taille,couleur,X,Y):

   turtle.up()
   turtle.goto(X,Y)
   turtle.down()
   angle = 360 / cote
   turtle.color(couleur)
   turtle.begin_fill()


   for i in range(cote):
      turtle.forward(taille)
      turtle.left(angle)

   turtle.end_fill()


for couleur in ("black", "blue", "red", "green", "yellow"):
   polygone_turtle(randint(3,9), randint(10,200), couleur, randint(-300,200), randint(-300,200))



turtle.done()