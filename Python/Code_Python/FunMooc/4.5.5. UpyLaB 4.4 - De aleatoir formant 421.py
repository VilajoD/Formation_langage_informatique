"""
fonction alea_dice(s) qui génère trois nombres (pseudo) aléatoires à l’aide de la fonction randint du module
random, représentant trois dés (à six faces avec les valeurs de 1 à 6), et qui renvoie la valeur booléenne True
si les dés forment un 421, et la valeur booléenne False sinon.

Le paramètre s de la fonction est un nombre entier, qui sera passé en argument à la fonction random.seed au
début du code de la fonction. Cela permettra de générer la même suite de nombres aléatoires à chaque appel de
la fonction, et ainsi de pouvoir tester son fonctionnement.

"""
import random

def alea_dice(s):

    random.seed(s)
    res = False

    x = random.randint(1, 6)

    if x == 4 or x == 2 or x == 1 :
        y = random.randint(1, 6)

        if (y == 4 or y == 2 or y == 1) and x != y :
            z = random.randint(1, 6)

            if (z == 4 or z == 2 or z == 1) and z != y and z != x :
                res = True

    return(res)