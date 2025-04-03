"""
Programme qui dit si deux des trois nombre en entrées sont egaux. En utilisant une fonction.

"""



def deux_egaux(a, b, c) :

    if a == b :
        egaux = True
    elif a == c :
        egaux = True
    elif b == c :
        egaux = True
    else :
        egaux = False
    return egaux


x = float(input())
y = float(input())
z = float(input())

print(deux_egaux(x,y,z))