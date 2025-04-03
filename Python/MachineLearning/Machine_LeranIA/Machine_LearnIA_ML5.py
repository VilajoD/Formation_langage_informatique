import numpy as np

'Création d une matrice '
A = np.array([[1,2],[3,4],[5,6]])
print (A)
print(A.shape)
print(' ')

'Transposé de la matrice A --> C est a dire retourner la matrice'
AT = A.T
print (AT)
print(AT.shape)
print(' ')

'Addition de matrice --> Elle doivent avoir les même dimension'
'Créer une matice rapidement que avec des 1'

B = np.ones([3,2])
print(B)
print(' ')

C = A + B
print(C)
print(' ')


'Faire un produit matriciel A.B = C (une mutltiplication) --> Il faut que le nombre de ligne B soit egale au nombre de colonne A'
'A --> M*N  B --> N*Z  C --> M*Z (M, N et Z sont les dimension de la matrice)'
'Donc actuellement on ne peut pas faire A.B car A --> 3,2 et B --> 3,2'
'Pour le test on pourrait simplement transposer B ce qui donnerai BT --> 2,3. Attention B et BT n ont rien a voir, c est juste pour l exemple)'

BT = B.T
print(A)
print('-----')
print(BT)
print('-----')
print(A.dot(BT))
