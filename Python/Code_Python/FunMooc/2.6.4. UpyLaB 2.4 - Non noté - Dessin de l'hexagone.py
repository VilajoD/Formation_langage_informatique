import turtle
from math import pi, cos, sin

print("Dessinons un Hexagone ensemble")
long = float(input('Quelle taille ?'))
couleur = input('Quelle couleur (en anglais)? ')

x1 = cos(1*(pi/3))
y1 = sin(1*(pi/3))

x2 = cos(2*(pi/3))
y2 = sin(2*(pi/3))

x3 = cos(3*(pi/3))
y3 = sin(3*(pi/3))

x4 = cos(4*(pi/3))
y4 = sin(4*(pi/3))

x5 = cos(5*(pi/3))
y5 = sin(5*(pi/3))


turtle.up()
turtle.goto(long,0.0)
turtle.down()
turtle.color(couleur)
turtle.begin_fill()
turtle.goto(long * x1 , long * y1)
turtle.goto(long * x2 , long * y2)
turtle.goto(long * x3 , long * y3)
turtle.goto(long * x4 , long * y4)
turtle.goto(long * x5 , long * y5)
turtle.goto(long,0.0)
turtle.end_fill()

turtle.hideturtle()
turtle.done()