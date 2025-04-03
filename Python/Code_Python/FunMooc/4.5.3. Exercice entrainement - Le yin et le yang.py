"""
yin et yang dessiné avec turtle
"""
import turtle

def yin_yang(taille, couleur_1="black", couleur_2="white"):
    """ dessine un logo yin-yang de rayon 200 """
    turtle.down()  # met la plume en mode tracé (si ce n'était déjà le cas)
    turtle.width(2)  # grosseur du tracé de 2 points


    # dessine le yin externe
    def principal(taille, remplissage, contour = "black"):
        turtle.color(contour, remplissage) # le tracé et le remplissage seront en noir
        turtle.begin_fill()  # la ou les formes suivantes seront remplies
        turtle.circle(-taille, 180)  # demi cercle intérieur tournant vers la droite
        turtle.circle(-(taille*2), -180)  # demi cercle extérieur, en marche arrière
        turtle.circle(-taille, -180)  # demi cercle intérieur qui complète le yin
        turtle.end_fill() # remplissage

    # dessine le yang interne
    def petit_rond(taille, remplissage):
        turtle.color(remplissage)
        turtle.up() # on ne trace pas ce qui suit

        # déplace la tortue au bon endroit
        turtle.right(90)
        turtle.forward((8/10)*taille)
        turtle.left(90)

        # tracé du disque yang (blanc) interne au yin
        turtle.down()
        turtle.begin_fill()
        turtle.circle(-(taille/4))
        turtle.end_fill()

        # se replace au centre
        turtle.up()
        turtle.left(90)
        turtle.forward((8/10)*taille)
        turtle.right(90)
        turtle.down()

    principal(taille,couleur_1,couleur_1)
    petit_rond(taille,couleur_2)

    principal(taille,couleur_2,couleur_1)
    petit_rond(taille,couleur_1)

    return

#code principal
couleur_1 = input("Couleur 1 : ")
couleur_2 = input("Couleur 2 : ")
taille = int(input("Quelle taille : "))

yin_yang(taille,couleur_1,couleur_2) #réalise le logo

turtle.done()