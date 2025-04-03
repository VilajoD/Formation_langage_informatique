import turtle

nbr_cote=4
angle1=360/nbr_cote + 30
angle2=360/nbr_cote - 30
taille=300

#dessin du losange noir
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

#dessin du losange bleu
turtle.color('blue')
turtle.begin_fill()
turtle.forward(taille)
turtle.left(angle1)
turtle.forward(taille)
turtle.left(angle2)
turtle.forward(taille)
turtle.left(angle1)
turtle.forward(taille)
turtle.left(angle2)
turtle.end_fill()

#dessin du losange rouge
turtle.color('red')
turtle.right(angle1)
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


turtle.hideturtle()
turtle.done()