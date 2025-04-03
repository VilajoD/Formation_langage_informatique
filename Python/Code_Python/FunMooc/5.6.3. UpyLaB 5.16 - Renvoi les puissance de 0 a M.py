"""
fonction my_pow qui prend comme paramètres un nombre entier m et un nombre flottant b,
et qui renvoie une liste contenant les m premières puissances de b, c’est-à-dire une liste contenant
les nombres allant de b^0 à b^{m - 1}.

Si le type des paramètres n’est pas celui attendu, la fonction retournera la valeur None.
"""

def my_pow(puissance, nombre):
    if type(nombre) == float and type(puissance) == int:
        liste=[nombre**i for i in range(puissance)]
    else :
        liste=None
    return(liste)

print(my_pow('i', 5.0))
