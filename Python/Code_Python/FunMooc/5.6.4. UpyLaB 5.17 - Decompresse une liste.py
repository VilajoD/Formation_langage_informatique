"""
fonction decompresse qui reçoit une telle liste en paramètre et renvoie la séquence
t sous forme d’une nouvelle liste.

Par exemple, la liste [(1, 'He'), (2, 'l'), (1,'o')] décrit la séquence "Hello".
"""

def decompresse(liste):
    return([liste[i][1] for i in range(len(liste)) for j in range(liste[i][0])])

print(decompresse([(4, 1), (0, 2), (2, 'test'), (3, 3), (1, 'bonjour')]))
