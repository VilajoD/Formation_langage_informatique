import turtle

cote = int(input("Nombre de cotés du polygone : "))
taille = int(input("Taille du cotés du polygone : "))
couleur = input("Quelle couleur (en anglais) : ")
angle = 360 / cote


turtle.color(couleur)
turtle.begin_fill()

for i in range(cote):
   turtle.forward(taille)
   turtle.left(angle)

turtle.end_fill()
turtle.done()