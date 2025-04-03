"""
fonction my_insert qui reçoit comme premier paramètre une liste d’entiers relatifs triée par
ordre croissant et comme deuxième paramètre un entier relatif n, et qui renvoie une liste correspondant
à la liste reçue, mais dans laquelle le nombre n a été inséré à la bonne place.

La liste donnée en paramètre ne doit pas être modifiée par votre fonction.

Vous pouvez supposer que le premier paramètre sera bien une liste triée d’entiers, mais si le
deuxième paramètre n’est pas du bon type, la fonction retourne None.
"""

def my_insert(liste, insert):
    insertion = False

    assert type(insert) == int , 'AssertionError'

    if type(insert) == int:
        for i in range(len(liste)):
            if liste[i] > insert and insertion == False :
                liste.insert(i,insert)
                insertion = True
        if insertion == False:
            liste.insert(len(liste),insert)
    return(None)

l = [1, 3, 5]
print(l)
my_insert(l, 4)
print(l)
